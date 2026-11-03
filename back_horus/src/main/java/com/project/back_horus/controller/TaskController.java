package com.project.back_horus.controller;

import com.project.back_horus.dto.TaskDTO;
import com.project.back_horus.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    public ResponseEntity<TaskDTO> crearTarea(@RequestBody @Valid TaskDTO taskDTO) {
        return ResponseEntity.status(201).body(taskService.crearTarea(taskDTO));
    }


    @GetMapping
    public ResponseEntity<List<TaskDTO>> obtenerTareas() {
        return ResponseEntity.ok(taskService.obtenerTareas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> obtenerTareaPorId(@PathVariable Long id) {
        return taskService.obtenerTareaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> actualizarTarea(
            @PathVariable Long id,
            @RequestBody @Valid TaskDTO taskDTO) {

        return taskService.actualizarTarea(id, taskDTO)
                .map(ResponseEntity::ok)           // si existe -> 200 + DTO actualizado
                .orElse(ResponseEntity.notFound().build()); // si no -> 404
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        boolean eliminado = taskService.eliminarTarea(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 Sin Contenido
        } else {
            return ResponseEntity.notFound().build();  // 404 No Encontrado
        }
    }
}
