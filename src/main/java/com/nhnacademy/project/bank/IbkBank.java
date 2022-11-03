package com.nhnacademy.project.bank;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.CalculateDiffernetCurrencyException;
import com.nhnacademy.project.money.Money;

public class IbkBank implements Bank{
    private final double EXCHANGE_FEERATE = FeeRate.IBK.getFeeRate();

    @Override
    public Money addMoney(Money money1, Money money2) {
        checkSameCurrency(money1, money2);
        int totalAmount = money1.getAmount() + money2.getAmount();
        return new Money(totalAmount, money1.getCurrency());
    }


    @Override
    public Money subtractMoney(Money money1, Money money2) {
        checkSameCurrency(money1, money2);
        int totalAmount = money1.getAmount() - money2.getAmount();
        return new Money(totalAmount, money1.getCurrency());
    }

    @Override
    public Money exchangeMoney(Money money1, Currency currency) {
        return null;
    }
    private void checkSameCurrency(Money money1, Money money2) {
        if (!money1.getCurrency().getNationalCode().equals(money2.getCurrency().getNationalCode())) {
            throw new CalculateDiffernetCurrencyException(money1, money2);
        }
    }


}
