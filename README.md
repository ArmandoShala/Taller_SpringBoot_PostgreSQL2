# Inventario de Productos API

API REST desarrollada con **Spring Boot + PostgreSQL** para gestionar un inventario de productos mediante operaciones CRUD, validaciones de negocio y consultas avanzadas.

---

## 1) Descripción

Este proyecto implementa un backend orientado a la gestión de productos con las siguientes capacidades:

- Alta, consulta, actualización y eliminación de productos.
- Validación de campos obligatorios (nombre, categoría, precio, stock, SKU).
- Control de unicidad de SKU.
- Manejo global de errores con respuestas JSON consistentes.
- Carga inicial de datos de ejemplo para pruebas rápidas.

La API está pensada para prácticas de desarrollo backend con arquitectura por capas (**Controller / Service / Repository**) usando Spring Data JPA.

---

## 2) Tecnologías usadas

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Web**
- **Spring Data JPA**
- **Spring Validation**
- **PostgreSQL**
- **Maven**
- **Lombok**

---

## 3) Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **JDK 17**
- **Maven 3.9+** (o usar el Maven de tu entorno)
- **PostgreSQL 14+** (recomendado)
- Un cliente para probar endpoints (Postman, Insomnia o cURL)

Verificación rápida:

```bash
java -version
mvn -version
psql --version
```

---

## 4) Configuración de PostgreSQL

La aplicación usa estas variables en `application.properties` (con valores por defecto):

- `DB_URL` → `jdbc:postgresql://localhost:5432/inventario_db`
- `DB_USERNAME` → `postgres`
- `DB_PASSWORD` → `postgres`

### 4.1 Crear base de datos

```sql
CREATE DATABASE inventario_db;
```

### 4.2 Configurar credenciales (opcional)

Puedes exportar variables de entorno antes de iniciar la app:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/inventario_db
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
```

> La app está configurada con `spring.jpa.hibernate.ddl-auto=update` y `spring.sql.init.mode=always`, por lo que crea/actualiza la tabla y carga datos desde `data.sql` al iniciar.

---

## 5) Cómo ejecutar el proyecto

Desde la raíz del repositorio:

```bash
mvn spring-boot:run
```

La API quedará disponible por defecto en:

```text
http://localhost:8080
```

### 5.1 Empaquetar y ejecutar JAR

```bash
mvn clean package
java -jar target/inventario-productos-api-0.0.1-SNAPSHOT.jar
```

---

## 6) Estructura del proyecto

```text
src/main/java/com/example/inventarioproductosapi
├── controller
│   └── ProductoController.java
├── service
│   ├── ProductoService.java
│   └── impl
│       └── ProductoServiceImpl.java
├── repository
│   └── ProductoRepository.java
├── model
│   └── Producto.java
├── exception
│   ├── ApiErrorResponse.java
│   ├── DuplicateResourceException.java
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── InventarioProductosApiApplication.java

