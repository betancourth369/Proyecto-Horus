package com.project.back_horus.service;

import com.project.back_horus.dto.ShoppingItemDTO;
import com.project.back_horus.entity.ShoppingItem;
import com.project.back_horus.entity.Task;
import com.project.back_horus.exception.ResourceNotFoundException;
import com.project.back_horus.repository.ShoppingItemRepository;
import com.project.back_horus.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShoppingItemService {

    private final ShoppingItemRepository shoppingItemRepository;
    private final TaskRepository taskRepository;

    // ========= METODOS ==============================================

    public ShoppingItemDTO crearArticulo(ShoppingItemDTO shoppingItemDTO){
        ShoppingItem shoppingItem = toEntity(shoppingItemDTO);
        ShoppingItem saved = shoppingItemRepository.save(shoppingItem);
        return toDto(saved);
    }

    public List<ShoppingItemDTO> obtenerArticulos(){
        return shoppingItemRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ShoppingItemDTO obtenerArticuloPorId(Long id){
        return shoppingItemRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el artículo con id " + id));
    }

    public ShoppingItemDTO actualizarArticulo(Long id, ShoppingItemDTO shoppingItemDTO){
        return shoppingItemRepository.findById(id)
                .map(existente -> {
                    existente.setNombre(shoppingItemDTO.getNombre());
                    existente.setCantidad(shoppingItemDTO.getCantidad());
                    existente.setUnidad(shoppingItemDTO.getUnidad());
                    existente.setComprado(shoppingItemDTO.isComprado());
                    ShoppingItem saved = shoppingItemRepository.save(existente);
                    return toDto(saved);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el artículo con id " + id));
    }

    public boolean eliminarArticulo(Long id) {
        if (shoppingItemRepository.existsById(id)) {
            shoppingItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================================================================
    // NUEVO: OPERACIONES CON TAREA
    // ================================================================

    public ShoppingItemDTO agregarArticuloATarea(Long taskId, ShoppingItemDTO shoppingItemDTO){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id " + taskId));

        ShoppingItem item = new ShoppingItem();
        item.setNombre(shoppingItemDTO.getNombre());
        item.setCantidad(shoppingItemDTO.getCantidad());
        item.setUnidad(shoppingItemDTO.getUnidad());
        item.setComprado(shoppingItemDTO.isComprado());
        item.setTask(task);

        ShoppingItem saved = shoppingItemRepository.save(item);
        return toDto(saved);
    }

    public List<ShoppingItemDTO> obtenerArticulosDeTarea(Long taskId) {
        return shoppingItemRepository.findByTaskId(taskId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ShoppingItemDTO actualizarArticuloEnTarea(Long taskId, Long itemId, ShoppingItemDTO dto) {
        ShoppingItem item = shoppingItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el artículo con id " + itemId));

        if (!item.getTask().getId().equals(taskId)) {
            throw new ResourceNotFoundException("El artículo no pertenece a esta tarea");
        }

        item.setNombre(dto.getNombre());
        item.setCantidad(dto.getCantidad());
        item.setUnidad(dto.getUnidad());
        item.setComprado(dto.isComprado());

        ShoppingItem saved = shoppingItemRepository.save(item);
        return toDto(saved);
    }

    public boolean eliminarArticuloDeTarea(Long taskId, Long itemId) {
        ShoppingItem item = shoppingItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el artículo con id " + itemId));

        if (!item.getTask().getId().equals(taskId)) {
            throw new ResourceNotFoundException("El artículo no pertenece a esta tarea");
        }

        shoppingItemRepository.deleteById(itemId);
        return true;
    }

    // ================ METODOS CONVERSORES =========================================

    private ShoppingItemDTO toDto(ShoppingItem item) {
        ShoppingItemDTO dto = new ShoppingItemDTO();
        dto.setId(item.getId());
        dto.setNombre(item.getNombre());
        dto.setCantidad(item.getCantidad());
        dto.setUnidad(item.getUnidad());
        dto.setComprado(item.isComprado());
        dto.setTaskId(item.getTask() != null ? item.getTask().getId() : null);
        dto.setTaskNombre(item.getTask() != null ? item.getTask().getTitulo() : null);
        return dto;
    }

    private ShoppingItem toEntity(ShoppingItemDTO dto) {
        ShoppingItem item = new ShoppingItem();
        item.setNombre(dto.getNombre());
        item.setCantidad(dto.getCantidad());
        item.setUnidad(dto.getUnidad());
        item.setComprado(dto.isComprado());
        return item;
    }
}