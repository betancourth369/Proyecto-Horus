# 03 — Arquitectura

## Estado actual (verificado 2026-11-17)

- Un único módulo de backend: `back_horus/`.
- Tres funcionalidades: tareas (`Task`), lista de la compra (`ShoppingItem`)
  y clima (`Clima`, consulta externa sin persistencia).
- Paquetes por capas: `controller`, `entity`, `repository`, `service`, `dto`,
  `exception`.
- Un frontend Angular en `frontend-horus/` con componentes **standalone**.
- Relación implementada **Task 1 — N ShoppingItem**.

## Backend (`back_horus/`)

### Estructura de carpetas (real)

```
back_horus/
└── src/
    ├── main/
    │   ├── java/com/project/back_horus/
    │   │   ├── BackHorusApplication.java
    │   │   ├── controller/
    │   │   │   ├── TaskController.java
    │   │   │   ├── ShoppingItemController.java
    │   │   │   └── ClimaController.java
    │   │   ├── dto/
    │   │   │   ├── TaskDTO.java
    │   │   │   ├── ShoppingItemDTO.java
    │   │   │   └── ClimaDTO.java
    │   │   ├── entity/
    │   │   │   ├── Task.java
    │   │   │   └── ShoppingItem.java
    │   │   ├── repository/
    │   │   │   ├── TaskRepository.java
    │   │   │   └── ShoppingItemRepository.java
    │   │   ├── service/
    │   │   │   ├── TaskService.java
    │   │   │   ├── ShoppingItemService.java
    │   │   │   └── ClimaService.java
    │   │   └── exception/
    │   │       ├── BusinessException.java
    │   │       ├── ResourceNotFoundException.java
    │   │       ├── InvalidQuantityException.java
    │   │       ├── DuplicateResourceException.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       └── application.properties
    └── test/java/com/project/back_horus/
        └── BackHorusApplicationTests.java
```

> **No existen** `ClimaEntity` ni `ClimaRepository`. El clima no se persiste:
> `ClimaDTO` es un objeto de transferencia que se rellena con la respuesta de
> WeatherAPI.

### Capas implementadas

| Capa | Clase | Responsabilidad |
|---|---|---|
| Controller | `TaskController` | HTTP de tareas; devuelve `ResponseEntity` con el código adecuado |
| Controller | `ShoppingItemController` | HTTP de artículos, incluidos los anidados por tarea |
| Controller | `ClimaController` | HTTP de clima; delega en el servicio que consulta la API externa |
| Service | `TaskService` | Lógica de tareas y conversión DTO ↔ entidad |
| Service | `ShoppingItemService` | Lógica de artículos, relación con `Task` y conversión DTO ↔ entidad |
| Service | `ClimaService` | Consulta a WeatherAPI con tiempos de espera; convierte a `ClimaDTO` |
| Repository | `TaskRepository` | `JpaRepository<Task, Long>` |
| Repository | `ShoppingItemRepository` | `JpaRepository<ShoppingItem, Long>` + `findByTaskId` |
| Entity | `Task` | Tabla `tasks`; `@OneToMany` (LAZY) hacia `ShoppingItem` |
| Entity | `ShoppingItem` | Tabla `shopping_items`; `@ManyToOne` (LAZY) hacia `Task` |
| DTO | `TaskDTO`, `ShoppingItemDTO`, `ClimaDTO` | Contrato JSON de la API, separado de la entidad |
| Excepciones | `GlobalExceptionHandler` | Traduce excepciones a `400`, `404` y `409` con JSON |

### Relación Task ↔ ShoppingItem

```
Task (tasks)                       ShoppingItem (shopping_items)
├── id                             ├── id
├── titulo                         ├── nombre
├── completado                     ├── cantidad
├── fecha_creacion                 ├── unidad
└── items  @OneToMany LAZY ──────► ├── comprado
                                   └── task_id  @ManyToOne LAZY
```

Consecuencias prácticas:

- La relación es **unidireccional en la práctica**: se navega de artículo a
  tarea (`item.getTask()`), y `Task.items` existe para el mapeo.
- `TaskDTO` **no incluye** los artículos. El cliente los pide aparte
  (ver *Endpoints*).
- `ShoppingItemDTO` sí incluye `taskId` y `taskNombre`, datos **derivados** de
  la relación, no columnas de la tabla.
- `spring.jpa.open-in-view=false` (ver `05-base-de-datos.md`), así que el
  servicio necesita `@Transactional` para leer `item.getTask().getTitulo()`
  dentro de la sesión de Hibernate. Sin él aparece
  `LazyInitializationException` (defecto ya corregido).

### Endpoints (verificados en ejecución)

