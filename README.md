# Guía de prueba rápida — API de Inventario de Productos

> Base URL local sugerida: `http://localhost:8080`

## 1) Endpoints disponibles

| Método | URL | Descripción |
|---|---|---|
| GET | `/productos` | Lista todos los productos. |
| GET | `/productos/{id}` | Obtiene un producto por su ID. |
| POST | `/productos` | Crea un nuevo producto. |
| PUT | `/productos/{id}` | Actualiza un producto existente por ID. |
| DELETE | `/productos/{id}` | Elimina un producto por ID. |
| GET | `/productos/max/precio` | Obtiene el producto con mayor precio. |
| GET | `/productos/categoria/{categoria}` | Lista productos filtrados por categoría (ignorando mayúsculas/minúsculas). |
| GET | `/productos/en/stock` | Devuelve la cantidad de productos con stock mayor a 0. |

---

## 2) Ejemplos de body JSON (POST y PUT)

### POST `/productos` (crear)

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

### PUT `/productos/{id}` (actualizar)

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

> Nota: para `PUT`, envía todos los campos editables. `id` y `fechaIngreso` no se modifican desde la API.

---

## 3) Ejemplos de respuesta esperada

### Éxito — GET `/productos/1` (200 OK)

```json
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
```

### Éxito — POST `/productos` (201 Created)

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

### Éxito — DELETE `/productos/9` (204 No Content)

Sin cuerpo de respuesta.

### Éxito — GET `/productos/en/stock` (200 OK)

```json
{
  "productosEnStock": 6
}
```

---

## 4) Ejemplos cURL por endpoint

### GET `/productos`

```bash
curl -X GET "http://localhost:8080/productos"
```

### GET `/productos/{id}`

```bash
curl -X GET "http://localhost:8080/productos/1"
```

### POST `/productos`

```bash
curl -X POST "http://localhost:8080/productos" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Monitor 27 4K",
    "descripcion": "Monitor IPS 4K para diseño",
    "precio": 499.99,
    "categoria": "Tecnología",
    "stock": 10,
    "sku": "SKU-TEC-900",
    "fabricante": "VisionPro"
  }'
```

### PUT `/productos/{id}`

```bash
curl -X PUT "http://localhost:8080/productos/9" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Monitor 27 4K HDR",
    "descripcion": "Monitor IPS 4K con HDR10",
    "precio": 549.99,
    "categoria": "Tecnología",
    "stock": 8,
    "sku": "SKU-TEC-900",
    "fabricante": "VisionPro"
  }'
```

### DELETE `/productos/{id}`

```bash
curl -X DELETE "http://localhost:8080/productos/9"
```

### GET `/productos/max/precio`

```bash
curl -X GET "http://localhost:8080/productos/max/precio"
```

### GET `/productos/categoria/{categoria}`

```bash
curl -X GET "http://localhost:8080/productos/categoria/Tecnología"
```

### GET `/productos/en/stock`

```bash
curl -X GET "http://localhost:8080/productos/en/stock"
```

---

## 5) Casos de error esperados

La API devuelve errores estructurados como:

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

> En errores de negocio (`404`, `409`) puede venir sin `errores`, solo con `mensaje`, `status` y `timestamp`.

### A) ID no encontrado

**Caso:** GET `/productos/9999` (o PUT/DELETE con un ID inexistente)

**HTTP:** `404 Not Found`

**Ejemplo respuesta:**

```json
{
  "mensaje": "Producto no encontrado con id: 9999",
  "status": 404,
  "timestamp": "2026-04-18T12:41:00.000"
}
```

### B) SKU duplicado

**Caso:** POST `/productos` con `sku` ya existente (ejemplo: `SKU-TEC-001`)

**HTTP:** `409 Conflict`

**Ejemplo respuesta:**

```json
{
  "mensaje": "Ya existe un producto con el SKU: SKU-TEC-001",
  "status": 409,
  "timestamp": "2026-04-18T12:42:00.000"
}
```

### C) Precio inválido

**Caso:** POST/PUT con `precio <= 0` o `precio` nulo

**HTTP:** `400 Bad Request`

**Ejemplo respuesta (`precio` en 0):**

```json
{
  "mensaje": "Error de validación en la solicitud",
  "status": 400,
  "timestamp": "2026-04-18T12:43:00.000",
  "errores": {
    "precio": "El precio debe ser mayor a 0"
  }
}
```

### D) Nombre vacío

**Caso:** POST/PUT con `nombre` vacío o espacios

**HTTP:** `400 Bad Request`

**Ejemplo respuesta:**

```json
{
  "mensaje": "Error de validación en la solicitud",
  "status": 400,
  "timestamp": "2026-04-18T12:44:00.000",
  "errores": {
    "nombre": "El nombre es obligatorio"
  }
}
```
