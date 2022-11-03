package com.nhnacademy.project.currency;

public enum Currency {
    KOR("KOR",1),
    USD("USD",1000);

    private final String nationalCode;
    private final int exchangeRate;

    Currency(String nationalCode, int exchangeRate) {
        this.nationalCode = nationalCode;
        this.exchangeRate = exchangeRate;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public int getExchangeRate() {
        return exchangeRate;
    }
}
