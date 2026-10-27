package com.project.back_horus.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "tasks")
@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean completed;
    private Instant createdAt;//tipo de fecha UTC

    @PrePersist
    void fillCreatedAt(){ //aqui le doy el valor mediante este IF ya que si es NULL
        if (createdAt == null){
            createdAt = Instant.now();
        }
    }

}
