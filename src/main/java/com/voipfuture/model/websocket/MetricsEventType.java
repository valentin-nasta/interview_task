package com.voipfuture.model.websocket;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MetricsEventType {
    SNAPSHOT,
    NEW_METRIC;

    @JsonValue
    public String value() {
        return name().toLowerCase();
    }
}
