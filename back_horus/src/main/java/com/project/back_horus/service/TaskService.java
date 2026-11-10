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

    public Optional<TaskDTO> obtenerTareaPorId(Long id) {
        return taskRepository.findById(id)   // 1. Buscar entidad por id en la BD
                .map(this::toDTO);           // 2. Si existe, convertir a DTO
    }

    public Optional<TaskDTO> actualizarTarea(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id)                      // 1. Buscar la tarea existente
                .map(existente -> {                             // 2. Si existe...
                    existente.setTitulo(taskDTO.getTitulo());   //    actualizar título
                    existente.setCompletado(taskDTO.isCompletado());//    actualizar completada
                    Task saved = taskRepository.save(existente); // 3. Guardar cambios
                    return toDTO(saved);                        // 4. Convertir a DTO y devolver
                });
    }

    public boolean eliminarTarea(Long id) {
        if (taskRepository.existsById(id)) {    // 1. Verificar que existe
            taskRepository.deleteById(id);      // 2. Borrar de la base de datos
            return true;                        // 3. Devolver true
        }
        return false;                           // 4. Devolver false si no existía
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
