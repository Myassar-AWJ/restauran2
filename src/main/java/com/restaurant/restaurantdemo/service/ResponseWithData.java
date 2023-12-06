package com.restaurant.restaurantdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseWithData<T> {
    private final String message;
    private T data;
    private List<String> errors;


    public ResponseWithData(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // Constructor for error response with a list of errors
    public ResponseWithData(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
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

    @JsonProperty("errors")
    public List<String> getErrors() {
        return errors;
    }
}
