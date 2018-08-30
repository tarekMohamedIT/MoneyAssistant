package com.r3tr0.moneyassistant.core.exceptions.items;

public class InvalidItemNameException extends RuntimeException {
    public InvalidItemNameException() {
        super("The item's name should be between 3 to 30 characters");
    }

    public InvalidItemNameException(Throwable cause) {
        super("The item's name should be between 3 to 30 characters", cause);
    }
}
