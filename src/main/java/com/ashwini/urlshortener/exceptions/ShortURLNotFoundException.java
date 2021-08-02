package com.ashwini.urlshortener.exceptions;

public class ShortURLNotFoundException extends Exception {
    private String message;
    public ShortURLNotFoundException(String message) {
        super(message);
    }
}
