package com.r3tr0.moneyassistant.core.exceptions.wallets;

public class WalletNotExistedException extends RuntimeException {
    public WalletNotExistedException() {
        super("This wallet does not exist");
    }

    public WalletNotExistedException(Throwable cause) {
        super("This wallet does not exist", cause);
    }
}
