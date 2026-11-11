package com.project.back_horus.service;

import com.project.back_horus.dto.TaskDTO;
import com.project.back_horus.entity.Task;
import com.project.back_horus.exception.ResourceNotFoundException;
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

    // ========= METODOS ==============================================

    public TaskDTO crearTarea(TaskDTO taskDTO) {
        // 1. Convertir DTO -> Entidad (sin id ni fechaCreacion, los pone la BD)
        Task task = toEntity(taskDTO);
        // 2. Guardar en base de datos y obtener la entidad con id asignado
        Task saved = taskRepository.save(task);
        // 3. Convertir entidad guardada -> DTO para devolver al cliente
        return toDTO(saved);
    }

    public List<TaskDTO> obtenerTareas() {
        return taskRepository.findAll().stream()   // 1. Obtener todas las entidades de la BD
                .map(this::toDTO)                  // 2. Convertir cada entidad a DTO
                .collect(Collectors.toList());     // 3. Recolectar en lista y devolver
    }

    public TaskDTO obtenerTareaPorId(Long id) {
        return taskRepository.findById(id)                      // 1. Buscar entidad por id en la BD
                .map(this::toDTO)                               // 2. Si existe, convertir a DTO
                .orElseThrow(() -> new ResourceNotFoundException( // 3. Si no existe, lanzar excepción
                        "No se encontró la tarea con id " + id));
    }

    public TaskDTO actualizarTarea(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)                      // 1. Buscar la tarea existente
                .map(existente -> {                             // 2. Si existe...
                    existente.setTitulo(taskDTO.getTitulo());   //    actualizar título
                    existente.setCompletado(taskDTO.isCompletado());//    actualizar completada
                    Task saved = taskRepository.save(existente); // 3. Guardar cambios
                    return toDTO(saved);                        // 4. Convertir a DTO y devolver
                })
                .orElseThrow(() -> new ResourceNotFoundException( // 5. Si no existe, lanzar excepción
                        "No se encontró la tarea con id " + id));
    }

    public boolean eliminarTarea(Long id) {
        if (!taskRepository.existsById(id)) {    // 1. Verificar si no existe
            throw new ResourceNotFoundException("No se encontró la tarea con id " + id); // 2. Si no existe, lanzar excepción
        }
        taskRepository.deleteById(id);      // 3.Si existe  borrar de la base de datos
        return true;                        // 4. Devolver true
    }

    //*******************************************************************************

    // ================ METODOS CONVERSORES =========================================

    private TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitulo(task.getTitulo());
        dto.setCompletado(task.isCompletado());
        dto.setFechaCreacion(task.getFechaCreacion());
        return dto;
    }

    private Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitulo(taskDTO.getTitulo());
        task.setCompletado(taskDTO.isCompletado());
        // id y fechaCreacion se dejan sin asignar: la base de datos y @PrePersist los rellenan
        return task;
    }
}
