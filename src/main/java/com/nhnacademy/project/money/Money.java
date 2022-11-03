package com.nhnacademy.project.money;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.EqualsDifferentCurrencyException;
import com.nhnacademy.project.exception.InvalidMoneyAmountException;

import java.util.Optional;

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
        return  Math.round(this.amount * 100) / 100.0;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        Money money1 = (Money) Optional.ofNullable(obj).orElseThrow(IllegalArgumentException::new);
        if(!this.getCurrency().getNationalCode().equals(money1.getCurrency().getNationalCode())) {
            throw new EqualsDifferentCurrencyException(this, money1);
        }

        if(this.getAmount() == money1.getAmount()) {
            return true;
        }
        return false;
    }
}
