package com.voipfuture.service;

import com.voipfuture.model.Component;
import com.voipfuture.model.Filter;
import com.voipfuture.model.Metric;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@ApplicationScoped
public class MetricsStorageImpl implements MetricsStorage {
    private static final OffsetDateTime START_TIME = OffsetDateTime.now().minusDays(7).truncatedTo(ChronoUnit.DAYS);
    private static final Duration METRIC_POINTS_DURATION = Duration.ofMinutes(1);
    private static final Map<Component, List<Metric>> metrics = new HashMap<>();

    private final List<MetricsListener> listeners = new ArrayList<>();

    void startup(@Observes StartupEvent event) {
        final var now = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Duration diff = Duration.between(START_TIME, now);
        var time = START_TIME;
        while (!diff.isNegative()) {
            final var metricsTime = time;
            Arrays.stream(Component.values()).filter(c -> c != Component.ALL).forEach(
                    component -> metrics.compute(component, (c, metrics) -> metrics == null ? new ArrayList<>() : metrics)
                            .add(new Metric(component, metricsTime)));
            diff = diff.minus(METRIC_POINTS_DURATION);
            time = time.plusNanos(METRIC_POINTS_DURATION.toNanos());
        }
        Log.infof("Dummy metrics data in place with: %s(%s) - %s(%s)", Component.CPU, metrics.get(Component.CPU).size(), Component.RAM, metrics.get(Component.RAM).size());
    }

    @Override
    public Uni<Map<Component, List<Metric>>> getMetricsByFilter(Filter filter) {
        final Map<Component, List<Metric>> result = new HashMap<>();
        final List<Component> components = filter.getComponents();

        metrics.forEach((c, m) -> {
            if (components.contains(c) || components.contains(Component.ALL)) {
                final List<Metric> filteredMetrics = m.stream().filter(metric -> metric.getTime().getDayOfYear() == filter.getDate().getDayOfYear()).toList();
                result.put(c, filteredMetrics);
            }
        });
        return Uni.createFrom().item(result);
    }

    @Override
    public void registerListener(MetricsListener listener) {
        this.listeners.add(listener);
    }

    @Scheduled(cron = "0 * * * * ?")
    void componentsSnapshot() {
        Arrays.stream(Component.values()).filter(c -> c != Component.ALL).forEach(
                component -> {
                    final var metric = new Metric(component);
                    metrics.compute(component, (c, metrics) -> metrics == null ? new ArrayList<>() : metrics).add(metric);
                    listeners.forEach(l -> l.newMetric(metric));
                });
    }
}
