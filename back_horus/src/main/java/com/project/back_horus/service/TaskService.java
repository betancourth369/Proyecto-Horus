package com.project.back_horus.service;

import com.project.back_horus.entity.Task;
import com.project.back_horus.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> obtenerTareas(){
        return taskRepository.findAll();
    }

    public Task crearTarea(Task task){
        return  taskRepository.save(task);
    }

}
