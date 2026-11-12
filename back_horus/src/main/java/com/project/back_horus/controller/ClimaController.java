package com.project.back_horus.controller;

import com.project.back_horus.dto.ClimaDTO;
import com.project.back_horus.service.ClimaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/clima")
public class ClimaController {

    private final ClimaService climaService;

    @GetMapping
    public ResponseEntity<ClimaDTO> obtenerClima(@RequestParam String ciudad){
        ClimaDTO clima =  climaService.obtenerClima(ciudad);
        return  ResponseEntity.ok(clima);
    }

}
