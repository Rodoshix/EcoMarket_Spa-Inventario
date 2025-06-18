package com.ecomarket.inventario.services;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.ProductoRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepositoryJPA productoRepository;

    private Producto productoBase;

    @BeforeEach
    void setUp() {
        productoBase = new Producto(1L, "Eco", "desc", "cat", 10, 1000.0, "prov");
    }

    @Test
    @DisplayName("Obtener todos los productos")
    void testObtenerTodos() {
        when(productoRepository.findAll()).thenReturn(List.of(productoBase));

        List<Producto> resultado = productoService.obtenerTodos();

        assertEquals(1, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    @DisplayName("Buscar por ID - encontrado")
    void testBuscarPorId_Encontrado() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoBase));

        Optional<Producto> result = productoService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Eco", result.get().getNombre());
    }

    @Test
    @DisplayName("Buscar por ID - obligatorio - no encontrado")
    void testBuscarPorIdObligatorio_NoEncontrado() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                productoService.buscarPorIdObligatorio(99L));

        assertEquals("Producto no encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Guardar producto nuevo")
    void testGuardarProductoNuevo() {
        when(productoRepository.findByNombreAndDescripcion("Eco", "desc")).thenReturn(Optional.empty());
        when(productoRepository.save(productoBase)).thenReturn(productoBase);

        Producto guardado = productoService.guardarProducto(productoBase);

        assertEquals(productoBase.getNombre(), guardado.getNombre());
        verify(productoRepository).save(productoBase);
    }

    @Test
    @DisplayName("Guardar producto existente - sumar stock")
    void testGuardarProductoExistente() {
        Producto existente = new Producto(2L, "Eco", "desc", "cat", 5, 1000.0, "prov");
        when(productoRepository.findByNombreAndDescripcion("Eco", "desc")).thenReturn(Optional.of(existente));
        when(productoRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Producto nuevo = new Producto(null, "Eco", "desc", "cat", 3, 1000.0, "prov");

        Producto resultado = productoService.guardarProducto(nuevo);

        assertEquals(8, resultado.getCantidadEnStock());
    }

    @Test
    @DisplayName("Descontar stock exitoso")
    void testDescontarStock() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoBase));

        productoService.descontarStock(1L, 2);

        assertEquals(8, productoBase.getCantidadEnStock());
        verify(productoRepository).save(productoBase);
    }

    @Test
    @DisplayName("Descontar stock insuficiente")
    void testDescontarStockInsuficiente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoBase));

        assertThrows(RuntimeException.class, () -> productoService.descontarStock(1L, 999));
    }

    @Test
    @DisplayName("Eliminar producto por ID")
    void testEliminarProducto() {
        productoService.eliminarProducto(1L);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Buscar por nombre")
    void testBuscarPorNombre() {
        when(productoRepository.findByNombre("Eco")).thenReturn(productoBase);

        Producto resultado = productoService.buscarPorNombre("Eco");

        assertEquals("Eco", resultado.getNombre());
    }

    @Test
    @DisplayName("Buscar por categoría")
    void testBuscarPorCategoria() {
        when(productoRepository.findByCategoria("cat")).thenReturn(List.of(productoBase));

        List<Producto> resultado = productoService.buscarPorCategoria("cat");

        assertEquals(1, resultado.size());
    }
}