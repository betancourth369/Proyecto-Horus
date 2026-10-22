# 02 — Stack tecnológico

## Estado actual (verificado)

Deducido del `pom.xml`, de la estructura del backend y del **log real de
arranque** de la aplicación (2026-10-21):

| Tecnología | Versión | Función |
|-----------|---------|---------|
| Java | 27 (non-LTS) | Lenguaje de programación |
| Spring Boot | 4.1.1 | Framework base |
| Maven Wrapper | — | Build y gestión de dependencias |
| Spring Web MVC | (heredado) | API REST (Tomcat embebido 11.0.24) |
| Spring Data JPA | (heredado) | Persistencia |
| Hibernate ORM | 7.4.5.Final | Mapeo objeto-relacional |
| PostgreSQL | servidor 18.6 | Base de datos relacional |
| HikariCP | (gestionado por Boot) | Pool de conexiones |
| Lombok | 1.18.48 | Reducir código repetitivo |

> **Nota:** Java 27 es una versión **non-LTS**. El LTS actual es Java 25.
> Su uso es una elección a revisar, no una recomendación.

## Previsto (no implementado)

- Angular como frontend (fase posterior).
- Docker / Docker Compose (cuando aporte valor).

## Pendiente

- Confirmar el stack como decisión definitiva (ver `decisiones/0001-stack-inicial.md`).
- Evaluar Java 25 LTS frente a Java 27.

## Estado

- Datos verificados del `pom.xml` y del arranque real de la aplicación.
- Última actualización: 2026-10-21.
