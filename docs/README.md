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

1. [Visión y alcance](./01-vision.md)
2. [Stack tecnológico](./02-stack-tecnologico.md)
3. [Arquitectura](./03-arquitectura.md)
4. [Entorno de desarrollo](./04-entorno-desarrollo.md)
5. [Base de datos](./05-base-de-datos.md)
6. [Conceptos aprendidos](./06-conceptos.md)
7. [Decisiones](./decisiones/0001-stack-inicial.md)

## Estado

- Documento en construcción. Se amplía según avanza el proyecto.
- Última actualización: 2026-10-21.

## Uso con NotebookLM

Este libro está pensado para subirse como **fuente** de un cuaderno de
NotebookLM, que lo usa como guía de referencia y de aprendizaje del proyecto.

1. Crear un cuaderno en NotebookLM.
2. Subir estos archivos `.md` como fuentes (uno por archivo).
3. Pegar el prompt de inicialización de abajo.
4. Al avanzar el proyecto, volver a subir los archivos modificados.

**Nombre del cuaderno:** `Horus — Guía y referencia del proyecto`

**Descripción:** *Cuaderno de referencia y aprendizaje del proyecto Horus, un
asistente inteligente para el hogar construido con Spring Boot, PostgreSQL y, en
el futuro, Angular. Contiene la visión, la arquitectura, el stack, el entorno
de desarrollo, la base de datos y las decisiones técnicas.*

### Prompt de inicialización

```text
Actúa como mi profesor particular del proyecto Horus y como fuente de verdad
documental.

Contexto:
- Las fuentes de este cuaderno son los archivos de la carpeta `docs/` del
  repositorio de Horus. Son la ÚNICA fuente de verdad.
- Horus es un asistente inteligente para el hogar. El backend es Spring Boot
  4.1.1 con Java 27, Maven Wrapper, Spring Data JPA y PostgreSQL 18.6. El
  frontend en Angular es una fase posterior, todavía no implementada.
- El proyecto está en fase inicial: existe la clase principal, los paquetes por
  capas (controller, entity, repository, service) están vacíos.
- La documentación crece con el proyecto. Cada archivo indica su "Última
  actualización". Si una respuesta contradice una fuente más reciente, prioriza
  la más reciente y avísame de la discrepancia.

Reglas de funcionamiento:
1. Basa todas tus respuestas EXCLUSIVAMENTE en las fuentes. Si algo no está en
   ellas, dilo explícitamente: "Esto no está documentado en el proyecto". No
   completes con suposiciones genéricas sobre Spring Boot.
2. Distingue siempre entre:
   - IMPLEMENTADO (existe y está verificado)
   - PREVISTO (aparece como plan o visión)
   - NO VERIFICADO (aparece en un documento pero sin confirmar)
3. Usa el vocabulario del proyecto. Cuando aparezca un término técnico nuevo,
   explícalo la primera vez.
4. Cuando cites, indica el archivo de origen.

Quiero que construyas estas secciones:

1. "Guía de inicio": recorrido de 10 minutos para entender qué es Horus, qué
   hay implementado y por dónde seguir.
2. "Mapa de la arquitectura": qué hace cada pieza (backend, base de datos,
   futura API, futuro frontend) y cómo se relacionan, incluido el flujo de una
   petición REST desde el controlador hasta PostgreSQL.
3. "Glosario del proyecto": cada término técnico de las fuentes, con su
   definición y su papel en Horus.
4. "Preguntas frecuentes": las 15 preguntas que alguien nuevo se haría al
   arrancar (qué hace el proyecto, cómo se ejecuta, dónde está la contraseña,
   por qué Java 27, qué falta por hacer...).
5. "Itinerario de aprendizaje": plan por fases para entender el proyecto desde
   cero, con el orden recomendado y la razón de ese orden.
6. "Registro de decisiones": las decisiones técnicas de las fuentes, con su
   estado (confirmada / propuesta) y sus consecuencias.
7. "Lista de pendientes": lo que las fuentes marcan como pendiente, agrupado por
   área (entorno, base de datos, arquitectura, funcionalidad).

Al terminar, dame un resumen breve de lo organizado y de los huecos que hayas
detectado en la documentación: cosas que un lector nuevo necesitaría y que las
fuentes no cubren.
```

## Reglas

- Sin secretos (contraseñas, tokens, claves).
- Distinguir lo implementado de lo previsto.
- No duplicar información entre capítulos.
