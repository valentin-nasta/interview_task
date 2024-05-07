package com.voipfuture;

import com.voipfuture.model.Metric;
import com.voipfuture.service.MetricsListener;
import com.voipfuture.service.MetricsStorage;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.json.Json;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/metrics")
@ApplicationScoped
public class MetricsSocket implements MetricsListener {
    private final Map<String, Session> clients = new HashMap<>();

    void startup(@Observes StartupEvent event, MetricsStorage metricsStorage) {
        metricsStorage.registerListener(this);
    }

    @OnOpen
    public void onOpen(Session session) {
        Log.debugf("onOpen: %s", session);
        this.clients.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        Log.debugf("onClose: %s", session);
        this.clients.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        Log.error("onError: %s", throwable.getMessage(), throwable);
        this.clients.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        Log.debugf("onMessage: %s", message);
    }

    @Override
    public void newMetric(Metric metric) {
        Log.debugf("New metric was added: %s", metric);
        clients.values().forEach(c -> c.getAsyncRemote().sendText(Json.encode(MetricsSocketEventCreator.metrics(metric)), sendResult -> {
            if (!sendResult.isOK()) {
                Log.error(sendResult.getException());
            }
        }));
    }
}
