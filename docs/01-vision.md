# 01 — Visión y alcance

## Qué es Horus

Horus es un ecosistema ampliable cuyo objetivo es coordinar distintas
inteligencias artificiales y permitir progresivamente la interacción con el
hogar desde un sistema central.

## Visión a largo plazo

La visión contempla distintas formas de interacción, incorporadas por etapas:

- Panel web.
- Interfaz móvil.
- Interacción por voz.
- Integraciones con dispositivos y sistemas domóticos.

Son objetivos posibles de evolución, no funcionalidades implementadas.

## Alcance confirmado (2026-10-27)

Decisión del usuario: Horus será un **asistente conversacional**. La primera
capacidad es la **gestión de tareas**; después, la **lista de la compra** y la
**consulta del clima**. La domótica pasa a una fase posterior.

Decisiones asociadas:

| Tema | Decisión | Estado |
|---|---|---|
| Canal | API REST probada con Postman. Angular llegará después como frontend; la API se diseña pensando en él | **IMPLEMENTADO** (API y frontend) |
| Inteligencia | Comandos explícitos ahora. Un LLM se añadirá después como traductor de frases a comandos | **PREVISTO** |
| Usuarios | Uno solo, sin login | **IMPLEMENTADO** (no hay autenticación) |
| Domótica | Fase posterior a la demostración | **PREVISTO** |

> El alcance exacto de la demostración y sus criterios de aceptación siguen
> pendientes de concretar.

## Qué está implementado hoy

Solo lo verificado en ejecución. El detalle técnico está en
`03-arquitectura.md` y el registro de pruebas en `07-estado-y-pruebas.md`.

- Backend Spring Boot con PostgreSQL.
- CRUD de tareas (`/api/tasks`).
- CRUD de artículos de la lista de la compra (`/api/shopping-items`) y su
  relación con las tareas (`Task 1 — N ShoppingItem`).
- Consulta de clima mediante API externa (`/api/clima`).
- Validación de entrada y manejo global de errores.
- Frontend Angular con pantallas de tareas, compra, clima y despensa (esta
  última marcada «en construcción»).

## Qué NO está implementado

- Interacción por voz.
- Asistente conversacional con LLM.
- Coordinación de varias inteligencias artificiales.
- Integración con dispositivos del hogar o domótica.
- Login, usuarios y permisos.
- Docker y despliegue automatizado.
- Tests unitarios y de integración (solo existe el test de contexto).

## Principios de evolución

- Comenzar con una base pequeña, funcional e integrada.
- Ampliar según necesidades reales, tiempo disponible y resultados.
- No implementar prematuramente todos los componentes de la visión final.
- Priorizar decisiones fundamentadas, comprensión y sostenibilidad.
- Cada incremento debe ser verificable antes de pasar al siguiente.

## Estado

- Documento de visión actualizado con el alcance confirmado el 2026-10-27 y con
  la separación explícita entre lo implementado y lo previsto.
- Última actualización: 2026-11-17.
