# 03 — Arquitectura

## Estado actual (verificado)

- Un único módulo: `back_horus/`.
- Una clase principal: `BackHorusApplication`.
- Primera funcionalidad implementada: gestión de tareas (`Task`).
- Segunda funcionalidad implementada: gestión de lista de la compra (`ShoppingItem`).
- Paquetes por capas poblados: `controller`, `entity`, `repository`, `service`, `dto`, `exception`.

### Estructura de carpetas (actual)

```
back_horus/
└── src/
    └── main/
        └── java/com/project/back_horus/
            ├── BackHorusApplication.java
            ├── controller/
            │   ├── TaskController.java
            │   └── ShoppingItemController.java
            ├── dto/
            │   ├── TaskDTO.java
            │   └── ShoppingItemDTO.java
            ├── entity/
            │   ├── Task.java
            │   └── ShoppingItem.java
            ├── repository/
            │   ├── TaskRepository.java
            │   └── ShoppingItemRepository.java
            ├── service/
            │   ├── TaskService.java
            │   └── ShoppingItemService.java
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
| Service | `TaskService` | Lógica de negocio y conversión DTO ↔ Entidad |
| Service | `ShoppingItemService` | Lógica de negocio y conversión DTO ↔ Entidad |
| Repository | `TaskRepository` | Lectura y escritura en PostgreSQL (`JpaRepository`) |
| Repository | `ShoppingItemRepository` | Lectura y escritura en PostgreSQL (`JpaRepository`) |
| Entity | `Task` | Tabla `tasks` en la base de datos |
| Entity | `ShoppingItem` | Tabla `shopping_items` en la base de datos |
| DTO | `TaskDTO` | Contrato de la API: qué entra y qué sale |
| DTO | `ShoppingItemDTO` | Contrato de la API: qué entra y qué sale |
| Excepciones | `GlobalExceptionHandler` | Captura errores de validación y devuelve 400 con detalle |

### Flujo verificado

```
POST /api/tasks → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
GET  /api/tasks/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
PUT  /api/tasks/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
DELETE /api/tasks/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON

POST /api/shopping-items → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
GET  /api/shopping-items/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
PUT  /api/shopping-items/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
DELETE /api/shopping-items/{id} → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON

POST /api/tasks (DTO inválido) → Controller → GlobalExceptionHandler → 400 + detalle del error
POST /api/shopping-items (DTO inválido) → Controller → GlobalExceptionHandler → 400 + detalle del error
```

## Arquitectura prevista (no implementada)

- Monolito modular.
- Capas: `controller` → `service` → `repository` → `entity`.
- Comunicación mediante API REST.
- Frontend Angular separado (fase posterior).

## Pendiente

- Elegir fuente de datos del clima (gratuita y sin clave).
- Definir módulos adicionales del monolito cuando surjan nuevas funcionalidades.

## Estado

- Documento de arquitectura actualizado con CRUD completo de tareas y lista de la compra.
- Última actualización: 2026-11-11.