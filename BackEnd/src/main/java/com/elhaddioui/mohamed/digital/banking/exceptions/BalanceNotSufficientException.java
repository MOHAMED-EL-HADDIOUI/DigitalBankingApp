package com.elhaddioui.mohamed.digital.banking.exceptions;

public class BalanceNotSufficientException extends  Exception {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
