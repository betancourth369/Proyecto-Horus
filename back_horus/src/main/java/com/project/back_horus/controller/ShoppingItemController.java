package com.project.back_horus.controller;

import com.project.back_horus.dto.ShoppingItemDTO;
import com.project.back_horus.service.ShoppingItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-items")
@AllArgsConstructor
public class ShoppingItemController {

    private final ShoppingItemService shoppingItemService;

    // ================================================================
    // LISTAR TODOS LOS ARTÍCULOS
    // ================================================================
    @GetMapping
    public ResponseEntity<List<ShoppingItemDTO>> obtenerArticulos() {
        return ResponseEntity.ok(shoppingItemService.obtenerArticulos());
    }

    // ================================================================
    // CREAR UN NUEVO ARTÍCULO
    // ================================================================
    @PostMapping
    public ResponseEntity<ShoppingItemDTO> crearArticulo(@RequestBody @Valid ShoppingItemDTO shoppingItemDTO) {
        return ResponseEntity.status(201).body(shoppingItemService.crearArticulo(shoppingItemDTO));
    }

    // ================================================================
    // OBTENER UN ARTÍCULO POR SU ID
    // ================================================================
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingItemDTO> obtenerArticuloPorId(@PathVariable Long id) {
        return ResponseEntity.ok(shoppingItemService.obtenerArticuloPorId(id));
    }

    // ================================================================
    // ACTUALIZAR UN ARTÍCULO POR SU ID
    // ================================================================
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingItemDTO> actualizarArticulo(
            @PathVariable Long id,
            @RequestBody @Valid ShoppingItemDTO shoppingItemDTO) {

        return ResponseEntity.ok(shoppingItemService.actualizarArticulo(id, shoppingItemDTO));
    }

    // ================================================================
    // BORRAR UN ARTÍCULO POR SU ID
    // ================================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArticulo(@PathVariable Long id) {
        boolean eliminado = shoppingItemService.eliminarArticulo(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 Sin Contenido
        } else {
            return ResponseEntity.notFound().build();  // 404 No Encontrado
        }
    }
}
