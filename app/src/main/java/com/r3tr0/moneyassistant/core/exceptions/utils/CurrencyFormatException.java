package com.r3tr0.moneyassistant.core.exceptions.utils;

public class CurrencyFormatException extends RuntimeException {

    public CurrencyFormatException() {
        super("The currency input is not in the right format.");
    }

    public CurrencyFormatException(Throwable cause) {
        super("The currency input is not in the right format.", cause);
    }
}
