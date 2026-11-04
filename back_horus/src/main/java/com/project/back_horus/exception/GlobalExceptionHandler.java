package com.project.back_horus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validadorDeExcepciones(MethodArgumentNotValidException excepcion) {

        Map<String, String> errores = new HashMap<>();// Recorre todos los errores de validación encontrados

        excepcion.getBindingResult().getFieldErrors().forEach((error) -> {
            // Cada error es un FieldError que contiene el nombre del campo y el mensaje
            String campo = error.getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        // Devuelve 400 Bad Request con el mapa de errores en JSON
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
