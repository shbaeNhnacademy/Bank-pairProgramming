package com.nhnacademy.project.exception;

public class InvalidMoneyAmountException extends RuntimeException {
    public InvalidMoneyAmountException(double amount) {
        super("Invalid money amount: " + amount);
    }
}
