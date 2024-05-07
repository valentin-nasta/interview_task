package com.voipfuture.model.filter;

public class InvalidFilterException extends IllegalArgumentException {
    private static final String MESSAGE_FORMAT = "Invalid value '%s' for '%s' - expected: %s";

    public InvalidFilterException(String key, String value, String expected) {
        super(MESSAGE_FORMAT.formatted(key, value, expected));
    }
}
