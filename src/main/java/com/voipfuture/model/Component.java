package com.voipfuture.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Component {
    ALL,
    CPU,
    RAM;

    @JsonValue
    public String value() {
        return name().toLowerCase();
    }
}
