# 05 — Base de datos

## Motor

PostgreSQL 18.6 (instalación local).

## Conexión

Definida en `back_horus/src/main/resources/application.properties`:

| Clave | Valor |
|-------|-------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/horus_db` |
| `spring.datasource.username` | `postgres` (usuario con permisos sobre `horus_db`) |
| `spring.datasource.password` | `${DB_PASSWORD}` (variable de entorno) |

> **Este documento no contiene contraseñas.** El valor real vive únicamente en
> la variable de entorno `DB_PASSWORD` del sistema.

## Gestión de credenciales — IMPLEMENTADO

- La contraseña **no** está en el código ni en el repositorio.
- Se define una sola vez en Windows con `setx DB_PASSWORD "..."`
  (ver `04-entorno-desarrollo.md`).
- Spring Boot resuelve el marcador `${DB_PASSWORD}` al arrancar.
- Si la variable no existe, la aplicación **falla al arrancar** con un error
  explícito, en lugar de usar un valor codificado en el archivo.
- La clave de WeatherAPI sigue el mismo patrón con `WEATHERAPI_KEY`
  (`clima.api.key=${WEATHERAPI_KEY}`).

## Pool de conexiones

Spring Boot configura **HikariCP** automáticamente. El log muestra
`HikariPool-1 - Added connection` cuando la conexión es correcta.

## JPA / Hibernate — IMPLEMENTADO

| Propiedad | Valor | Comentario |
|-----------|-------|------------|
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate crea y actualiza el esquema según las entidades. **No recomendado en producción** |
| `spring.jpa.show-sql` | `true` | Muestra SQL por consola |
| `spring.jpa.open-in-view` | `false` | La sesión se cierra al terminar la petición |
| `spring.jpa.properties.hibernate.format_sql` | `true` | SQL formateado |

### Por qué importa `open-in-view=false`

Con la sesión cerrada al salir de la capa de servicio, cualquier acceso a una
relación `LAZY` fuera de una transacción lanza `LazyInitializationException`.
Por eso `ShoppingItemService` convierte a DTO **dentro** del método:

| Método | Anotación | Motivo |
|---|---|---|
| `obtenerArticulos` | `@Transactional(readOnly = true)` | Lee `item.getTask().getTitulo()` en `toDto` |
| `obtenerArticuloPorId` | `@Transactional(readOnly = true)` | Ídem |
| `obtenerArticulosDeTarea` | `@Transactional(readOnly = true)` | Ídem |
| `actualizarArticulo` | `@Transactional` | Ídem tras `save` |
| `agregarArticuloATarea` | `@Transactional` | Ídem tras `save` |
| `actualizarArticuloEnTarea` | `@Transactional` | Lee `item.getTask()` para validar la pertenencia |

Este fue un defecto real (`500` en `GET` y `PUT` de artículos) corregido el
2026-11-15. Ver `07-estado-y-pruebas.md`.

## Esquema real — IMPLEMENTADO

Dos tablas, creadas por Hibernate a partir de las entidades `Task` y
`ShoppingItem`.

### `tasks`

| Columna | Tipo Java | Notas |
|---|---|---|
| `id` | `Long` | PK, `GenerationType.IDENTITY` |
| `titulo` | `String` | Sin restricción en la entidad; la validación vive en `TaskDTO` (`@NotBlank`) |
| `completado` | `boolean` | Por defecto `false` |
| `fecha_creacion` | `Instant` | Se rellena en `@PrePersist` si llega `null` (UTC) |

### `shopping_items`

| Columna | Tipo Java | Notas |
|---|---|---|
| `id` | `Long` | PK, `GenerationType.IDENTITY` |
| `nombre` | `String` | `@NotBlank` también en la entidad |
| `cantidad` | `Integer` | Nullable; no se valida todavía |
| `unidad` | `String` | Nullable (`"unidades"`, `"kg"`, …) |
| `comprado` | `boolean` | Por defecto `false` |
| `task_id` | `Long` | FK → `tasks.id`, `@ManyToOne(fetch = LAZY)` |

### Relación

```
tasks                              shopping_items
├── id           ◄───────────────  task_id
├── titulo                         ├── id
├── completado                     ├── nombre
└── fecha_creacion                 ├── cantidad
                                   ├── unidad
                                   └── comprado
```

- `Task.items` es `@OneToMany(mappedBy = "task", fetch = LAZY)`.
- `ShoppingItem.task` es `@ManyToOne(fetch = LAZY)`.
- **No hay `cascade`**: borrar una tarea **no** borra sus artículos desde JPA.
  Si la base tiene la FK con `ON DELETE` por defecto (`NO ACTION`), borrar una
  tarea con artículos fallaría. Este comportamiento **no se ha verificado** con
  una prueba específica: los borrados probados no tenían artículos asociados.
  Queda como cuestión abierta.
- Un artículo pertenece a **como máximo una** tarea (mejora F4 en
  `MEJORAS_PENDIENTES.md`, no versionado).

> **Precisión:** los tipos SQL exactos (`bigint`, `varchar(255)`,
> `timestamptz`, etc.) se deducen del mapeo de Hibernate; no se han volcado con
> `\d` en `psql`. El esquema lo genera `ddl-auto=update`, no un script
> versionado.

## Consultas

- `TaskRepository`: `JpaRepository<Task, Long>`. `obtenerTareas()` usa
  `findAll()` **sin orden** (defecto D3 en `07-estado-y-pruebas.md`).
- `ShoppingItemRepository`: `JpaRepository<ShoppingItem, Long>` +
  `findByTaskId(Long taskId)`.

## Pendiente

- Evaluar una estrategia de migraciones (Flyway o Liquibase) para producción.
- Sustituir `ddl-auto=update` por migraciones versionadas antes de desplegar.
- Decidir el comportamiento al borrar una tarea con artículos (`cascade` o FK
  con `ON DELETE`).
- Validar `cantidad` (`InvalidQuantityException` existe pero nadie la lanza).

## Estado

- Conexión verificada el 2026-10-21 y de nuevo el 2026-11-17.
- Esquema real documentado a partir de las entidades `Task` y `ShoppingItem`
  (2026-11-17).
- Última actualización: 2026-11-17.
