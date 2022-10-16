package com.example.springboottest20220608.entity;


public class Ssn {

    private String ssn;
    private String countryCode;

    public Ssn(String ssn, String countryCode) {
        this.ssn = ssn;
        this.countryCode = countryCode;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public char getControlCharacter() {
        char[] ssnCharArray = ssn.toCharArray();
        return ssnCharArray[ssnCharArray.length-1];
    }

    @Override
    public String toString() {
        return "Ssn{" +
                "ssn='" + ssn + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
