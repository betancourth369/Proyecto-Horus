# 02 — Stack tecnológico

## Estado actual — IMPLEMENTADO (verificado)

Deducido del `pom.xml`, de `frontend-horus/package.json`, de la estructura del
código y de los **logs reales de arranque** (última comprobación: 2026-11-17).

### Backend

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
| Jakarta Bean Validation | `spring-boot-starter-validation` | `@NotBlank`, `@Valid` |
| WeatherAPI | servicio externo | Datos del clima (`ClimaService`) |

> **Nota:** Java 27 es una versión **non-LTS**. El LTS actual es Java 25.
> Su uso es una elección a revisar, no una recomendación
> (`decisiones/0001-stack-inicial.md`).

### Frontend

| Tecnología | Versión | Función |
|-----------|---------|---------|
| Angular | 22.2 | SPA con componentes **standalone** (`frontend-horus/`) |
| TypeScript | 6.0 | Lenguaje del frontend |
| RxJS | 7.8 | Reactividad y consumo HTTP (`HttpClient`) |
| Node.js | 24.21 | Runtime de desarrollo |
| npm | 11.19 | Gestión de dependencias |
| `@angular/build` | (incluido) | Servidor de desarrollo en `:4200` |

> **Nota:** el frontend consume la API en rutas relativas `/api/...` y el
> servidor de desarrollo las redirige a `http://localhost:8080` mediante
> `frontend-horus/proxy.conf.json`. El backend no tiene CORS configurado.
> Detalle en `04-entorno-desarrollo.md`.

### Angular sin `zone.js` — IMPORTANTE

Angular v21+ es **zoneless por defecto**. Este proyecto **no incluye
`zone.js`**: no aparece en `package.json` ni en `polyfills` de `angular.json`.

Consecuencia: la detección de cambios no se dispara automáticamente al
completarse una petición HTTP. El estado asignado dentro de `subscribe` no
repinta la vista hasta el siguiente evento de plantilla. Es el defecto abierto
**D1** descrito en `07-estado-y-pruebas.md`.

## Previsto — NO IMPLEMENTADO

| Tecnología | Cuándo | Nota |
|---|---|---|
| Docker / Docker Compose | Cuando aporte valor práctico | Sin decidir versión ni alcance |
| LLM para comandos en lenguaje natural | Fase posterior a la demostración | Sin proveedor elegido |
| Autenticación (Spring Security) | Si se añaden usuarios | Cambia el modelo de datos |

## Pendiente

- Confirmar el stack como decisión definitiva
  (`decisiones/0001-stack-inicial.md`).
- Evaluar Java 25 LTS frente a Java 27.
- Cerrar la estrategia de migraciones de esquema (Flyway / Liquibase).
- Elegir el mecanismo de repintado del frontend (señales, `zone.js` o
  `markForCheck`), ver `07-estado-y-pruebas.md`.

## Estado

- Backend verificado del `pom.xml` y del arranque real de la aplicación.
- Frontend verificado en `frontend-horus/package.json` y compilado con
  `ng build` sin warnings.
- Añadida la nota de Angular zoneless (2026-11-17).
- Última actualización: 2026-11-17.
