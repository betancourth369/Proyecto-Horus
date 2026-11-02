package com.project.back_horus.service;

import com.project.back_horus.dto.TaskDTO;
import com.project.back_horus.entity.Task;
import com.project.back_horus.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDTO> obtenerTareas() {
        return taskRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO crearTarea(TaskDTO taskDTO) {
        Task task = toEntity(taskDTO);
        Task saved = taskRepository.save(task);
        return toDTO(saved);
    }

    private TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }

    private Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.isCompleted()); // id y createdAt se dejan sin asignar:
                                                  // la base de datos y @PrePersist los rellenan
        return task;
    }
}
