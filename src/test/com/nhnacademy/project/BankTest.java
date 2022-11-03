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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

class BankTest {

    //SUT
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new IbkBank();
    }

    @Test
    @DisplayName("환율 계산식 검증")
    void exchangeMoney_validation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 환율 계산식이 신뢰할 수 있는지 확인하기 위한 테스트.
        Method exchangeMoney = bank.getClass().getDeclaredMethod("exchangeMoney", Money.class, Currency.class);
        exchangeMoney.setAccessible(true);

        Currency originCurrency = Currency.KOR;
        Currency targetCurrency = Currency.USD;

        double amount = 1000;
        Money money = new Money(amount, originCurrency);

        Money invoke = (Money) exchangeMoney.invoke(bank, money, targetCurrency);

        assertThat(invoke.getAmount()).isEqualTo(1);
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
        double amount = 5.25;
        Money money1 = new Money(amount, Currency.USD);

        assertThat(bank.exchangeMoney(money1, Currency.KOR).getAmount()).isEqualTo(amount * Currency.KOR.getExchangeRate());
    }

    @Test
    @DisplayName("환전 성공 - KOR -> USD")
    void exchangeMoney_korToUsd() {
        double amount = 5000;
        Currency originCurrency = Currency.KOR;
        Currency targetCurrency = Currency.USD;
        Money money1 = new Money(amount, originCurrency);

        assertThat(bank.exchangeMoney(money1, targetCurrency).getAmount()).isEqualTo(amount / originCurrency.getExchangeRate());
    }

    @DisplayName("환전 이후의 반올림 - USD -> KOR")
    @ParameterizedTest
    @ValueSource(doubles = {10.005, 10.004, 10.2, 10.7})
    void exchangeMoney_success_rounded_usdToKor(double candidate) {
        Currency originCurrency = Currency.USD;
        Currency targetCurrency = Currency.KOR;
        Money money1 = new Money(candidate, originCurrency);

        Money exchangeMoney = bank.exchangeMoney(money1, targetCurrency);

        assertThat(exchangeMoney.getAmount()).isEqualTo((Math.round(candidate * 100) / 100.0) * targetCurrency.getExchangeRate());
    }

    @DisplayName("환전 이후의 반올림 - KOR -> USD")
    @ParameterizedTest
    @ValueSource(doubles = {6.0, 5.0, 4.0})
    void exchangeMoney_success_rounded_korToUsd(double candidate) {
        Currency originCurrency = Currency.KOR;
        Currency targetCurrency = Currency.USD;
        Money money1 = new Money(candidate, originCurrency);

        double exchangeAmount = candidate / (double) originCurrency.getExchangeRate();
        Money exchangeMoney = bank.exchangeMoney(money1, targetCurrency);

        assertThat(exchangeMoney.getAmount()).isEqualTo(Math.round(exchangeAmount * 100) / 100.0);
    }

    @DisplayName("엔화 환전 - JPY -> KOR")
    @ParameterizedTest
    @ValueSource(doubles = {250, 251, 1000})
    void exchangeMoney_jpyToKor(double candidate) {
        Currency originCurrency = Currency.JPY;
        Currency targetCurrency = Currency.KOR;
        Money money1 = new Money(candidate, originCurrency);

        double exchangeRate = (double) targetCurrency.getExchangeRate() / (double) originCurrency.getExchangeRate();
        Money exchangeMoney = bank.exchangeMoney(money1, targetCurrency);

        assertThat(exchangeMoney.getAmount()).isEqualTo(candidate * exchangeRate);
    }
}
