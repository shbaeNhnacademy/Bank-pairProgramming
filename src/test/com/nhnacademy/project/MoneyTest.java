package com.nhnacademy.project;

import com.nhnacademy.project.currency.Currency;
import com.nhnacademy.project.exception.InvalidMoneyAmountException;
import com.nhnacademy.project.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MoneyTest {

    //SUT

    //DOC
    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("양수의 돈을 제대로 넣었을 때")
    void money_insertNormal() {
        int amount = 1000;
        assertThatCode(() -> new Money(amount, Currency.KOR)).doesNotThrowAnyException();
    }


    @Test
    @DisplayName("음수의 돈을 넣었을 때.")
    void money_insertInvalidAmount() {
        int amount = -1000;

        assertThatThrownBy(() -> new Money(amount, Currency.KOR))
                .isInstanceOf(InvalidMoneyAmountException.class)
                .hasMessageContainingAll("Invalid money amount", String.valueOf(amount));
    }


}
