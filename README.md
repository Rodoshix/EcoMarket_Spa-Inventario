# EcoMarket SPA - Microservicio de Inventario

Este repositorio contiene el microservicio de **gestión de inventario** para el sistema EcoMarket SPA del proyecto semestral FullStack_1. Este componente permite registrar, consultar, actualizar y eliminar productos del catálogo utilizando **Spring Boot**, **Spring Data JPA** y **MySQL**.

---

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Lombok
- MySQL
- Maven

---

## Configuración del entorno

### Base de datos

- Motor: MySQL (MariaDB compatible)
- Nombre: `inventario_db`
- Usuario: `root`
- Contraseña: *(vacía por defecto en XAMPP)*

### Archivo `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventario_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8082
```

---

## Funcionalidades

- Registrar productos nuevos
- Sumar stock a productos existentes
- Buscar productos por ID, nombre o categoría
- Eliminar productos
- Descontar stock desde `venta-service` al confirmar una venta

---

## Integración con venta-service

Cuando se registra una venta, el microservicio `venta-service` llama a este endpoint para descontar stock. El método `guardarProducto(...)` **no debe usarse para ventas**.

---

##  Endpoints principales

| Método | Endpoint                                       | Descripción                         |
|--------|------------------------------------------------|-------------------------------------|
| GET    | `/api/productos`                               | Listar todos los productos          |
| GET    | `/api/productos/{id}`                          | Obtener producto por ID             |
| POST   | `/api/productos`                               | Crear o actualizar un producto      |
| DELETE | `/api/productos/{id}`                          | Eliminar producto por ID            |
| GET    | `/api/productos/buscar/nombre?nombre=xxx`      | Buscar producto por nombre          |
| GET    | `/api/productos/buscar/categoria?categoria=xxx`| Buscar productos por categoría      |

---

## Pruebas con Postman

Importa la colección incluida:  
**`EcoMarket - Inventario-service.postman_collection.json`**

Incluye pruebas para:

- Crear producto
- Obtener todos
- Buscar por nombre/categoría
- Eliminar por ID

---
