# 07 — Estado, pruebas y defectos conocidos

Este capítulo responde a tres preguntas: **qué está verificado**, **cómo se ha
verificado** y **qué se sabe que está mal**. No describe intenciones: lo que no
se ha comprobado se marca como tal.

## Estado del repositorio

| Dato | Valor |
|---|---|
| Rama | `main` |
| Último commit publicado | `1d3caf4` — *feat: añade relación Task-ShoppingItem con CRUD completo de artículos por tarea* |
| Cambios sin commit | Sí: backend, frontend completo y documentación |
| Remoto | `https://github.com/betancourth369/Proyecto-Horus` (público) |
| Licencia | MIT (`LICENSE`) |

> **Aviso de lectura.** Esta documentación describe el **árbol de trabajo
> local**, no solo lo publicado. En el momento de escribirla hay cambios de
> backend y un frontend Angular reescrito que **todavía no están en el commit
> `1d3caf4`**. Quien clone el repositorio remoto no verá ese código hasta que se
> publique. Lo que sigue distingue lo verificado en ejecución de lo que solo
> existe en local.

## Cómo se ha verificado

| Tipo de prueba | Alcance | Resultado |
|---|---|---|
| Compilación backend | `mvnw compile` | BUILD SUCCESS |
| Compilación frontend | `npx ng build` | Sin errores ni warnings |
| Pruebas HTTP contra la API | Peticiones reales a `:8080` y a través del proxy de `:4200` | 21 casos con el código esperado |
| Arranque con PostgreSQL | Log de Spring Boot | Conecta y escucha en `8080` |
| Navegador | — | **No verificada**: el navegador integrado no se pudo conectar |

## Resultados de la auditoría (2026-11-17)

### Tareas

| # | Caso | Esperado | Obtenido |
|---|---|---|---|
| 1 | `POST /api/tasks` válido | `201` | `201` |
| 2 | `POST` con `titulo` vacío | `400` | `400` |
| 3 | `POST` sin `titulo` | `400` | `400` |
| 4 | `GET /api/tasks` | `200` | `200` |
| 5 | `GET /api/tasks/{id}` existente | `200` | `200` |
| 6 | `GET /api/tasks/999999` | `404` | `404` |
| 7 | `PUT` con `titulo` + `completado` | `200` | `200` |
| 8 | `PUT` solo con `completado` | `400` (`@NotBlank`) | `400` |
| 9 | `PUT` sobre id inexistente | `404` | `404` |
| 10 | `DELETE` existente | `204` | `204` |
| 11 | `DELETE` ya borrada | `404` | `404` |

### Artículos y relación con tareas

| # | Caso | Esperado | Obtenido |
|---|---|---|---|
| 12 | `POST` artículo de una tarea | `201` | `201` |
| 13 | `POST` artículo con `nombre` vacío | `400` | `400` |
| 14 | `GET` artículos de la tarea | `200` | `200` |
| 15 | `GET` artículos de tarea inexistente | `404` | `404` |
| 16 | `PUT` artículo de la tarea | `200` | `200` |
| 17 | `PUT` artículo inexistente | `404` | `404` |
| 18 | `DELETE` artículo | `204` | `204` |
| 19 | `DELETE` artículo ya borrado | `404` | `404` |
| 20 | `GET /api/shopping-items` con artículos asociados | `200` | `200` |
| 21 | `GET /api/clima?ciudad=Madrid` | `200` | `200` |

> El caso 15 devolvía `200` con `[]` antes del 2026-11-17. Ahora devuelve `404`,
> para distinguir «tarea sin artículos» de «tarea inexistente».

### Medidas de velocidad (2026-11-17)

Todas las cifras son de este equipo, a través del proxy de Angular.

| Operación | Tiempo |
|---|---|
| `GET /api/tasks` (5 repeticiones, caliente) | 38–62 ms |
| `PUT /api/tasks/{id}` | 65–167 ms |
| `GET /api/tasks` tras actualizar | 34–115 ms |
| 4 × `GET` de artículos en paralelo | 143 ms |
| `DELETE /api/tasks/{id}` (caliente) | 36–81 ms |
| `GET /api/clima?ciudad=Bilbao` (caliente) | 61–122 ms |
| Primera petición tras arrancar el JVM | 1,5–1,6 s |
| Backend parado (proxy responde `502`) | 21–84 ms |

