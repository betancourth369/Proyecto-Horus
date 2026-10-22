# 05 — Base de datos

## Motor

PostgreSQL 18.6 (instalación local).

## Conexión

Definida en `back_horus/src/main/resources/application.properties`:

| Clave | Valor |
|-------|-------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/horus_db` |
| `spring.datasource.username` | usuario de PostgreSQL con permisos sobre `horus_db` |
| `spring.datasource.password` | `${DB_PASSWORD}` (variable de entorno) |

> **Este documento no contiene contraseñas.** El valor real vive únicamente en
> la variable de entorno `DB_PASSWORD` del sistema.

## Gestión de credenciales (resuelto)

- La contraseña **no** está en el código ni en el repositorio.
- Se define una sola vez en Windows con `setx DB_PASSWORD "..."`
  (ver `04-entorno-desarrollo.md`).
- Spring Boot resuelve el marcador `${DB_PASSWORD}` al arrancar.
- Si la variable no existe, la aplicación **falla al arrancar** con un error
  explícito, en lugar de usar un valor codificado en el archivo.

## Pool de conexiones

Spring Boot configura **HikariCP** automáticamente. El log muestra
`HikariPool-1 - Added connection` cuando la conexión es correcta.

## JPA / Hibernate

| Propiedad | Valor | Comentario |
|-----------|-------|------------|
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate actualiza el esquema. **No recomendado en producción** |
| `spring.jpa.show-sql` | `true` | Muestra SQL por consola |
| `spring.jpa.open-in-view` | `false` | La sesión se cierra al terminar la petición |
| `spring.jpa.properties.hibernate.format_sql` | `true` | SQL formateado |

## Pendiente

- Evaluar una estrategia de migraciones (Flyway o Liquibase) para producción.
- Definir el esquema de la primera versión.

## Estado

- Conexión verificada el 2026-10-21.
- Última actualización: 2026-10-21.
