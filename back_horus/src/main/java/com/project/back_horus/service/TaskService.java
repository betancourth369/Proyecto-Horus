package com.project.back_horus.service;

import com.project.back_horus.dto.TaskDTO;
import com.project.back_horus.entity.Task;
import com.project.back_horus.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // ================================================================
    // LISTAR TODAS (ya existía)
    // ================================================================
    public List<TaskDTO> obtenerTareas() {
        return taskRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ================================================================
    // CREAR (ya existía)
    // ================================================================
    public TaskDTO crearTarea(TaskDTO taskDTO) {
        Task task = toEntity(taskDTO);
        Task saved = taskRepository.save(task);
        return toDTO(saved);
    }

    // ================================================================
    // OBTENER POR ID (ya existía)
    // ================================================================
    public Optional<TaskDTO> obtenerTareaPorId(Long id) {
        return taskRepository.findById(id)
                .map(this::toDTO);
    }

    // ================================================================
    // NUEVO: ACTUALIZAR POR ID
    // Busca la tarea por id en la base de datos.
    // Si existe, actualiza su título y estado de completada,
    // guarda los cambios y devuelve el DTO actualizado envuelto en Optional.
    // Si no existe, devuelve Optional.empty().
    // ================================================================
    public Optional<TaskDTO> actualizarTarea(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)                      // 1. Buscar la tarea existente
                .map(existing -> {                              // 2. Si existe...
                    existing.setTitle(taskDTO.getTitle());      //    actualizar título
                    existing.setCompleted(taskDTO.isCompleted());//    actualizar completada
                    Task saved = taskRepository.save(existing); // 3. Guardar cambios
                    return toDTO(saved);                        // 4. Convertir a DTO y devolver
                });
    }

    // ================================================================
    // NUEVO: BORRAR POR ID
    // Verifica si existe la tarea con ese id.
    // Si existe, la borra de la base de datos y devuelve true.
    // Si no existe, devuelve false.
    // ================================================================
    public boolean eliminarTarea(Long id) {
        if (taskRepository.existsById(id)) {    // 1. Verificar que existe
            taskRepository.deleteById(id);      // 2. Borrar de la base de datos
            return true;                        // 3. Devolver true
        }
        return false;                           // 4. Devolver false si no existía
    }

    // ================================================================
    // CONVERSORES (ya existían)
    // ================================================================
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
        task.setCompleted(taskDTO.isCompleted());
        // id y createdAt se dejan sin asignar: la base de datos y @PrePersist los rellenan
        return task;
    }
}
