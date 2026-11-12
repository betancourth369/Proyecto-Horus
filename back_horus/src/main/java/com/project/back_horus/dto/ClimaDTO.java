package com.project.back_horus.dto;

import lombok.Data;

@Data
public class ClimaDTO {
    private String ciudad;
    private double temperatura;
    private String descripcion;
    private int humedad;
    private double sensacionTermica;
    private String region;
    private String pais;
    private String horaLocal;
}