src/main/resources
├── application.properties
└── data.sql
```

---

## 7) Endpoints

Base URL: `http://localhost:8080`

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/productos` | Listar todos los productos |
| GET | `/productos/{id}` | Obtener producto por ID |
| POST | `/productos` | Crear producto |
| PUT | `/productos/{id}` | Actualizar producto |
| DELETE | `/productos/{id}` | Eliminar producto |
| GET | `/productos/max/precio` | Obtener producto con mayor precio |
| GET | `/productos/categoria/{categoria}` | Filtrar por categoría (case-insensitive) |
| GET | `/productos/en/stock` | Contar productos con stock > 0 |

---

## 8) Consultas avanzadas implementadas

Además del CRUD, el repositorio implementa consultas derivadas de Spring Data JPA:

1. **Producto con mayor precio**
   - Método: `findTopByOrderByPrecioDesc()`
   - Endpoint: `GET /productos/max/precio`

2. **Filtrado por categoría sin importar mayúsculas/minúsculas**
   - Método: `findByCategoriaIgnoreCase(String categoria)`
   - Endpoint: `GET /productos/categoria/{categoria}`

3. **Conteo de productos con stock disponible**
   - Método: `countByStockGreaterThan(0)`
   - Endpoint: `GET /productos/en/stock`

4. **Validación de SKU único en creación/actualización**
   - Método: `existsBySku(String sku)`
   - Resultado: evita duplicados y responde con `409 Conflict`

---

## 9) Ejemplos de uso

### 9.1 Crear producto (POST `/productos`)

**Request**

```json
{
  "nombre": "Monitor 27 4K",
  "descripcion": "Monitor IPS 4K para diseño",
  "precio": 499.99,
  "categoria": "Tecnología",
  "stock": 10,
  "sku": "SKU-TEC-900",
  "fabricante": "VisionPro"
}
```

**Response (201 Created)**

```json
{
  "id": 9,
  "nombre": "Monitor 27 4K",
  "descripcion": "Monitor IPS 4K para diseño",
  "precio": 499.99,
  "categoria": "Tecnología",
  "stock": 10,
  "sku": "SKU-TEC-900",
  "fechaIngreso": "2026-04-18T12:35:10.456",
  "fabricante": "VisionPro"
}
```

---

### 9.2 Actualizar producto (PUT `/productos/{id}`)

**Request**

```json
{
  "nombre": "Monitor 27 4K HDR",
  "descripcion": "Monitor IPS 4K con HDR10",
  "precio": 549.99,
  "categoria": "Tecnología",
  "stock": 8,
  "sku": "SKU-TEC-900",
  "fabricante": "VisionPro"
}
```

**Nota:** `id` y `fechaIngreso` no se actualizan desde la API.

---

### 9.3 Consultar por categoría (GET `/productos/categoria/Tecnología`)

**Response (200 OK)**

```json
[
  {
    "id": 1,
    "nombre": "Laptop Pro 16",
    "descripcion": "Portátil de alto rendimiento para edición y desarrollo",
    "precio": 2899.99,
    "categoria": "Tecnología",
    "stock": 5,
    "sku": "SKU-TEC-001",
    "fechaIngreso": "2026-04-18T12:30:45.123",
    "fabricante": "NovaTech"
  }
]
```

---

### 9.4 Contar productos en stock (GET `/productos/en/stock`)

**Response (200 OK)**

```json
{
  "productosEnStock": 6
}
```

---

### 9.5 Ejemplos cURL rápidos

```bash
# Listar todos
curl -X GET "http://localhost:8080/productos"

# Obtener por ID
curl -X GET "http://localhost:8080/productos/1"

# Crear
curl -X POST "http://localhost:8080/productos" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Teclado Mecánico",
    "descripcion": "Switches táctiles",
    "precio": 120.50,
    "categoria": "Tecnología",
    "stock": 15,
    "sku": "SKU-TEC-901",
    "fabricante": "KeyPro"
  }'

# Eliminar
curl -X DELETE "http://localhost:8080/productos/1"
```

---

## 10) Posibles errores y cómo interpretarlos

La API devuelve errores en formato JSON:

```json
{
  "mensaje": "Texto del error",
  "status": 400,
  "timestamp": "2026-04-18T12:40:00.000",
  "errores": {
    "campo": "detalle"
  }
}
```

> En errores de negocio (`404`, `409`) el campo `errores` puede no aparecer.

### 10.1 `400 Bad Request` (error de validación)

Ocurre cuando faltan campos obligatorios o tienen formato/valor inválido.

Ejemplo:

```json
{
  "mensaje": "Error de validación en la solicitud",
  "status": 400,
  "timestamp": "2026-04-18T12:43:00.000",
  "errores": {
    "precio": "El precio debe ser mayor a 0",
    "nombre": "El nombre es obligatorio"
  }
}
```

### 10.2 `404 Not Found`

Ocurre cuando se consulta/actualiza/elimina un ID inexistente.

Ejemplo:

```json
{
  "mensaje": "Producto no encontrado con id: 9999",
  "status": 404,
  "timestamp": "2026-04-18T12:41:00.000"
}
```

### 10.3 `409 Conflict`

Ocurre cuando hay conflicto de integridad, por ejemplo SKU duplicado.

Ejemplo:

```json
{
  "mensaje": "Ya existe un producto con el SKU: SKU-TEC-001",
  "status": 409,
  "timestamp": "2026-04-18T12:42:00.000"
}
```

### 10.4 `500 Internal Server Error`

Error inesperado del servidor. Revisar logs de aplicación y conexión a base de datos.

---

## 11) Datos iniciales

Al iniciar la aplicación se insertan productos de ejemplo desde `src/main/resources/data.sql` para poder probar los endpoints inmediatamente.

---

## 12) Licencia

Este proyecto incluye archivo `LICENSE` en la raíz del repositorio.
