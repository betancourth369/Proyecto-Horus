package com.project.back_horus.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TaskDTO {

    private Long id;

    private String title;

    private boolean completed;

    private Instant createdAt;
}
