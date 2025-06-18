package com.ecomarket.inventario.performance;

import com.ecomarket.inventario.repository.ProductoRepositoryJPA;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductoPerformanceTest {

    @Autowired
    private ProductoRepositoryJPA productoRepository;

    @RepeatedTest(10)
    void medirTiempoDeConsulta() {
        long inicio = System.currentTimeMillis();
        productoRepository.findAll();
        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de consulta: " + (fin - inicio) + " ms");
    }
}