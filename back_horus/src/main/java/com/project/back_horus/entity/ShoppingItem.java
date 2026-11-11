package com.project.back_horus.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "Shopping_Items")
@Entity
@Data
public class ShoppingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer cantidad;
    private String unidad;
    private boolean comprado;

}
