package com.example.springboottest20220608.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoSupportedCurrencyException extends RuntimeException{

    private String from;
    private String to;

    public NoSupportedCurrencyException(String from, String to) {
        super(String.format("Currency exchange from %s to %s is not supported.", from, to));
        this.from = from;
        this.to = to;
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
}
