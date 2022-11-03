package com.nhnacademy.project.money;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.EqualsDifferentCurrencyException;
import com.nhnacademy.project.exception.InvalidMoneyAmountException;

public class Money {
    private final double amount;
    private final Currency currency;

    public Money(double amount, Currency currency) {
        if (amount < 0) {
            throw new InvalidMoneyAmountException(amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public boolean equals(Money money1) {
        if(!this.getCurrency().getNationalCode().equals(money1.getCurrency().getNationalCode())) {
            throw new EqualsDifferentCurrencyException(this, money1);
        }

        if(this.getAmount() == money1.getAmount()) {
            return true;
        }
        return false;
    }
}
