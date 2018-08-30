package com.r3tr0.moneyassistant.core.exceptions.wallets;

public class WalletExistsException extends RuntimeException {
    public WalletExistsException() {
        super("A wallet by the same name already exists");
    }

    public WalletExistsException(Throwable cause) {
        super("A wallet by the same name already exists", cause);
    }
}
