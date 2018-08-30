package com.r3tr0.moneyassistant.core.exceptions.wallets;

public class WalletInsufficientBalanceException extends RuntimeException {
    public WalletInsufficientBalanceException() {
        super("This wallet has insufficient balance!");
    }

    public WalletInsufficientBalanceException(Throwable cause) {
        super("This wallet has insufficient balance!", cause);
    }
}
