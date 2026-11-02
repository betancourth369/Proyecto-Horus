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

    @GetMapping
    public ResponseEntity<List<TaskDTO>> obtenerTareas() {
        return ResponseEntity.ok(taskService.obtenerTareas());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> crearTarea(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.status(201).body(taskService.crearTarea(taskDTO));
    }
}
