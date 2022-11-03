package com.nhnacademy.project.bank;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.money.Money;

public interface Bank {
    Money addMoney(Money money1, Money money2);
    Money subtractMoney(Money money1, Money money2);
    Money exchangeMoney(Money money1, Currency currency);
}
