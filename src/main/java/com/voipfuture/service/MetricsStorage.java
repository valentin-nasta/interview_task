package com.voipfuture.service;

import com.voipfuture.model.Component;
import com.voipfuture.model.Filter;
import com.voipfuture.model.Metric;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Map;

public interface MetricsStorage {
    Uni<Map<Component, List<Metric>>> getMetricsByFilter(Filter filter);

    void registerListener(MetricsListener listener);
}
