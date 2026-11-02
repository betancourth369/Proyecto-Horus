package com.project.back_horus.controller;

import com.project.back_horus.dto.TaskDTO;
import com.project.back_horus.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ================================================================
    // LISTAR TODAS (ya existía)
    // ================================================================
    @GetMapping
    public ResponseEntity<List<TaskDTO>> obtenerTareas() {
        return ResponseEntity.ok(taskService.obtenerTareas());
    }

    // ================================================================
    // CREAR (ya existía)
    // ================================================================
    @PostMapping
    public ResponseEntity<TaskDTO> crearTarea(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(201).body(taskService.crearTarea(taskDTO));
    }

    // ================================================================
    // OBTENER POR ID (ya existía)
    // ================================================================
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> obtenerTareaPorId(@PathVariable Long id) {
        return taskService.obtenerTareaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================================================================
    // NUEVO: ACTUALIZAR POR ID
    // Recibe el id en la URL y el nuevo contenido en el cuerpo JSON.
    // Si la tarea existe, la actualiza y devuelve 200 con el DTO.
    // Si no existe, devuelve 404.
    // ================================================================
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> actualizarTarea(
            @PathVariable Long id,
            @RequestBody TaskDTO taskDTO) {

        return taskService.actualizarTarea(id, taskDTO)
                .map(ResponseEntity::ok)           // si existe -> 200 + DTO actualizado
                .orElse(ResponseEntity.notFound().build()); // si no -> 404
    }

    // ================================================================
    // NUEVO: BORRAR POR ID
    // Recibe el id en la URL.
    // Si la tarea existe, la borra y devuelve 204 (sin contenido).
    // Si no existe, devuelve 404.
    // ================================================================
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
