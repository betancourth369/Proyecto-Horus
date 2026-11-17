# Horus — Documentación y guía del proyecto

> Libro de documentación, guía y aprendizaje de Horus.
> Este índice crece a medida que el proyecto avanza.

## Qué es este libro

Recoge la documentación técnica, las decisiones de diseño y los conceptos
aprendidos durante el desarrollo de Horus. Está pensado para:

- Consultarse como manual del proyecto.
- Servir de fuente para NotebookLM.
- Facilitar el aprendizaje progresivo.

## Índice

| Capítulo | Archivo | Contenido |
|---|---|---|
| 1 | [Visión y alcance](./01-vision.md) | Visión, alcance confirmado e implementado vs previsto |
| 2 | [Stack tecnológico](./02-stack-tecnologico.md) | Tecnologías actuales y previstas (incluye Angular zoneless) |
| 3 | [Arquitectura](./03-arquitectura.md) | Backend, frontend, endpoints, errores y flujo de datos |
| 4 | [Entorno de desarrollo](./04-entorno-desarrollo.md) | Arranque, puertos, proxy, tiempos de espera y mediciones |
| 5 | [Base de datos](./05-base-de-datos.md) | Esquema real, `@Transactional` y persistencia |
| 6 | [Conceptos aprendidos](./06-conceptos.md) | Glosario técnico aplicado al proyecto |
| 7 | [Estado, pruebas y defectos](./07-estado-y-pruebas.md) | Auditoría, mediciones y defectos abiertos |
| 8 | [Decisiones](./decisiones/0001-stack-inicial.md) | Registro de decisiones técnicas (ADR) |

## Estado de la documentación

| Documento | Qué refleja | Última actualización |
|---|---|---|
| `docs/01-vision.md` | Alcance confirmado y separación implementado/previsto | 2026-11-17 |
| `docs/02-stack-tecnologico.md` | Stack verificado + nota zoneless | 2026-11-17 |
| `docs/03-arquitectura.md` | Estructura real de backend y frontend | 2026-11-17 |
| `docs/04-entorno-desarrollo.md` | Arranque, proxy, tiempos y mediciones | 2026-11-17 |
| `docs/05-base-de-datos.md` | Esquema real `tasks` / `shopping_items` | 2026-11-17 |
| `docs/06-conceptos.md` | Glosario ampliado | 2026-11-17 |
| `docs/07-estado-y-pruebas.md` | Auditoría y defectos D1–D5 | 2026-11-17 |
| `docs/decisiones/0001-stack-inicial.md` | Decisión del stack inicial | 2026-10-21 |

> **Aviso de alcance.** Esta documentación describe el **árbol de trabajo
> local**, que en esta fecha contiene cambios aún no publicados en el remoto
> (frontend reescrito y correcciones). El detalle está en
> `07-estado-y-pruebas.md` → *Estado del repositorio*.

> Los estados internos de cada capítulo distinguen **IMPLEMENTADO** (verificado
> en el código y, cuando se indica, en ejecución), **PREVISTO** (hoja de ruta) y
> **NO VERIFICADO**.

## Uso con NotebookLM

Este libro está pensado para subirse como **fuente** de un cuaderno de
NotebookLM, que lo usa como guía de referencia y de aprendizaje del proyecto.

1. Crear un cuaderno en NotebookLM.
2. Subir estos archivos `.md` como fuentes (uno por archivo).
3. Al avanzar el proyecto, volver a subir los archivos modificados.

**Nombre del cuaderno:** `Horus — Guía y referencia del proyecto`

**Descripción:** *Cuaderno de referencia y aprendizaje del proyecto Horus, un
asistente inteligente para el hogar construido con Spring Boot, PostgreSQL y
Angular. Contiene la visión, la arquitectura, el stack, el entorno de
desarrollo, la base de datos, el estado de las pruebas y las decisiones
técnicas.*

### Prompt de inicialización

Se usa al pedirle a NotebookLM (o a cualquier IA) que actualice o explique este
libro. Debe respetar la separación de estados y no inventar información.

```
Actúa como un documentador técnico senior especializado en desarrollo de
software full-stack (Java/Spring Boot, Angular) y DevOps (Docker). Tu objetivo
es actualizar y mantener la documentación del "Proyecto Horus" en la carpeta
docs/ de forma impecable, modular y orientada tanto a humanos como a la ingesta
óptima por parte de inteligencias artificiales (NotebookLM).

Reglas estrictas para generar o actualizar los archivos .md:

1. Estructura modular y limpia: cada archivo se centra en un único dominio
   (visión, stack actual vs previsto, arquitectura de capas y futura
   arquitectura full-stack, entorno de desarrollo, base de datos y decisiones).
2. Separación estricta de estados (vital): en cada sección diferencia sin
   ambigüedades entre IMPLEMENTADO (código real verificado en el repositorio),
   PREVISTO (hoja de ruta) y NO VERIFICADO.
3. Claridad y formato profesional: tablas Markdown para separar tecnologías
   actuales de las futuras, bloques de código limpios para la arquitectura de
   carpetas y viñetas para la lectura rápida.
4. Trazabilidad temporal: cada archivo .md incluye obligatoriamente al final
   una sección "Estado" con la fecha de la última actualización (YYYY-MM-DD).
5. Cero secretos: nunca incluyas credenciales, contraseñas o tokens reales;
   referencia siempre las variables de entorno (DB_PASSWORD, WEATHERAPI_KEY).

Mantén un tono técnico, formal pero sumamente didáctico.
```

## Reglas de escritura y actualización

- **Fuente única de verdad**: `docs/` es la única fuente autorizada sobre el
  proyecto. No inventes información que no esté en el repositorio.
- **Distinguir estados**: marca claramente qué está implementado, qué está
  previsto y qué no está verificado.
- **No secretos**: nunca incluyas contraseñas, tokens, claves privadas ni
  credenciales; referencia `DB_PASSWORD` y `WEATHERAPI_KEY`.
- **No duplicar información**: si un dato ya existe en otro capítulo,
  referencia ese archivo en lugar de repetirlo.
- **Citar fuentes**: al referenciar una decisión o característica técnica,
  indica el archivo de origen (por ejemplo, `docs/04-entorno-desarrollo.md`).
- **Actualizar el índice**: al añadir un archivo nuevo, añadirlo también a la
  tabla de este documento.
- **No describir como implementado lo que solo está planificado.**

---

### Estado

- Índice ampliado con el capítulo 7 (*Estado, pruebas y defectos*) y con el
  prompt de inicialización para NotebookLM.
- Añadido el aviso de que la documentación describe el árbol de trabajo local.
- Última actualización: 2026-11-17.
