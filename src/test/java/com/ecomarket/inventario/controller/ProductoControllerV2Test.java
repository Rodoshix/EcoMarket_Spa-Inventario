package com.ecomarket.inventario.controller;

import com.ecomarket.inventario.assembler.ProductoModelAssembler;
import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.services.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoControllerV2.class)
@ActiveProfiles("test")
class ProductoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v2/productos - listar con HATEOAS")
    void listarProductos() throws Exception {
        Producto p1 = new Producto(1L, "A", "Desc", "Cat", 5, 1000.0, "Prov");
        Producto p2 = new Producto(2L, "B", "Desc", "Cat", 3, 500.0, "Prov");

        EntityModel<Producto> model1 = EntityModel.of(p1,
                Link.of("/api/v2/productos/1").withSelfRel(),
                Link.of("/api/v2/productos").withRel("productos"));

        EntityModel<Producto> model2 = EntityModel.of(p2,
                Link.of("/api/v2/productos/2").withSelfRel(),
                Link.of("/api/v2/productos").withRel("productos"));

        Mockito.when(productoService.obtenerTodos()).thenReturn(Arrays.asList(p1, p2));
        Mockito.when(assembler.toModel(p1)).thenReturn(model1);
        Mockito.when(assembler.toModel(p2)).thenReturn(model2);

        mockMvc.perform(get("/api/v2/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.productoList", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(jsonPath("_embedded.productoList[0]._links.self.href").value("/api/v2/productos/1"))
                .andExpect(jsonPath("_embedded.productoList[1]._links.self.href").value("/api/v2/productos/2"));
    }

    @Test
    @DisplayName("GET /api/v2/productos/{id} - encontrado con HATEOAS")
    void obtenerProducto() throws Exception {
        Producto p = new Producto(1L, "A", "Desc", "Cat", 5, 1000.0, "Prov");

        EntityModel<Producto> model = EntityModel.of(p,
                Link.of("/api/v2/productos/1").withSelfRel(),
                Link.of("/api/v2/productos").withRel("productos"));

        Mockito.when(productoService.buscarPorId(1L)).thenReturn(Optional.of(p));
        Mockito.when(assembler.toModel(p)).thenReturn(model);

        mockMvc.perform(get("/api/v2/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href").value("/api/v2/productos/1"));
    }

    @Test
    @DisplayName("POST /api/v2/productos - crear con HATEOAS")
    void crearProducto() throws Exception {
        Producto nuevo = new Producto(null, "X", "desc", "cat", 4, 1234.0, "prov");
        Producto creado = new Producto(10L, "X", "desc", "cat", 4, 1234.0, "prov");

        EntityModel<Producto> model = EntityModel.of(creado,
                Link.of("/api/v2/productos/10").withSelfRel(),
                Link.of("/api/v2/productos").withRel("productos"));

        Mockito.when(productoService.guardarProducto(any())).thenReturn(creado);
        Mockito.when(assembler.toModel(creado)).thenReturn(model);

        mockMvc.perform(post("/api/v2/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v2/productos/10"))
                .andExpect(jsonPath("_links.self.href").value("/api/v2/productos/10"));
    }

    @Test
    @DisplayName("PUT /api/v2/productos/{id} - actualizar con HATEOAS")
    void actualizarProducto() throws Exception {
        Producto actualizado = new Producto(5L, "Y", "desc", "cat", 7, 3000.0, "prov");

        EntityModel<Producto> model = EntityModel.of(actualizado,
                Link.of("/api/v2/productos/5").withSelfRel(),
                Link.of("/api/v2/productos").withRel("productos"));

        Mockito.when(productoService.buscarPorId(5L)).thenReturn(Optional.of(actualizado));
        Mockito.when(productoService.guardarProducto(any())).thenReturn(actualizado);
        Mockito.when(assembler.toModel(actualizado)).thenReturn(model);

        mockMvc.perform(put("/api/v2/productos/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href").value("/api/v2/productos/5"));
    }

    @Test
    @DisplayName("DELETE /api/v2/productos/{id}")
    void eliminarProducto() throws Exception {
        mockMvc.perform(delete("/api/v2/productos/99"))
                .andExpect(status().isNoContent());
    }
}