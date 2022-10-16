package com.example.springboottest20220608.controller;

import com.example.springboottest20220608.ExchangeRateTask;
import com.example.springboottest20220608.entity.ExchangeAmount;
import com.example.springboottest20220608.exception.NoSupportedCurrencyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        // This block of code could be extracted and simplified:

        if (exchangeAmount.getFrom().equalsIgnoreCase("EUR") && exchangeAmount.getTo().equalsIgnoreCase("SEK")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(ExchangeRateTask.getEur_to_sek().setScale(3, RoundingMode.HALF_UP))
                    + "\nReal-time exchange rate: "
                    + ExchangeRateTask.getEur_to_sek();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("EUR") && exchangeAmount.getTo().equalsIgnoreCase("USD")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(ExchangeRateTask.getEur_to_usd()).setScale(3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + ExchangeRateTask.getEur_to_usd();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("SEK") && exchangeAmount.getTo().equalsIgnoreCase("EUR")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(ExchangeRateTask.getEur_to_sek(), 3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + ExchangeRateTask.getEur_to_sek();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("SEK") && exchangeAmount.getTo().equalsIgnoreCase("USD")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(ExchangeRateTask.getUsd_to_sek(), 3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + ExchangeRateTask.getUsd_to_sek();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("USD") && exchangeAmount.getTo().equalsIgnoreCase("EUR")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.divide(ExchangeRateTask.getEur_to_usd(), 3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + ExchangeRateTask.getEur_to_usd();
        }

        if (exchangeAmount.getFrom().equalsIgnoreCase("USD") && exchangeAmount.getTo().equalsIgnoreCase("SEK")) {
            return exchangeAmount.toString()
                    + "\nResult: "
                    + resultValue.multiply(ExchangeRateTask.getUsd_to_sek()).setScale(3, RoundingMode.HALF_UP)
                    + "\nReal-time exchange rate: "
                    + ExchangeRateTask.getUsd_to_sek();
        }

        throw new NoSupportedCurrencyException(exchangeAmount.getFrom(), exchangeAmount.getTo());

    }

}
