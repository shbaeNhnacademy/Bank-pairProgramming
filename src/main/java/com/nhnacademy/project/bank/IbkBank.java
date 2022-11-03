package com.nhnacademy.project.bank;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.CalculateDiffernetCurrencyException;
import com.nhnacademy.project.money.Money;

public class IbkBank implements Bank{
    private final double EXCHANGE_FEERATE = FeeRate.IBK.getFeeRate();

    @Override
    public Money addMoney(Money money1, Money money2) {
        checkSameCurrency(money1, money2);
        double totalAmount = money1.getAmount() + money2.getAmount();
        return new Money(totalAmount, money1.getCurrency());
    }

    @Override
    public Money subtractMoney(Money money1, Money money2) {
        checkSameCurrency(money1, money2);
        double totalAmount = money1.getAmount() - money2.getAmount();
        return new Money(totalAmount, money1.getCurrency());
    }


    //TODO: 환전
    @Override
    public  Money exchangeMoney(Money money1, Currency currency) {
        double exchangeAmount = getExchangeAmount(money1, currency);
        return new Money(exchangeAmount, currency);
    }

    private double getExchangeAmount(Money money1, Currency currency) {
        double exchangeRate = (double) currency.getExchangeRate() / (double) money1.getCurrency().getExchangeRate();
        return money1.getAmount() * exchangeRate;
    }

    private void checkSameCurrency(Money money1, Money money2) {
        if (!money1.getCurrency().getNationalCode().equals(money2.getCurrency().getNationalCode())) {
            throw new CalculateDiffernetCurrencyException(money1, money2);
        }
    }
}
