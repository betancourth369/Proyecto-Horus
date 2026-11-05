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
| 1 | [Visión y alcance](./01-vision.md) | Visión del proyecto, alcance y evolución |
| 2 | [Stack tecnológico](./02-stack-tecnologico.md) | Tecnologías actuales y previstas |
| 3 | [Arquitectura](./03-arquitectura.md) | Arquitectura de capas y flujo de datos |
| 4 | [Entorno de desarrollo](./04-entorno-desarrollo.md) | Cómo arrancar y configurar el proyecto |
| 5 | [Base de datos](./05-base-de-datos.md) | Esquema y persistencia |
| 6 | [Conceptos aprendidos](./06-conceptos.md) | Glosario y conceptos técnicos |
| 7 | [Decisiones](./decisiones/0001-stack-inicial.md) | Registro de decisiones técnicas |

## Estado actual de la documentación

| Documento | Estado | Última actualización |
|---|---|---|
| `docs/01-vision.md` | IMPLEMENTADO | 2026-10-21 |
| `docs/02-stack-tecnologico.md` | IMPLEMENTADO | 2026-10-21 |
| `docs/03-arquitectura.md` | IMPLEMENTADO | 2026-11-03 |
| `docs/04-entorno-desarrollo.md` | IMPLEMENTADO | 2026-10-21 |
| `docs/05-base-de-datos.md` | IMPLEMENTADO | 2026-10-21 |
| `docs/06-conceptos.md` | IMPLEMENTADO | 2026-10-21 |
| `docs/decisiones/0001-stack-inicial.md` | IMPLEMENTADO | 2026-10-21 |

> Los estados marcan qué contenido de la documentación refleja código ya implementado y qué está planeado. Consulta cada archivo para ver la separación entre lo implementado y lo previsto.

## Uso con NotebookLM

Este libro está pensado para subirse como **fuente** de un cuaderno de
NotebookLM, que lo usa como guía de referencia y de aprendizaje del proyecto.

1. Crear un cuaderno en NotebookLM.
2. Subir estos archivos `.md` como fuentes (uno por archivo).
3. Al avanzar el proyecto, volver a subir los archivos modificados.

**Nombre del cuaderno:** `Horus — Guía y referencia del proyecto`

**Descripción:** *Cuaderno de referencia y aprendizaje del proyecto Horus, un
asistente inteligente para el hogar construido con Spring Boot, PostgreSQL y, en
el futuro, Angular. Contiene la visión, la arquitectura, el stack, el entorno de
desarrollo, la base de datos y las decisiones técnicas.*

## Reglas de escritura y actualización

- **Fuente única de verdad**: `docs/` es la única fuente autorizada sobre el proyecto. No inventes información que no esté en los archivos.
- **Distinguir estados**: Marca claramente qué está implementado, qué está previsto y qué no está verificado.
- **No secretos**: Nunca incluyas contraseñas, tokens, claves privadas ni credenciales.
- **No duplicar información**: Si un dato ya existe en otro capítulo de `docs/`, referencia ese archivo en lugar de repetirlo.
- **Citar fuentes**: Cuando hagas referencia a una decisión o característica técnica, indica el archivo de origen (ej. `docs/04-entorno-desarrollo.md`).

---

### Estado

- **Última actualización:** 2026-11-05
