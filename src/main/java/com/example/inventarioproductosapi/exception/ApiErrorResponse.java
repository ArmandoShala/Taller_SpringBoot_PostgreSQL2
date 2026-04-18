package com.example.inventarioproductosapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private final String mensaje;
    private final int status;
    private final LocalDateTime timestamp;
    private final Map<String, String> errores;

    public ApiErrorResponse(String mensaje, int status, LocalDateTime timestamp) {
        this(mensaje, status, timestamp, null);
    }

    public ApiErrorResponse(String mensaje, int status, LocalDateTime timestamp, Map<String, String> errores) {
        this.mensaje = mensaje;
        this.status = status;
        this.timestamp = timestamp;
        this.errores = errores;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrores() {
        return errores;
    }
}
