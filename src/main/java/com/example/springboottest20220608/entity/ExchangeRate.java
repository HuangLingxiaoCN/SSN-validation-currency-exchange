package com.example.springboottest20220608.entity;

public class ExchangeRate {
    private boolean success;
    private String timestamp;
    private String base;
    private String date;
    private Object rates;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getRates() {
        return rates;
    }

    public void setRates(Object rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "success=" + success +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }
}
