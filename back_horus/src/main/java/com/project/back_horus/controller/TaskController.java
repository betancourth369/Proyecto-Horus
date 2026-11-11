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
    public ResponseEntity<TaskDTO> crearTarea(@Valid @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(201).body(taskService.crearTarea(taskDTO));
    }


    @GetMapping
    public ResponseEntity<List<TaskDTO>> obtenerTareas() {
        return ResponseEntity.ok(taskService.obtenerTareas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> obtenerTareaPorId(@PathVariable Long id) {
        // El service ya lanza ResourceNotFoundException si no existe,
        // por lo que aquí simplemente envolvemos el resultado en 200 OK.
        return ResponseEntity.ok(taskService.obtenerTareaPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> actualizarTarea(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {

        // El service ya lanza ResourceNotFoundException si no existe,
        // por lo que simplemente envolvemos el resultado actualizado en 200 OK.
        return ResponseEntity.ok(taskService.actualizarTarea(id, taskDTO));
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
