package com.nhnacademy.project.exception;

import com.nhnacademy.project.money.Money;

public class EqualsDifferentCurrencyException extends RuntimeException {
    public EqualsDifferentCurrencyException(Money m1, Money m2) {
        super("Can not compare different currency: " +
                m1.getCurrency().getNationalCode() + " and " +
                m2.getCurrency().getNationalCode());
    }
}
