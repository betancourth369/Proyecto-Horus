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

    // ================================================================
    // MANEJADOR DE ERRORES DE VALIDACIÓN (@Valid, @NotBlank, etc.)
    // ================================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
        // ↑ Cuando ocurra esta de excepción, llama a este metodo

    public ResponseEntity<Map<String, String>> validadorDeExcepciones(
            MethodArgumentNotValidException excepcion) {

        // 1. Creo un mapa vacío para guardar los errores
        Map<String, String> errores = new HashMap<>();

        // 2. Recorro cada campo que falló la validación
        excepcion.getBindingResult().getFieldErrors().forEach((error) -> {

            // 3. ¿Qué campo falló? → "titulo"
            String campo = error.getField();

            // 4. ¿Qué mensaje definí en la anotación? → "El título no puede estar vacío"
            String mensaje = error.getDefaultMessage();

            // 5. Lo guardo: {"titulo": "El título no puede estar vacío"}
            errores.put(campo, mensaje);
        });

        // 6. Devuelvo 400 con el mapa como JSON
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // ================================================================
    // MANEJADOR DE RECURSO NO ENCONTRADO (HTTP 404)
    // ================================================================
    @ExceptionHandler(ResourceNotFoundException.class)
        // ↑ Cuando ocurra esta excepción, llama a este metodo

    public ResponseEntity<Map<String, String>> recursoNoEncontrado(
            ResourceNotFoundException excepcion) {

        // 1. Mapa con la info del error
        Map<String, String> error = new HashMap<>();

        // 2. Etiqueta fija → el cliente sabe que es un "no encontrado"
        error.put("error", "Recurso no encontrado");

        // 3. Mensaje dinámico → mensaje en el service
        error.put("mensaje", excepcion.getMessage());

        // 4. Devolver 404 + JSON
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // ================================================================
    // MANEJADOR DE CANTIDAD INVÁLIDA (HTTP 400)
    // ================================================================
    @ExceptionHandler(InvalidQuantityException.class)
        // ↑ Cuando ocurra esta excepción, llama a este metodo

    public ResponseEntity<Map<String, String>> cantidadInvalida(
            InvalidQuantityException excepcion) {

        // 1. Creo un mapa vacío para guardar la info del error
        Map<String, String> error = new HashMap<>();

        // 2. Etiqueta fija → el cliente sabe qué tipo de error es
        error.put("error", "Cantidad inválida");

        // 3. Mensaje dinámico → mensaje en el service
        error.put("mensaje", excepcion.getMessage());

        // 4. Devolver 400 + JSON
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // ================================================================
    // MANEJADOR DE RECURSO DUPLICADO (HTTP 409)
    // ================================================================
    @ExceptionHandler(DuplicateResourceException.class)
        // ↑ Cuando ocurra esta excepción, llama a este metodo

    public ResponseEntity<Map<String, String>> recursoDuplicado(
            DuplicateResourceException excepcion) {

        // 1. Creo un mapa vacío para guardar la info del error
        Map<String, String> error = new HashMap<>();

        // 2. Etiqueta fija → el cliente sabe que es un "duplicado"
        error.put("error", "Recurso duplicado");

        // 3. Mensaje dinámico → mensaje en el service
        error.put("mensaje", excepcion.getMessage());

        // 4. Devolver 409 + JSON
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // ================================================================
    // MANEJADOR GENERAL DE ERRORES DE NEGOCIO (HTTP 400)
    // ================================================================
    // Este debe ir al final porque es el más general: atrapa cualquier
    // BusinessException que no haya sido capturada por los manejadores más específicos de arriba.
    @ExceptionHandler(BusinessException.class)
        // ↑ Cuando ocurra ESTA excepción, llama a este metodo

    public ResponseEntity<Map<String, String>> errorDeNegocio(
            BusinessException excepcion) {

        // 1. Creo un mapa vacío para guardar la info del error
        Map<String, String> error = new HashMap<>();

        // 2. Etiqueta fija → el cliente sabe que es un "error de negocio"
        error.put("error", "Error de negocio");

        // 3. Mensaje dinámico → mensaje en el service
        error.put("mensaje", excepcion.getMessage());

        // 4. Devolver 400 + JSON
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}