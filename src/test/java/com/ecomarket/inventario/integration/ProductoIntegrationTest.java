package com.ecomarket.inventario.integration;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.ProductoRepositoryJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductoRepositoryJPA productoRepository;

    @BeforeEach
    void setup() {
        productoRepository.deleteAll();
        productoRepository.save(new Producto(null, "Integrado", "desc", "cat", 3, 1990.0, "prov"));
    }

    @Test
    @DisplayName("GET /api/productos - integración")
    void testGetProductos() {
        ResponseEntity<Producto[]> response = restTemplate.getForEntity("/api/productos", Producto[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(Arrays.stream(response.getBody()).anyMatch(p -> p.getNombre().equals("Integrado"))).isTrue();
    }
}