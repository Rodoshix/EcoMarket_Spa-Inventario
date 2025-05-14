package com.ecomarket.inventario.controller;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos") // Ruta base para este controlador
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar producto
    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardarProducto(producto));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<Producto> buscarPorNombre(@RequestParam String nombre) {
        Producto producto = productoService.buscarPorNombre(nombre);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar por categoría
    @GetMapping("/buscar/categoria")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
    }
}