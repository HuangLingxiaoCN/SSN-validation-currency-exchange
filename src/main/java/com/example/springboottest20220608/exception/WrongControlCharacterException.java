package com.example.springboottest20220608.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongControlCharacterException extends RuntimeException {
    private String ssn;
    private String correctControlCharacter;

    public WrongControlCharacterException(String ssn, String correctControlCharacter) {
        super(String.format("ssn: %s has wrong control character. It should be %s ", ssn, correctControlCharacter));
        this.ssn = ssn;
        this.correctControlCharacter = correctControlCharacter;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCorrectControlCharacter() {
        return correctControlCharacter;
    }

    public void setCorrectControlCharacter(String correctControlCharacter) {
        this.correctControlCharacter = correctControlCharacter;
    }
}
