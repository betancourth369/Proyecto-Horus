# ADR 0001 — Stack inicial del backend

- **Estado:** Aceptado
- **Fecha:** 2026-10-21 (propuesto) · 2026-10-22 (confirmado por el usuario)

## Contexto

Horus necesita un backend que exponga una API REST y persista datos del hogar
(habitaciones, dispositivos, etc.). El repositorio ya contiene un backend
generado con Spring Boot, con la estructura característica de Spring Initializr
(`mvnw`, `src/main/java`, `src/test/java`, `HELP.md`).

## Decisión

Se confirma el uso de **Java + Spring Boot + Spring Data JPA + PostgreSQL** para
el backend.

### Versiones verificadas en ejecución (2026-10-21)

| Componente | Versión |
|---|---|
| Spring Boot | 4.1.1 |
| Java | 27 (non-LTS) |
| Spring Data JPA / Hibernate | 7.4.5.Final |
| Tomcat embebido | 11.0.24 |
| PostgreSQL | 18.6 |
| Pool de conexiones | HikariCP |
| Puerto HTTP | 8080 |

## Motivos

- **Spring Boot** cubre las dos necesidades del backend (API REST y
  persistencia) con un único conjunto de dependencias y configuración mínima.
- **Spring Data JPA** permite modelar el dominio del hogar con entidades y
  relaciones, sin escribir SQL para las operaciones básicas.
- **PostgreSQL** es gratuito, mantenido y relacional; el modelo del hogar lo es
  (una habitación contiene dispositivos).
- **Maven Wrapper** (`mvnw`) evita depender de una instalación de Maven en el
  equipo.
- Es un stack con documentación abundante, lo que encaja con el objetivo de
  aprendizaje del proyecto.

## Alternativas

No se realizó una comparación formal de alternativas: el backend partió de la
plantilla de Spring Initializr y se adopta tras verificar su funcionamiento.

Si una necesidad concreta exige comparar (rendimiento, despliegue, cambio de
motor de base de datos), se abrirá una **nueva ADR** que analice las opciones y,
en su caso, sustituya a esta.

## Consecuencias

### Positivas

- Un solo lenguaje y un solo proceso para la API y la persistencia.
- El modelo de datos evoluciona con clases Java versionadas en Git.

### Negativas y riesgos asumidos

- **Java 27 es non-LTS**: deja de recibir actualizaciones antes que las
  versiones LTS (21, 25). Migrar a una LTS no exige reescribir código y debe
  hacerse antes de que la versión instalada quede sin soporte.
- **PostgreSQL local**: el desarrollo depende de una instancia en el puerto
  5432. Docker se valorará cuando aporte valor práctico.
- **Sin frontend**: Angular es una fase posterior y **no** forma parte de esta
  decisión; requerirá su propia ADR.

## Cómo se cambia esta decisión

Esta ADR queda aceptada. Cualquier cambio de stack se documentará en una **nueva
ADR** que indique qué sustituye y por qué; esta no se reescribe.
