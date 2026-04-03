package com.servicios.sppp.back_end_sppp.config;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private T data;
    private boolean success;
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data, boolean success, String message, int status) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, true, "OK", 200);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, true, message, 200);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(data, true, "Resource created", 201);
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(data, true, message, 201);
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(null, false, message, status);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(null, false, message, 404);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(null, false, message, 400);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(null, false, message, 401);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}