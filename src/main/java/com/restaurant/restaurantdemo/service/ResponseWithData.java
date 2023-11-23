package com.restaurant.restaurantdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseWithData<T> {
    private final String message;
    private T data;


    public ResponseWithData(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseWithData(String message) {
        this.message = message;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("data")
    public T getData() {
        return data;
    }
}
