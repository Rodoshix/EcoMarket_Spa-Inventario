package com.ecomarket.inventario.services;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.ProductoRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service // Marca esta clase como un servicio gestionado por Spring
@Transactional // Marca toda la clase como transaccional
public class ProductoService {

    @Autowired
    private ProductoRepositoryJPA productoRepository;

    // Obtener todos los productos
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // Buscar producto por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar un nuevo producto o actualizar uno existente
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar producto por ID
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // Buscar por nombre
    public Producto buscarPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    // Buscar por categoría
    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }
}