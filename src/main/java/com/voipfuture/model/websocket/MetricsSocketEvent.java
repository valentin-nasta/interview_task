package com.voipfuture.model.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public abstract class MetricsSocketEvent {
    @JsonProperty("event_time")
    private OffsetDateTime eventTime = OffsetDateTime.now();
}
