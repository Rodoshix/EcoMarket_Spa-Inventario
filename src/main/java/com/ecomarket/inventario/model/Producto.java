package com.ecomarket.inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
@Schema(description = "Entidad que representa un producto en el inventario")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Shampoo ecológico")
    private String nombre;

    @Schema(description = "Descripción detallada del producto", example = "Shampoo natural libre de parabenos")
    private String descripcion;

    @Schema(description = "Categoría a la que pertenece el producto", example = "Higiene")
    private String categoria;

    @Schema(description = "Cantidad disponible en stock", example = "50")
    private int cantidadEnStock;

    @Schema(description = "Precio unitario del producto", example = "3990.50")
    private double precioUnitario;

    @Schema(description = "Proveedor del producto", example = "BioProveedores Ltda.")
    private String proveedor;
}