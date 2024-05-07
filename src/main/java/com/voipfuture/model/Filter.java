package com.voipfuture.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.voipfuture.model.filter.InvalidFilterException;
import org.apache.commons.lang3.Validate;

import java.time.OffsetDateTime;
import java.util.List;

public class Filter {
    @JsonProperty("date")
    private OffsetDateTime date;

    @JsonProperty("components")
    private List<Component> components;

    public OffsetDateTime getDate() {
        return date;
    }


    public List<Component> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "time=" + date +
                ", components=" + components +
                '}';
    }

    public static void validateFilter(Filter filter) throws InvalidFilterException {
        Validate.notNull(filter.getDate(), "date is not allowed to be null");
        Validate.notNull(filter.getComponents(), "components list is not allowed to be null");
        Validate.isTrue(!filter.getComponents().isEmpty(), "components need to contain at least one element");
    }
}