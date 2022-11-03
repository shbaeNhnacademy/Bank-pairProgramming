package com.nhnacademy.project.exception;

public class InvalidMoneyAmountException extends RuntimeException {
    public InvalidMoneyAmountException(int amount) {
        super("Invalid money amount: " + amount);
    }
}
