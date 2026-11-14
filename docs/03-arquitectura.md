# 03 — Arquitectura

## Estado actual (verificado)

- Un único módulo: `back_horus/`.
- Una clase principal: `BackHorusApplication`.
- Primera funcionalidad implementada: gestión de tareas (`Task`).
- Segunda funcionalidad implementada: gestión de lista de la compra (`ShoppingItem`).
- Tercera funcionalidad implementada: consulta de clima (`Clima`).
- Paquetes por capas poblados: `controller`, `entity`, `repository`, `service`, `dto`, `exception`.

### Estructura de carpetas (actual)

```
back_horus/
└── src/
    └── main/
        └── java/com/project/back_horus/
            ├── BackHorusApplication.java
            ├── controller/
            │   ├── BackHorusApplication.java
            │   ├── TaskController.java
            │   ├── ShoppingItemController.java
            │   └── ClimaController.java
            ├── dto/
            │   ├── TaskDTO.java
            │   ├── ShoppingItemDTO.java
            │   └── ClimaDTO.java
            ├── entity/
            │   ├── Task.java
            │   ├── ShoppingItem.java
            │   └── ClimaEntity.java
            ├── repository/
            │   ├── TaskRepository.java
            │   ├── ShoppingItemRepository.java
            │   └── ClimaRepository.java
            ├── service/
            │   ├── TaskService.java
            │   ├── ShoppingItemService.java
            │   └── ClimaService.java
            └── exception/
                └── GlobalExceptionHandler.java
    └── test/
        └── java/com/project/back_horus/
            └── BackHorusApplicationTests.java
```

### Capas implementadas

| Capa | Clase | Responsabilidad |
|---|---|---|
| Controller | `TaskController` | Recibe HTTP, devuelve JSON con códigos de estado |
| Controller | `ShoppingItemController` | Recibe HTTP, devuelve JSON con códigos de estado |
| Controller | `ClimaController` | Recibe HTTP, llama a API externa de clima, devuelve JSON |
| Service | `TaskService` | Lógica de negocio y conversión DTO ↔ Entidad |
| Service | `ShoppingItemService` | Lógica de negocio y conversión DTO ↔ Entidad |
| Service | `ClimaService` | Llama a API externa de clima, convierte respuesta a DTO |
| Repository | `TaskRepository` | Lectura y escritura en PostgreSQL (`JpaRepository`) |
| Repository | `ShoppingItemRepository` | Lectura y escritura en PostgreSQL (`JpaRepository`) |
| Repository | `ClimaRepository` | (Reservado) Para futuras consultas de clima persistidas |
| Entity | `Task` | Tabla `tasks` en la base de datos |
| Entity | `ShoppingItem` | Tabla `shopping_items` en la base de datos |
| Entity | `Clima` | (No persistida) DTO de respuesta de API externa |
| DTO | `TaskDTO` | Contrato de la API: qué entra y qué sale |
| DTO | `ShoppingItemDTO` | Contrato de la API: qué entra y qué sale |
| DTO | `ClimaDTO` | Contrato de la API: qué entra y qué sale del clima |
| Excepciones | `GlobalExceptionHandler` | Captura errores de validación y devuelve 400 con detalle |

### Flujo verificado

```
GET /api/tasks → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
POST /api/tasks → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
PUT  /api/tasks/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
DELETE /api/tasks/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON

GET /api/shopping-items → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
POST /api/shopping-items → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
PUT  /api/shopping-items/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
DELETE /api/shopping-items/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON

GET /api/clima?ciudad=Madrid → Controller → Service → API externa (WeatherAPI) → Service → Controller → JSON
```

> Nota: `Clima` no es una entidad persistida. Se consulta a una API externa (WeatherAPI) y se devuelve directamente al cliente sin guardar en PostgreSQL.

## Arquitectura prevista (no implementada)

- Monolito modular.
- Capas: `controller` → `service` → `repository` → `entity`.
- Comunicación mediante API REST.
- Frontend Angular separado (fase posterior).

## Pendiente

- Definir módulos adicionales del monolito cuando surjan nuevas funcionalidades.
- Elegir fuente de datos del clima (ya implementada con WeatherAPI.com).
- Relación entre `Task` y `ShoppingItem` (tabla intermedia para asociar tareas con artículos de compra).

## Estado

- Documento de arquitectura actualizado con CRUD completo de tareas, lista de la compra y consulta de clima.
- Última actualización: 2026-11-12.