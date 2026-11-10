package com.project.back_horus.service;

import com.project.back_horus.dto.ShoppingItemDTO;
import com.project.back_horus.entity.ShoppingItem;
import com.project.back_horus.repository.ShoppingItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShoppingItemService {

    private final ShoppingItemRepository shoppingItemRepository;

    // ========= METODOS ==============================================

    public ShoppingItemDTO crearArticulo(ShoppingItemDTO shoppingItemDTO){
        ShoppingItem shoppingItem = toEntity(shoppingItemDTO);
        ShoppingItem saved =shoppingItemRepository.save(shoppingItem);
        return toDto(saved);
    }

    public List<ShoppingItemDTO> obtenerArticulos(){
        return shoppingItemRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ShoppingItemDTO> obtenerArticuloPorId(Long id){
        return shoppingItemRepository.findById(id)
                .map(this::toDto);
    }

    public Optional<ShoppingItemDTO> actualizarArticulo(Long id, ShoppingItemDTO shoppingItemDTO){
        return shoppingItemRepository.findById(id)
                .map(existente ->{
                    existente.setNombre(shoppingItemDTO.getNombre());
                    existente.setComprado(shoppingItemDTO.isComprado());
                    ShoppingItem saved = shoppingItemRepository.save(existente);
                    return toDto(saved);
                });
    }

    public boolean eliminarArticulo(Long id) {
        if (shoppingItemRepository.existsById(id)) {
            shoppingItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ================ METODOS CONVERSORES =========================================

    private ShoppingItemDTO toDto(ShoppingItem item) {
        ShoppingItemDTO dto = new ShoppingItemDTO();
        dto.setId(item.getId());
        dto.setNombre(item.getNombre());
        dto.setCantidad(item.getCantidad());
        dto.setUnidad(item.getUnidad());
        dto.setComprado(item.isComprado());
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
