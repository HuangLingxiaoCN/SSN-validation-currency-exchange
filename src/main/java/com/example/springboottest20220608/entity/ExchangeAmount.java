package com.example.springboottest20220608.entity;

public class ExchangeAmount {
    private String from;
    private String to;
    private String from_amount;

    public ExchangeAmount(String from, String to, String from_amount) {
        this.from = from;
        this.to = to;
        this.from_amount = from_amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom_amount() {
        return from_amount;
    }

    public void setFrom_amount(String from_amount) {
        this.from_amount = from_amount;
    }

    @Override
    public String toString() {
        return "ExchangeAmount{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", from_amount='" + from_amount + '\'' +
                '}';
    }
}
