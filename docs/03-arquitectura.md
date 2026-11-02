# 03 — Arquitectura

## Estado actual (verificado)

- Un único módulo: `back_horus/`.
- Una clase principal: `BackHorusApplication`.
- Primera funcionalidad implementada: gestión de tareas (`Task`).
- Paquetes por capas poblados: `controller`, `entity`, `repository`, `service`,
  más `dto` para el contrato de la API.

### Estructura de carpetas (actual)

```
back_horus/
└── src/
    └── main/
        └── java/com/project/back_horus/
            ├── BackHorusApplication.java
            ├── controller/
            │   └── TaskController.java
            ├── dto/
            │   └── TaskDTO.java
            ├── entity/
            │   └── Task.java
            ├── repository/
            │   └── TaskRepository.java
            └── service/
                └── TaskService.java
    └── test/
        └── java/com/project/back_horus/
            └── BackHorusApplicationTests.java
```

### Capas implementadas

| Capa | Clase | Responsabilidad |
|---|---|---|
| Controller | `TaskController` | Recibe HTTP, devuelve JSON con códigos de estado |
| Service | `TaskService` | Lógica de negocio y conversión DTO ↔ Entidad |
| Repository | `TaskRepository` | Lectura y escritura en PostgreSQL (`JpaRepository`) |
| Entity | `Task` | Tabla `tasks` en la base de datos |
| DTO | `TaskDTO` | Contrato de la API: qué entra y qué sale |

### Flujo verificado

```
POST /api/tasks → Controller → Service → Repository → PostgreSQL → Service → Controller → JSON
```

## Arquitectura prevista (no implementada)

- Monolito modular.
- Capas: `controller` → `service` → `repository` → `entity`.
- Comunicación mediante API REST.
- Frontend Angular separado (fase posterior).

## Pendiente

- Completar el CRUD de tareas: `GET /api/tasks/{id}`, `PUT /api/tasks/{id}`,
  `DELETE /api/tasks/{id}`.
- Definir módulos adicionales del monolito cuando surjan nuevas funcionalidades.

## Estado

- Documento de arquitectura actualizado con la primera funcionalidad implementada.
- Última actualización: 2026-11-03.
