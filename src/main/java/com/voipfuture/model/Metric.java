package com.voipfuture.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.logging.Log;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Metric {
    public static final String UNIT_CELSIUS = "celsius";
    public static final String UNIT_GB = "GB";

    @JsonProperty(value = "unit")
    private String unit;
    @JsonProperty(value = "value")
    private Integer value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "total")
    private Integer total;
    @JsonProperty(value = "time")
    private OffsetDateTime time;
    @JsonProperty(value = "component")
    private Component component;

    public Metric(Component component) {
        this(component, OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public Metric(Component component, OffsetDateTime time) {
        this.time = time;
        Random random = new Random();
        this.component = component;
        switch (component) {
            case CPU -> {
                this.unit = UNIT_CELSIUS;
                this.value = random.nextInt(50) + 40;
            }
            case RAM -> {
                this.unit = UNIT_GB;
                this.value = random.nextInt(28) + 4;
                this.total = 32;
            }
            default -> {
                Log.infof("Component %s not implemented yet", component);
            }
        }
    }

    public String getUnit() {
        return unit;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getTotal() {
        return total;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public String toString() {
        return "Metric{" +
                "unit='" + unit + '\'' +
                ", value=" + value +
                ", total=" + total +
                ", time=" + time +
                ", component=" + component +
                '}';
    }
}
