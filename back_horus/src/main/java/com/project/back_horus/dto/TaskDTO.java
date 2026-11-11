package com.project.back_horus.dto;

import lombok.Data;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;

@Data
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Titulo vacio mi so, llenelo o paila!!")
    private String titulo;

    private boolean completado;

    private Instant fechaCreacion;
}
