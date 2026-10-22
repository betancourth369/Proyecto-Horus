# 04 — Entorno de desarrollo

## Requisitos

- Java 27.
- PostgreSQL 18.6 local, con la base de datos `horus_db` creada.
- IntelliJ IDEA (IDE utilizado).
- Maven Wrapper, incluido en el proyecto (`mvnw` / `mvnw.cmd`).

## Credenciales

La contraseña de PostgreSQL **no** está en el código. Se lee de la variable de
entorno `DB_PASSWORD`:

~~~properties
spring.datasource.password=${DB_PASSWORD}
~~~

Definirla **una vez** en Windows (persistente: la heredan IntelliJ, la terminal
y Maven):

~~~bash
read -s -p "Contraseña: " DB_PASSWORD
setx DB_PASSWORD "$DB_PASSWORD"
unset DB_PASSWORD
~~~

> Tras `setx` hay que **reiniciar IntelliJ**: los procesos heredan las variables
> de entorno al arrancar, no en caliente.

## Cómo arrancar el backend

Desde la carpeta `back_horus/`:

~~~bash
./mvnw spring-boot:run      # Git Bash
mvnw.cmd spring-boot:run    # Windows (cmd / PowerShell)
~~~

O desde IntelliJ: botón ▶️ sobre `BackHorusApplication`.

## Verificación del arranque (2026-10-21)

| Señal en la consola | Significado |
|---------------------|-------------|
| `HikariPool-1 - Added connection` | Conexión con PostgreSQL correcta |
| `Tomcat started on port 8080` | Servidor HTTP escuchando |
| `Started BackHorusApplication in X seconds` | Arranque completo |

El puerto es **8080** (`server.port` está comentado en `application.properties`).

## Estructura del proyecto

- `back_horus/` — backend (Java + Spring Boot).
- `docs/` — libro de documentación.
- `README.md` — presentación del proyecto.

## Estado

- Arranque verificado: la aplicación conecta con PostgreSQL y escucha en 8080.
- Última actualización: 2026-10-21.
