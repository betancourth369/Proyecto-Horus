# 06 — Conceptos aprendidos

Glosario didáctico del proyecto. Se amplía a medida que avanza el aprendizaje.
Cada término se explica tal como se usa **en Horus**, no en abstracto.

## Backend

### Spring Boot

Framework Java que simplifica crear aplicaciones. Aporta configuración
automática, un servidor embebido y gestión de dependencias. En Horus arranca
Tomcat en el puerto `8080` sin instalar un servidor aparte.

### Maven y Maven Wrapper

Herramienta de build y gestión de dependencias. El Wrapper (`mvnw` /
`mvnw.cmd`) permite compilar y ejecutar sin instalar Maven globalmente: usa la
versión declarada en el propio proyecto.

### Inyección de dependencias (DI)

Patrón por el que los objetos reciben sus dependencias en lugar de crearlas
ellos mismos. En Horus se ve en los constructores generados por
`@AllArgsConstructor` de Lombok: `TaskController` recibe `TaskService`,
`TaskService` recibe `TaskRepository`, etc. Spring se encarga de construir la
cadena.

### API REST

Estilo de comunicación sobre HTTP basado en recursos y métodos (`GET`, `POST`,
`PUT`, `DELETE`). En Horus cada recurso tiene su ruta base: `/api/tasks`,
`/api/shopping-items` y `/api/clima`.

### DTO (Data Transfer Object)

Objeto que define **qué se envía y se recibe** por la API, separado de la
entidad que se guarda en la base de datos.

En Horus:

- `Task` (entidad) ↔ `TaskDTO` (contrato JSON).
- `ShoppingItem` (entidad) ↔ `ShoppingItemDTO`.
- `ClimaDTO` **no tiene entidad**: es solo un DTO relleno con la respuesta de
  WeatherAPI.

Ventajas prácticas: el JSON no expone la estructura interna, se puede validar
con `@NotBlank` en el DTO y se evita serializar relaciones `LAZY`.

### Spring Data JPA y Hibernate

Capa de persistencia. JPA es la especificación; Hibernate la implementa. Mapea
tablas a objetos Java (ORM).

En Horus:

- `@Entity` + `@Table(name = "tasks")` deciden el nombre de la tabla.
- `JpaRepository<Task, Long>` aporta `findAll`, `findById`, `save`,
  `deleteById`… sin escribir SQL.
- Las relaciones se declaran con `@OneToMany` / `@ManyToOne`.

### `@Transactional` y `open-in-view=false`

`@Transactional` abre una sesión de Hibernate durante la ejecución del método y
la cierra al terminar. Con `spring.jpa.open-in-view=false`, la sesión **no**
vive hasta el final de la petición HTTP.

Consecuencia: si un método devuelve una entidad y alguien lee después una
relación `LAZY`, ya no hay sesión y salta `LazyInitializationException`. Por eso
en Horus la conversión a DTO se hace **dentro** del método transaccional.

`readOnly = true` es una pista para Hibernate: no se va a escribir, así que
puede optimizar y no ensuciar el contexto de persistencia.

### Validación con Jakarta Bean Validation

Anotaciones como `@NotBlank` en el DTO declaran reglas. `@Valid` en el
controlador las activa. Si fallan, Spring lanza
`MethodArgumentNotValidException`, que `GlobalExceptionHandler` traduce a
`400` con el nombre del campo y el mensaje.

### Manejo global de excepciones

Mecanismo de Spring para capturar excepciones lanzadas en los controladores y
convertirlas en respuestas HTTP con el código adecuado. Se implementa con
`@RestControllerAdvice` y métodos `@ExceptionHandler`.

En Horus (`GlobalExceptionHandler`):

| Excepción | Código | Respuesta |
|---|---|---|
| `MethodArgumentNotValidException` | `400` | `{"campo":"mensaje"}` |
| `BusinessException` | `400` | `{"error":"Error de negocio", ...}` |
| `ResourceNotFoundException` | `404` | `{"error":"Recurso no encontrado", ...}` |
| `InvalidQuantityException` | `400` | `{"error":"Cantidad inválida", ...}` |
| `DuplicateResourceException` | `409` | `{"error":"Recurso duplicado", ...}` |