| Método | Ruta | Código | Descripción |
|---|---|---|---|
| `GET` | `/api/tasks` | `200` | Lista de tareas |
| `POST` | `/api/tasks` | `201` | Crea tarea |
| `GET` | `/api/tasks/{id}` | `200` / `404` | Tarea por id |
| `PUT` | `/api/tasks/{id}` | `200` / `404` | Actualiza; exige `titulo` (`@NotBlank`) |
| `DELETE` | `/api/tasks/{id}` | `204` / `404` | Borra |
| `GET` | `/api/shopping-items` | `200` | Todos los artículos, con `taskId` y `taskNombre` |
| `POST` | `/api/shopping-items` | `201` | Crea artículo sin tarea |
| `GET` | `/api/shopping-items/{id}` | `200` / `404` | Artículo por id |
| `PUT` | `/api/shopping-items/{id}` | `200` / `404` | Actualiza artículo |
| `DELETE` | `/api/shopping-items/{id}` | `204` / `404` | Borra artículo |
| `POST` | `/api/shopping-items/tasks/{taskId}/items` | `201` / `404` | Crea artículo asociado a una tarea |
| `GET` | `/api/shopping-items/tasks/{taskId}/items` | `200` / `404` | Artículos de una tarea; `404` si la tarea no existe |
| `PUT` | `/api/shopping-items/tasks/{taskId}/items/{itemId}` | `200` / `404` | Actualiza artículo de una tarea |
| `DELETE` | `/api/shopping-items/tasks/{taskId}/items/{itemId}` | `204` / `404` | Borra artículo de una tarea |
| `GET` | `/api/clima?ciudad={ciudad}` | `200` / `404` | Clima actual vía WeatherAPI |

> La ruta `/api/tasks/{taskId}/items` **no existe** (devuelve `404`). Los
> artículos por tarea viven bajo `/api/shopping-items/tasks/...`.

### Manejo de errores (backend)

| Situación | Excepción | Respuesta |
|---|---|---|
| Campo inválido (`@Valid`) | `MethodArgumentNotValidException` | `400` + `{"campo":"mensaje"}` |
| Recurso inexistente | `ResourceNotFoundException` | `404` + `{"error":"Recurso no encontrado","mensaje":"..."}` |
| Cantidad inválida | `InvalidQuantityException` | `400` + `{"error":"Cantidad inválida","mensaje":"..."}` |
| Duplicado | `DuplicateResourceException` | `409` + `{"error":"Recurso duplicado","mensaje":"..."}` |
| Otra regla de negocio | `BusinessException` | `400` + `{"error":"Error de negocio","mensaje":"..."}` |

> `InvalidQuantityException` y `DuplicateResourceException` están **definidas y
> manejadas**, pero ningún servicio las lanza todavía. Son infraestructura
> preparada, no comportamiento en uso.

### Flujo de una petición

```
GET /api/shopping-items/tasks/6/items
   → ShoppingItemController
   → ShoppingItemService   (@Transactional, comprueba que la tarea exista)
   → ShoppingItemRepository.findByTaskId
   → PostgreSQL
   → conversión a ShoppingItemDTO (incluye taskNombre desde la relación)
   → JSON
```

## Frontend (`frontend-horus/`)

Aplicación Angular con **componentes standalone** (sin `NgModules`) que consume
la API con rutas relativas `/api/...`.

### Estructura de carpetas (real)

```
frontend-horus/
├── angular.json          Build y dev server (proxy en el bloque serve)
├── package.json          Dependencias y scripts
├── proxy.conf.json       Redirige /api → http://localhost:8080
└── src/
    ├── index.html        Documento raíz (lang="es")
    ├── main.ts           bootstrapApplication(AppComponent, appConfig)
    ├── styles.css        Variables de tema claro/oscuro y estilos compartidos
    └── app/
        ├── app.component.*      Raíz: menú lateral + contenedor + router-outlet
        ├── app.config.ts        provideRouter + provideHttpClient
        ├── app.routes.ts        Rutas de la aplicación
        ├── core/
        │   ├── models/          tarea · shopping-item · clima (.model.ts)
        │   └── services/        tarea · shopping-item · clima · tema (.service.ts)
        │                        peticion-http.ts (operador de tiempo límite)
        ├── shared/
        │   ├── layout/          menu-lateral · contenedor-principal
        │   └── components/      chip-estado · estado-vacio · tarjeta-cristal
        │                        boton · barra-progreso
        └── features/
            ├── tareas/pages/    lista-tareas · nueva-tarea
            ├── compra/pages/    lista-compra
            ├── clima/pages/     clima
            └── despensa/pages/  despensa (marcada "en construcción")
```

Además, `public/theme-init.js` aplica el tema guardado antes del primer
pintado.

### Capas del frontend

| Carpeta | Contenido | Responsabilidad |
|---|---|---|
| `core/models` | `tarea.model.ts`, `shopping-item.model.ts`, `clima.model.ts` | Tipos que reflejan el JSON del backend |
| `core/services` | `TareaService`, `ShoppingItemService`, `ClimaService`, `TemaService` | HTTP y estado del tema; ninguna URL absoluta |
| `core/services/peticion-http.ts` | `conTiempoLimite()`, `PeticionError`, `mensajeDeError()` | Tiempo límite y traducción de errores de tareas y artículos |
| `shared/layout` | `MenuLateralComponent`, `ContenedorPrincipalComponent` | Estructura visual común |
| `shared/components` | `ChipEstadoComponent`, `EstadoVacioComponent`, `TarjetaCristalComponent`, `BotonComponent`, `BarraProgresoComponent` | Piezas reutilizables sin lógica de negocio |
| `features/*/pages` | Componentes de pantalla | Orquestan servicios, estado de vista y navegación |

