package com.nhnacademy.project.bank;

public enum FeeRate {
    IBK("Ibk", 0.1)
    ;

    private final String bankName;
    private final double feeRate;

    FeeRate(String bankName, double feeRate) {
        this.bankName = bankName;
        this.feeRate = feeRate;
    }

    public String getBankName() {
        return bankName;
    }

    public double getFeeRate() {
        return feeRate;
    }
}
