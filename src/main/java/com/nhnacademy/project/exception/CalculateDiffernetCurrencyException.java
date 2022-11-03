package com.nhnacademy.project.exception;

import com.nhnacademy.project.money.Money;

public class CalculateDiffernetCurrencyException extends RuntimeException{
    public CalculateDiffernetCurrencyException(Money m1, Money m2) {
        super("Can not calculate different currency: " +
                m1.getCurrency().getNationalCode() + " and " +
                m2.getCurrency().getNationalCode());
    }
}
