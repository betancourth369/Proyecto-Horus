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

    private String titulo;
    private boolean completado;
    private Instant fechaCreacion;//tipo de fecha UTC

    @PrePersist
    void fillCreatedAt(){ //aqui le doy el valor mediante este IF ya que si es NULL
        if (fechaCreacion == null){
            fechaCreacion = Instant.now();
        }
    }

}
