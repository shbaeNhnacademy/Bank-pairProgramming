package com.nhnacademy.project;

import com.nhnacademy.project.bank.Bank;
import com.nhnacademy.project.bank.IbkBank;
import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.CalculateDiffernetCurrencyException;
import com.nhnacademy.project.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class BankTest {

    //SUT
    Bank bank;
    @BeforeEach
    void setUp() {
        bank = new IbkBank();
    }

//    @Test
//    @DisplayName("화폐 equals 성공 ")
//    void

    @Test
    @DisplayName("화폐 더하기 성공 - currency 검증")
    void addMoney_success_currencyCheck() {
        int amount = 1000;
        Money money1 = new Money(amount, Currency.KOR);
        Money money2 = new Money(amount, Currency.KOR);

        assertThatCode(() -> bank.addMoney(money1, money2)).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("화폐 더하기 성공 - amount 검증")
    void addMoney_success_amountCheck() {
        int amount = 1000;
        Money money1 = new Money(amount, Currency.KOR);
        Money money2 = new Money(amount, Currency.KOR);
        Money addMoney = bank.addMoney(money1, money2);

        assertThat(addMoney.getAmount()).isEqualTo(money1.getAmount() + money2.getAmount());

    }

    @Test
    @DisplayName("화폐 더하기 실패 - currency 다름 ")
    void addMoney_fail_currencyDifferent() {
        int amount = 1000;
        Money money1 = new Money(amount, Currency.USD);
        Money money2 = new Money(amount, Currency.KOR);

        assertThatThrownBy(() -> bank.addMoney(money1, money2))
                .isInstanceOf(CalculateDiffernetCurrencyException.class)
                .hasMessageContainingAll("different currency",
                        money1.getCurrency().getNationalCode(),
                        money2.getCurrency().getNationalCode());

    }


    @Test
    @DisplayName("화폐 빼기 성공 - currency 검증")
    void subtractMoney_success_currencyCheck() {
        int amount1 = 1000;
        int amount2 = 500;
        Money money1 = new Money(amount1, Currency.KOR);
        Money money2 = new Money(amount2, Currency.KOR);

        assertThatCode(() -> bank.subtractMoney(money1, money2)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("화폐 빼기 성공 - amount 검증")
    void subtractMoney_success_amountCheck() {
        int amount1 = 1000;
        int amount2 = 500;
        Money money1 = new Money(amount1, Currency.KOR);
        Money money2 = new Money(amount2, Currency.KOR);
        Money subtractMoney = bank.subtractMoney(money1, money2);

        assertThat(subtractMoney.getAmount()).isEqualTo(money1.getAmount() - money2.getAmount());
    }

    @Test
    @DisplayName("화폐 빼기 실패 - currency 다름 ")
    void subtractMoney_fail_currencyDifferent() {
        int amount1 = 1000;
        int amount2 = 500;
        Money money1 = new Money(amount1, Currency.USD);
        Money money2 = new Money(amount2, Currency.KOR);

        assertThatThrownBy(() -> bank.subtractMoney(money1, money2))
                .isInstanceOf(CalculateDiffernetCurrencyException.class)
                .hasMessageContainingAll("different currency",
                        money1.getCurrency().getNationalCode(),
                        money2.getCurrency().getNationalCode());

    }

    @Test
    void exchangeMoney() {
    }
}
