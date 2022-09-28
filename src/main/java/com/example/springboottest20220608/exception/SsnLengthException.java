package com.example.springboottest20220608.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SsnLengthException extends RuntimeException{

    private String ssn;

    public SsnLengthException(String ssn) {
        super(String.format("ssn: %s is not 11 characters long!", ssn));
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
