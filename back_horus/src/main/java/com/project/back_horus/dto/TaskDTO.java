package com.project.back_horus.dto;

import lombok.Data;
import java.time.Instant;
import jakarta.validation.constraints.NotBlank;

@Data
public class TaskDTO {

    private Long id;

    @NotBlank(message = "El titulo no puede estar vacio")
    private String title;

    private boolean completed;

    private Instant createdAt;
}
