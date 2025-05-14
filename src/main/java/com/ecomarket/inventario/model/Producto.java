package com.ecomarket.inventario.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok: genera getters, setters, toString, equals y hashCode
@AllArgsConstructor // Lombok: constructor con todos los campos
@NoArgsConstructor // Lombok: constructor vacío necesario para JPA
@Entity // Marca esta clase como una entidad JPA
@Table(name = "productos") // Define el nombre de la tabla en la base de datos
public class Producto {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremento desde la BD
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private int cantidadEnStock;
    private double precioUnitario;
    private String proveedor;
}