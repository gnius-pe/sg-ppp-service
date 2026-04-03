package com.servicios.sppp.back_end_sppp.servicios;

public class ResultadoOperacion<T> {
    private T data;
    private boolean success;
    private String message;
    private boolean encontrado;

    private ResultadoOperacion(T data, boolean success, String message, boolean encontrado) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.encontrado = encontrado;
    }

    public static <T> ResultadoOperacion<T> exito(T data, String message) {
        return new ResultadoOperacion<>(data, true, message, true);
    }

    public static <T> ResultadoOperacion<T> noEncontrado(String message) {
        return new ResultadoOperacion<>(null, false, message, false);
    }

    public static <T> ResultadoOperacion<T> error(String message) {
        return new ResultadoOperacion<>(null, false, message, false);
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isEncontrado() {
        return encontrado;
    }
}