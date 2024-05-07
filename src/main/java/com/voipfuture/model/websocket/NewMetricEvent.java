package com.voipfuture.model.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.voipfuture.model.Metric;

public class NewMetricEvent extends MetricsSocketEvent {
    @JsonProperty("event_type")
    private MetricsEventType eventType = MetricsEventType.NEW_METRIC;

    @JsonProperty("data")
    private Metric data;

    public void setData(Metric data) {
        this.data = data;
    }
}
