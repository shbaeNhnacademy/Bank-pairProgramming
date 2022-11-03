package com.nhnacademy.project;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.EqualsDifferentCurrencyException;
import com.nhnacademy.project.exception.InvalidMoneyAmountException;
import com.nhnacademy.project.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("양수의 돈을 제대로 넣었을 때")
    void money_insertNormal() {
        double amount = 1000;
        assertThatCode(() -> new Money(amount, Currency.KOR)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("음수의 돈을 넣었을 때.")
    void money_insertInvalidAmount() {
        double amount = -1000;
        assertThatThrownBy(() -> new Money(amount, Currency.KOR))
                .isInstanceOf(InvalidMoneyAmountException.class)
                .hasMessageContainingAll("Invalid money amount", String.valueOf(amount));
    }

    @Test
    @DisplayName("화폐 equals 성공 - currency 동일")
    void equalsMoney_success_sameCurrency() {
        double amount = 1000;
        Money money1 = new Money(amount, Currency.KOR);
        Money money2 = new Money(amount, Currency.KOR);

        assertThat(money1.equals(money2)).isTrue();
    }

    @Test
    @DisplayName("화폐 equals 실패 - currency 동일")
    void equalsMoney_fail_sameCurrency() {
        double amount1 = 1000;
        double amount2 = 500;
        Money money1 = new Money(amount1, Currency.KOR);
        Money money2 = new Money(amount2, Currency.KOR);

        assertThat(money1.equals(money2)).isFalse();
    }

    @Test
    @DisplayName("화폐 equals 실패 - currency 다름")
    void equalsMoney_success_differentCurrency() {
        double amount = 1000;
        Money money1 = new Money(amount, Currency.KOR);
        Money money2 = new Money(amount, Currency.USD);

        assertThatThrownBy(() -> money1.equals(money2))
                .isInstanceOf(EqualsDifferentCurrencyException.class)
                .hasMessageContainingAll("different currency",
                        money1.getCurrency().getNationalCode(),
                        money2.getCurrency().getNationalCode());
    }

    @Test
    @DisplayName("화폐 equals 실패 - currency 다름")
    void equalsMoney_fail_differentCurrency() {
        double amount1 = 1000;
        double amount2 = 500;
        Money money1 = new Money(amount1, Currency.KOR);
        Money money2 = new Money(amount2, Currency.USD);

        assertThatThrownBy(() -> money1.equals(money2))
                .isInstanceOf(EqualsDifferentCurrencyException.class)
                .hasMessageContainingAll("different currency",
                        money1.getCurrency().getNationalCode(),
                        money2.getCurrency().getNationalCode());
    }

    @ParameterizedTest
    @DisplayName("화폐 소수점 2번째 자리까지 출력")
    @ValueSource(doubles = {1.2345, 1.5678})
    void getAmount_isRounded(double candidate) {
        Money money = new Money(candidate, Currency.USD);
        assertThat(money.getAmount()).isEqualTo(Math.round(candidate * 100) / 100.0);
    }

}
