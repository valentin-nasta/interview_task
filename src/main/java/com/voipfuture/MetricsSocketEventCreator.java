package com.voipfuture;

import com.voipfuture.model.Metric;
import com.voipfuture.model.websocket.MetricsSocketEvent;
import com.voipfuture.model.websocket.NewMetricEvent;

public class MetricsSocketEventCreator {

    public static MetricsSocketEvent metrics(Metric metric) {
        final NewMetricEvent event = new NewMetricEvent();
        event.setData(metric);
        return event;
    }
}
