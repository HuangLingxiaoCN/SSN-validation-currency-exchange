package com.example.springboottest20220608.controller;

import com.example.springboottest20220608.entity.ExchangeAmount;
import com.example.springboottest20220608.entity.ExchangeRate;
import com.example.springboottest20220608.exception.NoSupportedCurrencyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeAmountController {

    // locally stored exchange rate
/*    private Map<String, BigDecimal> exchangeRate = Map.of(
            "eur_to_usd", new BigDecimal("0.96"),
            "eur_to_sek", new BigDecimal("10.91"),
            "usd_to_sek", new BigDecimal("11.37")
    );*/

    @PostMapping
    public String getExchangeAmount(@RequestBody ExchangeAmount exchangeAmount) {

        BigDecimal resultValue = new BigDecimal(exchangeAmount.getFrom_amount());

        // check all conditions of EUR, SEK and USD exchange
        if (exchangeAmount.getFrom().equalsIgnoreCase("EUR") && exchangeAmount.getTo().equalsIgnoreCase("SEK")) {
            BigDecimal realTimeRate = getExchangeRate("EUR", "SEK");
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(realTimeRate.setScale(3, RoundingMode.HALF_UP))
                    + "\nReal-time exchange rate: "
                    + realTimeRate;
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("EUR") && exchangeAmount.getTo().equalsIgnoreCase("USD")) {
            BigDecimal realTimeRate = getExchangeRate("EUR", "USD");
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(realTimeRate).setScale(3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + realTimeRate;
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("SEK") && exchangeAmount.getTo().equalsIgnoreCase("EUR")) {
            BigDecimal realTimeRate = getExchangeRate("SEK", "EUR");
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(realTimeRate, 3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + realTimeRate;
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("SEK") && exchangeAmount.getTo().equalsIgnoreCase("USD")) {
            BigDecimal realTimeRate = getExchangeRate("SEK", "USD");
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(realTimeRate, 3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + realTimeRate;
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("USD") && exchangeAmount.getTo().equalsIgnoreCase("EUR")) {
            BigDecimal realTimeRate = getExchangeRate("USD", "EUR");
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(realTimeRate, 3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + realTimeRate;
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("USD") && exchangeAmount.getTo().equalsIgnoreCase("SEK")) {
            BigDecimal realTimeRate = getExchangeRate("USD", "SEK");
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(realTimeRate).setScale(3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + realTimeRate;
        }

        throw new NoSupportedCurrencyException(exchangeAmount.getFrom(), exchangeAmount.getTo());

    }

    public BigDecimal getExchangeRate(String from, String to) {
        BigDecimal rate = new BigDecimal(0);
        String url = "https://api.apilayer.com/exchangerates_data/latest?symbols="+to+"&base="+from;
        String apiKey = "gvCtnrLeF5EvK4HnTyElEmi7ldIUfhSO";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .header("apiKey", apiKey)
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            ExchangeRate exchangeRate = objectMapper.readValue(httpResponse.body(), ExchangeRate.class);

            String rateString = exchangeRate.getRates().toString();
            rate = BigDecimal.valueOf(Double.parseDouble(rateString.substring(5, rateString.length() - 2)));

            System.out.println(exchangeRate);
            System.out.println(rate);
        }
        catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return rate;
    }

}