> `InvalidQuantityException` y `DuplicateResourceException` están preparadas
> pero **ningún servicio las lanza todavía**.

### Perfiles de Spring

Conjuntos de configuración asociados a un entorno (desarrollo, producción).
En Horus aún no se usan: hay un único `application.properties`.

## Frontend

### Angular standalone

Desde Angular 15 los componentes pueden declarar sus dependencias
directamente (`imports: [...]`) sin `NgModules`. Horus usa **solo** componentes
standalone: `app.config.ts` concentra `provideRouter` y `provideHttpClient`.

### Angular zoneless

`zone.js` es la librería que, históricamente, avisaba a Angular de cualquier
tarea asíncrona (un `setTimeout`, una respuesta HTTP) para que revisara la
vista. Angular v21+ es **zoneless por defecto**.

Sin `zone.js`, Angular solo repinta cuando:

- cambia una **señal** (`signal`) leída en la plantilla,
- ocurre un **evento de plantilla** (clic, input…),
- se llama explícitamente a `markForCheck()` / `setInput()`.

En Horus **ningún componente usa señales** para el estado de datos y no se
carga `zone.js`, así que lo que llega en `subscribe` no se pinta hasta el
siguiente clic. Es el defecto D1 documentado en `07-estado-y-pruebas.md`.

La única señal del proyecto está en `TemaService`
(`signal<Tema>(...)`): por eso el botón de tema sí repinta de inmediato.

### Servicios e `HttpClient`

Un servicio Angular (`@Injectable({ providedIn: 'root' })`) centraliza las
llamadas HTTP. En Horus: `TareaService`, `ShoppingItemService`, `ClimaService`
y `TemaService`. Ninguno usa URL absoluta: todos piden `/api/...` y el proxy
del dev server resuelve el destino.

### Observables y operadores de RxJS

Un `Observable` es un flujo de valores en el tiempo. Los **operadores** lo
transforman o lo cortan. Los que se usan en Horus:

| Operador | Para qué |
|---|---|
| `map` | Convertir la respuesta HTTP en el modelo que espera el componente |
| `catchError` | Capturar el error y devolver un valor alternativo (`of(...)`) |
| `tap` | Efecto secundario sin cambiar el valor (por ejemplo, guardar en caché) |
| `timeout` | Cortar la petición si tarda demasiado (`conTiempoLimite`) |
| `finalize` | Quitar el estado «cargando» pase lo que pase |
| `switchMap` | Cancelar la petición anterior cuando llega una nueva (tareas y clima) |
| `forkJoin` | Lanzar varias peticiones en paralelo y esperar a todas (artículos de cada tarea) |

La caché del clima **no** usa `shareReplay`: es un `Map` propio rellenado con
`tap` y consultado antes de lanzar la petición.

### `pipeable` propio: `conTiempoLimite()`

En lugar de repetir el `timeout` y la traducción de errores en cada método,
`peticion-http.ts` define una función que envuelve un observable y devuelve
otro con tiempo límite y errores ya clasificados (`PeticionError`). Se aplica
con `pipe(conTiempoLimite())` en las 14 llamadas de tareas y artículos.

Es el mismo patrón que un operador de RxJS, pero escrito en el proyecto: una
función que recibe un `Observable<T>` y devuelve un `Observable<T>`.

### Proxy del servidor de desarrollo

El backend no tiene CORS. Para que el navegador pueda llamar a `:8080` desde
`:4200`, el dev server de Angular reenvía `/api` a `http://localhost:8080`
(`proxy.conf.json`). Para el navegador todo es el mismo origen; para el
servidor, es una petición más.

### Variables CSS y `data-theme`

El tema claro/oscuro se implementa con variables CSS declaradas en `:root` y
sobrescritas en `html[data-theme="oscuro"]`. Cambiar un atributo del `<html>`
cambia todos los colores sin tocar los componentes. `public/theme-init.js` lee
`localStorage` **antes** del primer pintado para evitar un destello blanco.

## Pendiente

- Añadir conceptos nuevos a medida que se aprenden (pruebas, Docker,
  migraciones, seguridad).

## Estado

- Glosario ampliado con DTO, `@Transactional`, `open-in-view`, RxJS, servicios,
  proxy, variables CSS y Angular zoneless (2026-11-17).
- Última actualización: 2026-11-17.
