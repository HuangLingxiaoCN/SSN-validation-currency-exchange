package com.example.springboottest20220608.controller;

import com.example.springboottest20220608.entity.ExchangeAmount;
import com.example.springboottest20220608.entity.ExchangeRate;
import com.example.springboottest20220608.exception.NoSupportedCurrencyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
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
import java.util.Map;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeAmountController {

    // locally stored exchange rate
    private Map<String, BigDecimal> exchangeRate = Map.of(
            "eur_to_usd", new BigDecimal("0.96"),
            "eur_to_sek", new BigDecimal("10.91"),
            "usd_to_sek", new BigDecimal("11.37")
    );

    @PostMapping
    public String getExchangeAmount(@RequestBody ExchangeAmount exchangeAmount) {

        BigDecimal resultValue = new BigDecimal(exchangeAmount.getFrom_amount());

        // check all conditions of EUR, SEK and USD exchange
        if (exchangeAmount.getFrom().equalsIgnoreCase("EUR") && exchangeAmount.getTo().equalsIgnoreCase("SEK")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(exchangeRate.get("eur_to_sek")).setScale(3, RoundingMode.HALF_UP)
                    + "\n"
                    + getExchangeRate();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("EUR") && exchangeAmount.getTo().equalsIgnoreCase("USD")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(exchangeRate.get("eur_to_usd")).setScale(3, RoundingMode.HALF_UP)
                    + "n"
                    + getExchangeRate();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("SEK") && exchangeAmount.getTo().equalsIgnoreCase("EUR")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(exchangeRate.get("eur_to_sek"), 3, RoundingMode.HALF_UP)
                    + "\n"
                    + getExchangeRate();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("SEK") && exchangeAmount.getTo().equalsIgnoreCase("USD")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(exchangeRate.get("usd_to_sek"), 3, RoundingMode.HALF_UP)
                    + "\n"
                    + getExchangeRate();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("USD") && exchangeAmount.getTo().equalsIgnoreCase("EUR")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(exchangeRate.get("eur_to_usd"), 3, RoundingMode.HALF_UP)
                    + "\n"
                    + getExchangeRate();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("USD") && exchangeAmount.getTo().equalsIgnoreCase("SEK")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(exchangeRate.get("usd_to_sek")).setScale(3, RoundingMode.HALF_UP)
                    + "\n"
                    + getExchangeRate();
        }

        throw new NoSupportedCurrencyException(exchangeAmount.getFrom(), exchangeAmount.getTo());

    }

    public String getExchangeRate() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.apilayer.com/exchangerates_data/latest?symbols=USD%2CSEK&base=EUR";
        String apiKey = "gvCtnrLeF5EvK4HnTyElEmi7ldIUfhSO";
/*        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", apiKey)
                .build();*/

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .header("apiKey", apiKey)
                .uri(URI.create(url))
                .build();

        try {
/*            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());*/
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            ExchangeRate exchangeRate = objectMapper.readValue(httpResponse.body(), ExchangeRate.class);
            System.out.println(exchangeRate);

            //
            return exchangeRate.toString();

            //

        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        return null;
    }

}
