package com.example.springboottest20220608;

import com.example.springboottest20220608.entity.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ExchangeRateTask {

    private static BigDecimal eur_to_sek;
    private static BigDecimal eur_to_usd;
    private static BigDecimal usd_to_sek;

    public static BigDecimal getEur_to_sek() {
        return eur_to_sek;
    }

    public static BigDecimal getEur_to_usd() {
        return eur_to_usd;
    }

    public static BigDecimal getUsd_to_sek() {
        return usd_to_sek;
    }


    // Schedule getExchangeRate task every hour(60 x 60 x 1000ms = 3,600,000ms)
    // For testing purpose you could set it to be 60,000ms which is 1 minute
    @Scheduled(fixedRate = 3600000)
    public void getExchangeRate() {
        eur_to_sek = httpRequest("EUR", "SEK");
        eur_to_usd = httpRequest("EUR", "USD");
        usd_to_sek = httpRequest("USD", "SEK");
    }

    public BigDecimal httpRequest(String from, String to) {
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

            System.out.println(httpResponse.body());
            System.out.println(exchangeRate);

        }
        catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return rate;
    }

}
