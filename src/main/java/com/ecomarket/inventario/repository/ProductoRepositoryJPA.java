package com.ecomarket.inventario.repository;

import com.ecomarket.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Marca esta interfaz como componente de acceso a datos
public interface ProductoRepositoryJPA extends JpaRepository<Producto, Long> {

    // Buscar un producto por su nombre exacto
    Producto findByNombre(String nombre);

    // Buscar una lista de productos por su categoría
    List<Producto> findByCategoria(String categoria);

    // Buscar producto por nombre y descripción
    Optional<Producto> findByNombreAndDescripcion(String nombre, String descripcion);

    // Spring Data JPA ya incluye automáticamente:
    // - findAll()
    // - findById(Long id)
    // - save(Producto producto)
    // - deleteById(Long id)
}
