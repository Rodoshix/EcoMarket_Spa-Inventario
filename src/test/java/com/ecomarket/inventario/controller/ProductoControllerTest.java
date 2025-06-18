package com.ecomarket.inventario.controller;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.services.ProductoService;
import com.ecomarket.inventario.dto.ActualizarStockDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@ActiveProfiles("test")
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/productos - listar todos")
    void listarProductos() throws Exception {
        Producto p1 = new Producto(1L, "Producto A", "Desc A", "Categoria A", 10, 1000.0, "Proveedor A");
        Producto p2 = new Producto(2L, "Producto B", "Desc B", "Categoria B", 20, 2000.0, "Proveedor B");

        Mockito.when(productoService.obtenerTodos()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("GET /api/productos/{id} - encontrado")
    void obtenerPorId_Encontrado() throws Exception {
        Producto p = new Producto(1L, "Producto A", "Desc", "Cat", 10, 999.0, "Prov");
        Mockito.when(productoService.buscarPorId(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/productos/{id} - no encontrado")
    void obtenerPorId_NoEncontrado() throws Exception {
        Mockito.when(productoService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/productos - guardar producto")
    void guardarProducto() throws Exception {
        Producto nuevo = new Producto(null, "Nuevo", "Desc", "Cat", 5, 1200.0, "Prov");
        Producto guardado = new Producto(10L, "Nuevo", "Desc", "Cat", 5, 1200.0, "Prov");

        Mockito.when(productoService.guardarProducto(any(Producto.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    @DisplayName("DELETE /api/productos/{id}")
    void eliminarProducto() throws Exception {
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/productos/buscar/nombre")
    void buscarPorNombre() throws Exception {
        Producto p = new Producto(1L, "Eco", "desc", "cat", 1, 100.0, "prov");
        Mockito.when(productoService.buscarPorNombre("Eco")).thenReturn(p);

        mockMvc.perform(get("/api/productos/buscar/nombre").param("nombre", "Eco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Eco"));
    }

    @Test
    @DisplayName("GET /api/productos/buscar/categoria")
    void buscarPorCategoria() throws Exception {
        Producto p1 = new Producto(1L, "A", "desc", "Eco", 2, 10.0, "prov");
        Producto p2 = new Producto(2L, "B", "desc", "Eco", 2, 20.0, "prov");
        Mockito.when(productoService.buscarPorCategoria("Eco")).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/productos/buscar/categoria").param("categoria", "Eco"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("POST /api/productos/stock")
    void actualizarStock() throws Exception {
        ActualizarStockDTO dto = new ActualizarStockDTO();
        dto.setIdProducto(1L);
        dto.setCantidadVendida(3);

        mockMvc.perform(post("/api/productos/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock actualizado correctamente"));
    }
}