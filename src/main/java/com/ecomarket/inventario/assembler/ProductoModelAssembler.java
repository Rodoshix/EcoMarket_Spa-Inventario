package com.ecomarket.inventario.assembler;

import com.ecomarket.inventario.controller.ProductoControllerV2;
import com.ecomarket.inventario.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).obtenerProducto(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).listarProductos()).withRel("productos"));
    }
}