### Rutas

| Ruta | Componente | Pantalla |
|---|---|---|
| `''` | redirección | Redirige a `/tareas` |
| `/tareas` | `ListaTareasComponent` | Listado con filtros, artículos y progreso |
| `/tareas/nueva` | `NuevaTareaComponent` | Formulario de creación |
| `/compra` | `ListaCompraComponent` | Artículos de la lista de la compra |
| `/clima` | `ClimaComponent` | Consulta del clima por ciudad |
| `/despensa` | `DespensaComponent` | Marcada "en construcción" |
| `**` | redirección | Redirige a `/tareas` |

### Flujo de datos

```
Navegador → Angular (localhost:4200)
    → HttpClient  /api/...
    → proxy del dev server (proxy.conf.json)
    → Spring Boot (localhost:8080)
    → PostgreSQL  ·  WeatherAPI
```

### Decisiones de diseño aplicadas

- Un único componente raíz: `AppComponent`. Se eliminó el scaffold `App`
  (`app.ts`, `app.html`, `app.css`, `app.spec.ts`).
- Cada componente usa `.ts` + `.html` + `.css` en archivos separados: **sin
  plantillas ni estilos embebidos**.
- Los modelos viven en `core/models/` con el patrón `nombre.model.ts`. El tipo
  `Long` de Java se representa como `number` en TypeScript.
- Tema claro/oscuro con variables CSS. El tema activo se marca con
  `data-theme="claro|oscuro"` en `<html>`; lo aplica `public/theme-init.js`
  antes del primer pintado y `TemaService` al pulsar el botón del menú lateral.
  La elección se guarda en `localStorage` (`horus-tema`); si no hay elección
  previa se usa `prefers-color-scheme`.
- El estado de una tarea se **deriva en el frontend**: `completado` →
  completada; artículos parcialmente comprados → en curso; resto → pendiente.
- Los artículos de cada tarea se piden a
  `GET /api/shopping-items/tasks/{taskId}/items`, porque `TaskDTO` no los
  incluye.
- El backend valida `@NotBlank` en `titulo`, así que el `PUT /api/tasks/{id}`
  debe enviar el título junto al estado.
- Los errores visibles se muestran en un aviso con botón **Reintentar**. El
  bloque `.aviso-error` es compartido y vive en `src/styles.css`.
- Al refrescar una lista **no se oculta** la rejilla si ya hay datos: solo se
  muestra «Cargando...» cuando no hay nada que enseñar y «Actualizando...»
  cuando ya hay contenido en pantalla.

### Tiempos de espera del cliente (tareas y artículos)

`conTiempoLimite()` envuelve las **14** llamadas de `TareaService` y
`ShoppingItemService`:

| Elemento | Valor | Efecto |
|---|---|---|
| `TIEMPO_MAXIMO_PETICION_MS` | 10 s | Corta la petición si el proxy o el backend no responden |
| `PeticionError` | — | Error con `motivo` (`sin-conexion`, `timeout`, `no-existe`, `no-valido`, `conflicto`, `servidor`) y mensaje listo para mostrar |
| `mensajeDeError()` | — | Extrae el mensaje del backend (`mensaje` o el primer campo de texto) |

Los valores del clima son distintos y están en `04-entorno-desarrollo.md` →
*Parámetros del clima*.

### Cómo se repinta la interfaz — IMPORTANTE

Este proyecto **no carga `zone.js`**. Angular v21+ es zoneless por defecto, y
sin señales ni `markForCheck()` un cambio de estado asíncrono **no repinta la
vista** hasta que ocurre un evento de plantilla. Es el defecto abierto más
importante del frontend: está descrito en `07-estado-y-pruebas.md`.

## Previsto (no implementado)

- Docker / Docker Compose.
- Autenticación de usuarios.
- Coordinación de servicios de IA.
- Integración con dispositivos del hogar.
- Migración del estado del frontend a signals (ver `07-estado-y-pruebas.md`).

## Pendiente

- Corregir el defecto de repintado descrito en `07-estado-y-pruebas.md`.
- Optimizar el N+1 de artículos por tarea (una petición por tarea).
- Definir un orden estable para `GET /api/tasks`.
- Añadir tests unitarios y de integración.
- Mejoras de despensa: consumo, stock, caducidades y artículo compartido.

## Estado

- Documento reescrito con la estructura **real** del código (2026-11-17).
- Eliminadas `ClimaEntity` y `ClimaRepository`, que nunca existieron, y el
  `BackHorusApplication` duplicado en `controller/`.
- Añadidos: relación Task ↔ ShoppingItem, tabla real de endpoints, manejo de
  errores completo, `peticion-http.ts` y la nota de repintado.
- Última actualización: 2026-11-17.
