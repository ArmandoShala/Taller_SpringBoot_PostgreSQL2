package Producto.demo.helpers;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ApiErrorResponse {

    private String mensaje;
    private int status;
    private LocalDateTime timestamp;
    private Map<String, String> errores;
}
