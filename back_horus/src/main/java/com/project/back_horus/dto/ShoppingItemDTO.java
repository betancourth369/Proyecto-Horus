package com.project.back_horus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingItemDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private Integer cantidad;

    private String unidad;

    private boolean comprado;

    private Long taskId;
    private String taskNombre;
}