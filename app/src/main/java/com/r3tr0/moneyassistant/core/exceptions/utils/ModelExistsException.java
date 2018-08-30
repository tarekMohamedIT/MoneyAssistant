package com.r3tr0.moneyassistant.core.exceptions.utils;

public class ModelExistsException extends RuntimeException {
    public ModelExistsException() {
        super("This model already exists in this manager");
    }

    public ModelExistsException(Throwable cause) {
        super("This model already exists in this manager", cause);
    }
}
