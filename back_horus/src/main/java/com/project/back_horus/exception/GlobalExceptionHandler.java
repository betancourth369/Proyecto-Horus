package com.project.back_horus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Captura excepciones globales de la API y las convierte en respuestas HTTP con
 * código de estado adecuado, en lugar de dejar que lleguen al cliente como un
 * error 500 genérico.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Se ejecuta cuando Spring detecta que un objeto anotado con @Valid
     * (por ejemplo, un TaskDTO) no cumple las reglas de validación (ej. @NotBlank).
     *
     * @param excepcion la excepción lanzada por Spring al fallar la validación
     * @return una respuesta HTTP 400 Bad Request con un mapa de campo → mensaje de error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validadorDeExcepciones(MethodArgumentNotValidException excepcion) {
        Map<String, String> errores = new HashMap<>();

        // Recorre todos los errores de validación encontrados
        excepcion.getBindingResult().getAllErrors().forEach((error) -> {
            // Cada error es un FieldError que contiene el nombre del campo y el mensaje
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        // Devuelve 400 Bad Request con el mapa de errores en JSON
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
