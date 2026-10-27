package com.project.back_horus.controller;

import com.project.back_horus.entity.Task;
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
    public ResponseEntity<List<Task>> obtenerTareas(){
        return  ResponseEntity.ok(taskService.obtenerTareas());
    }

    @PostMapping
    public ResponseEntity<Task> crearTarea(@RequestBody Task task){
        return ResponseEntity.status(201).body(taskService.crearTarea(task));
    }

}

