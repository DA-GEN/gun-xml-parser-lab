package com.dagen.gunxml.parser;

public class GunParsingException extends RuntimeException {

    public GunParsingException(String message) {
        super(message);
    }

    public GunParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
