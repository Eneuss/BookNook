package com.booknook.dao;

// a checkout that cannot be completed for a business reason (empty cart, not enough stock)
public class CheckoutException extends Exception {
    private static final long serialVersionUID = 1L;

    public CheckoutException(String message) {
        super(message);
    }
}
