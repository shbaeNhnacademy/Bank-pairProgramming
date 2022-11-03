package com.nhnacademy.project.money;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.InvalidMoneyAmountException;

public class Money {
    private final int amount;
    private final Currency currency;

    public Money(int amount, Currency currency) {
        if (amount < 0) {
            throw new InvalidMoneyAmountException(amount);
        }
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
