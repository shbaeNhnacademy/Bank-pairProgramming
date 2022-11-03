package com.nhnacademy.project;

import com.nhnacademy.project.bank.Bank;
import com.nhnacademy.project.bank.IbkBank;
import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.CalculateDiffernetCurrencyException;
import com.nhnacademy.project.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

class BankTest {

    //SUT
    Bank bank;

    // TODO: composite 패턴을 사용하여, Bank interface를 받은 여러 은행에 대한 테스트 작성.
    @BeforeEach
    void setUp() {
        bank = new IbkBank();
    }

    @Test
    @DisplayName("화폐 더하기 성공 - currency 검증")
    void addMoney_success_currencyCheck() {
        double amount = 1000;
        Money money1 = new Money(amount, Currency.KOR);
        Money money2 = new Money(amount, Currency.KOR);

        assertThatCode(() -> bank.addMoney(money1, money2)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("화폐 더하기 성공 - amount 검증")
    void addMoney_success_amountCheck() {
        double amount = 1000;
        Money money1 = new Money(amount, Currency.KOR);
        Money money2 = new Money(amount, Currency.KOR);
        Money addMoney = bank.addMoney(money1, money2);

        assertThat(addMoney.getAmount()).isEqualTo(money1.getAmount() + money2.getAmount());

    }

    @Test
    @DisplayName("화폐 더하기 실패 - currency 다름 ")
    void addMoney_fail_currencyDifferent() {
        double amount = 1000;
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
        double amount1 = 1000;
        double amount2 = 500;
        Money money1 = new Money(amount1, Currency.KOR);
        Money money2 = new Money(amount2, Currency.KOR);

        assertThatCode(() -> bank.subtractMoney(money1, money2)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("화폐 빼기 성공 - amount 검증")
    void subtractMoney_success_amountCheck() {
        double amount1 = 1000;
        double amount2 = 500;
        Money money1 = new Money(amount1, Currency.KOR);
        Money money2 = new Money(amount2, Currency.KOR);
        Money subtractMoney = bank.subtractMoney(money1, money2);

        assertThat(subtractMoney.getAmount()).isEqualTo(money1.getAmount() - money2.getAmount());
    }

    @Test
    @DisplayName("화폐 빼기 실패 - currency 다름 ")
    void subtractMoney_fail_currencyDifferent() {
        double amount1 = 1000;
        double amount2 = 500;
        Money money1 = new Money(amount1, Currency.USD);
        Money money2 = new Money(amount2, Currency.KOR);

        assertThatThrownBy(() -> bank.subtractMoney(money1, money2))
                .isInstanceOf(CalculateDiffernetCurrencyException.class)
                .hasMessageContainingAll("different currency",
                        money1.getCurrency().getNationalCode(),
                        money2.getCurrency().getNationalCode());

    }

    @Test
    @DisplayName("환전 성공 - USD -> KOR")
    void exchangeMoney_usdToKor() {
        double amount = 5.25;       // 5250원 나와야함.
        Money money1 = new Money(amount, Currency.USD);

        //TODO: 수수료 뺀 거 확인.
        assertThat(bank.exchangeMoney(money1, Currency.KOR).getAmount()).isEqualTo(amount * Currency.KOR.getExchangeRate());
    }

    @Test
    @DisplayName("환전 성공 - KOR -> USD")
    void exchangeMoney_korToUsd() {
        double amount = 5000;       // 5원 나와야함.
        Currency originCurrency = Currency.KOR;
        Currency targetCurrency = Currency.USD;
        Money money1 = new Money(amount, originCurrency);


        //TODO: 수수료 뺀 거 확인.
        assertThat(bank.exchangeMoney(money1, targetCurrency).getAmount()).isEqualTo(amount / originCurrency.getExchangeRate());
    }

    @DisplayName("환전 이후의 반올림 - USD -> KOR")
    @ParameterizedTest
    @ValueSource(doubles = {10.005, 10.004, 10.2, 10.7})
    void exchangeMoney_success_rounded(double candidate) {
        Currency originCurrency = Currency.USD;
        Currency targetCurrency = Currency.KOR;
        Money money1 = new Money(candidate, originCurrency);
        Money money = bank.exchangeMoney(money1, targetCurrency);
        assertThat(money.getAmount()).isEqualTo((Math.round(candidate * 100) / 100.0) * targetCurrency.getExchangeRate());
    }


}
