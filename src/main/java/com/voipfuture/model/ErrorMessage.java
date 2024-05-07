package com.voipfuture.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {
    @JsonProperty("err_message")
    private String message;

    @JsonProperty("err_code")
    private int code;

    public ErrorMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
