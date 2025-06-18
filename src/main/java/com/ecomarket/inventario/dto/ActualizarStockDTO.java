package com.ecomarket.inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para actualizar el stock luego de una venta")
public class ActualizarStockDTO {

    @Schema(description = "ID del producto a actualizar", example = "1")
    private Long idProducto;

    @Schema(description = "Cantidad vendida a descontar del stock", example = "3")
    private int cantidadVendida;
}