Lectura: el backend **no** es el cuello de botella. La primera petición tras
arrancar es más lenta por la conexión a PostgreSQL y la compilación JIT; a
partir de ahí todo baja de 100 ms.

## Defectos conocidos

### D1 — La interfaz no se repinta hasta que el usuario hace clic

- **Severidad:** alta (afecta a todas las pantallas).
- **Síntoma:** al entrar en `/tareas`, `/compra`, `/despensa` o `/clima` se ve
  «Cargando...» y los contadores a cero. Los datos ya llegaron, pero no
  aparecen hasta pulsar un filtro o cualquier botón.
- **Causa:** el proyecto **no carga `zone.js`**. Angular v21+ es zoneless por
  defecto (confirmado en la documentación oficial), y en ese modo la vista solo
  se actualiza con `markForCheck()`, `setInput()`, una señal leída en la
  plantilla o un evento de plantilla. Los componentes asignan campos normales
  dentro de `subscribe`, que no es ninguna de esas señales.
- **Por qué el clic lo arregla:** el clic es un evento de plantilla y dispara la
  detección de cambios; entonces se pintan los datos que ya estaban en memoria.
- **Estado:** **abierto**. Requiere decisión entre habilitar `zone.js`,
  migrar el estado a signals o llamar a `markForCheck()`.
- **Detalle técnico:** ver `03-arquitectura.md` → *Cómo se repinta la
  interfaz*.

### D2 — N+1 peticiones al cargar la lista de tareas

- **Severidad:** media.
- **Síntoma:** `GET /api/tasks` más **una** petición de artículos por cada
  tarea.
- **Impacto medido:** 4 tareas → 5 peticiones, 143 ms en paralelo. Crece linealmente.
- **Alternativa:** pedir `GET /api/shopping-items` una vez y agrupar por
  `taskId` en el cliente.
- **Estado:** abierto, no bloqueante.

### D3 — `GET /api/tasks` no tiene orden definido

- **Severidad:** baja.
- **Síntoma:** las tarjetas cambian de posición después de actualizar una tarea.
- **Causa:** `TaskService.obtenerTareas()` usa `findAll()` sin `Sort`.
  PostgreSQL puede mover la fila al reescribirla en un `UPDATE`.
- **Estado:** abierto, aceptado por el usuario el 2026-11-17.

### D4 — Cobertura de pruebas mínima

- **Severidad:** media.
- **Síntoma:** solo existe `BackHorusApplicationTests` (test de contexto). El
  frontend no tiene pruebas.
- **Riesgo:** los defectos D1 y D3 no los detecta ni el compilador ni una
  prueba HTTP; hacen falta pruebas de componente o revisión en navegador.
- **Estado:** abierto.

### D5 — Sin verificación visual en navegador

- **Severidad:** media.
- **Síntoma:** el aspecto de la interfaz (tema claro/oscuro, avisos de error,
  textos de carga) no se ha confirmado en pantalla.
- **Motivo:** el navegador integrado de la herramienta de desarrollo no se pudo
  conectar a la sesión durante las pruebas del 2026-11-17.
- **Estado:** abierto. Es la única parte que no cubren las pruebas HTTP.

## Riesgos y deuda técnica

| Tema | Riesgo | Mitigación actual |
|---|---|---|
| `ddl-auto=update` | Cambios de esquema no versionados | Aceptado en desarrollo; pendiente Flyway o Liquibase |
| Java 27 (non-LTS) | Menos tiempo de soporte que una LTS | Documentado en `decisiones/0001-stack-inicial.md` |
| Sin autenticación | Un solo usuario, sin login | Decisión de alcance, no olvido |
| Sin tests | Regresiones silenciosas | Verificación manual y compilación |
| Backend sin CORS | Solo se puede llamar a través del proxy | `proxy.conf.json` |

## Próximos pasos acordados

1. Decidir y aplicar la corrección de D1 (repintado de la interfaz).
2. Verificar en navegador el tema claro/oscuro y los avisos de error (D5).
3. Publicar los cambios pendientes de backend y frontend.
4. Decidir si se optimiza D2 y si se ordena la lista (D3).
5. Añadir las primeras pruebas automatizadas (D4).

## Estado

- Capítulo nuevo (2026-11-17) con el estado del repositorio, la auditoría, las
  mediciones y los defectos conocidos.
- Los defectos D1–D5 están **abiertos**; ninguno se describe aquí como
  resuelto.
- Última actualización: 2026-11-17.
