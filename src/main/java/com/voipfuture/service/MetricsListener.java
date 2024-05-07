package com.voipfuture.service;

import com.voipfuture.model.Metric;

public interface MetricsListener {
    void newMetric(Metric metric);
}
