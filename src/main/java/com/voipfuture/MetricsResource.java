package com.voipfuture;

import com.voipfuture.model.Component;
import com.voipfuture.model.ErrorMessage;
import com.voipfuture.model.Filter;
import com.voipfuture.model.Metric;
import com.voipfuture.service.MetricsStorage;
import io.quarkus.logging.Log;
import io.smallrye.common.constraint.NotNull;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Path("/metrics")
public class MetricsResource {

    @Inject
    MetricsStorage storage;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Map<Component, List<Metric>>> pollMetrics(@NotNull @QueryParam("filter") String filterParam) {
        if (filterParam == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("a base64 encoded filter is required", 1)).build());
        }
        final String filterString = new String(Base64.getDecoder().decode(filterParam));
        Log.debugf(filterString);
        final Filter filter = Json.decodeValue(filterString, Filter.class);
        Filter.validateFilter(filter);
        return storage.getMetricsByFilter(filter);
    }
}
