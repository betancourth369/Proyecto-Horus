# 04 — Entorno de desarrollo

## Requisitos

- Java 27.
- PostgreSQL 18.6 local, con la base de datos `horus_db` creada.
- IntelliJ IDEA (IDE utilizado).
- Maven Wrapper, incluido en el proyecto (`mvnw` / `mvnw.cmd`).
- Node.js 24.21 y npm 11.19, solo para el frontend.

## Puertos utilizados

| Puerto | Servicio |
|---|---|
| `8080` | Backend Spring Boot |
| `4200` | Servidor de desarrollo de Angular |
| `5432` | PostgreSQL |

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

Para **detenerlo**, `Ctrl + C` en la terminal donde se lanzó. Si quedó un
proceso escuchando en segundo plano:

~~~powershell
Get-NetTCPConnection -LocalPort 8080 -State Listen |
  Select-Object -ExpandProperty OwningProcess -Unique |
  ForEach-Object { Stop-Process -Id $_ -Force }
~~~

## Verificación del arranque (2026-10-21)

| Señal en la consola | Significado |
|---------------------|-------------|
| `HikariPool-1 - Added connection` | Conexión con PostgreSQL correcta |
| `Tomcat started on port 8080` | Servidor HTTP escuchando |
| `Started BackHorusApplication in X seconds` | Arranque completo |

El arranque completo medido el 2026-11-17 fue de **28–30 s** (escaneo de
repositorios, inicialización de Hibernate y conexión a PostgreSQL incluidos).
El puerto es **8080** (`server.port` está comentado en
`application.properties`).

## Cómo arrancar el frontend

El frontend vive en `frontend-horus/` y necesita el backend en marcha para
mostrar datos reales.

~~~bash
cd frontend-horus
npm install          # solo la primera vez
npx ng serve         # servidor de desarrollo en http://localhost:4200
~~~

### Proxy hacia el backend

Los servicios usan rutas relativas (`/api/tasks`, `/api/shopping-items`,
`/api/clima`). `frontend-horus/proxy.conf.json` las redirige a
`http://localhost:8080`, configurado en el bloque `serve` de `angular.json`.

~~~json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
~~~

Motivo: el backend **no tiene CORS configurado**, así que el navegador no puede
llamar directamente a `:8080` desde `:4200`.

> Con el backend parado, el proxy responde **`502` en 20–80 ms** en lugar de
> dejar la petición colgada. El frontend traduce ese `502` a «No se pudo
> conectar con el servidor».

### Clave de la API de clima

`ClimaService` lee la clave de la variable de entorno `WEATHERAPI_KEY`. Sin
ella, la pantalla `/clima` devuelve error de consulta.

## Tiempos de espera

Hay dos grupos de parámetros, porque el clima depende de un servicio externo y
las tareas solo de PostgreSQL.

### Parámetros del clima — IMPLEMENTADO

| Parámetro | Valor | Dónde se define | Qué evita |
|---|---|---|---|
| Timeout de conexión | 3 s | `ClimaService.java` → `TIMEOUT_CONEXION` | Esperar a que WeatherAPI acepte la conexión |
| Timeout de lectura | 8 s | `ClimaService.java` → `TIMEOUT_LECTURA` | Esperar indefinidamente la respuesta de WeatherAPI |
| Red de seguridad del cliente | 20 s | `clima.service.ts` → `tiempoMaximoMs` | Que la pantalla se quede en «cargando» para siempre |
| Vigencia de la caché | 5 min | `clima.service.ts` → `vigenciaCacheMs` | Repetir la consulta en cada navegación |

~~~text
backend: 3 s (conectar) + 8 s (leer) = 11 s como máximo
cliente: 20 s → margen por encima; solo actúa si el proxy deja de contestar
~~~

### Parámetros de tareas y artículos — IMPLEMENTADO

| Parámetro | Valor | Dónde se define | Qué evita |
|---|---|---|---|
| Tiempo límite por petición | 10 s | `peticion-http.ts` → `TIEMPO_MAXIMO_PETICION_MS` | Que la lista se quede en «Cargando...» para siempre |

`conTiempoLimite()` envuelve las 14 llamadas de `TareaService` y
`ShoppingItemService` y traduce cualquier fallo a un `PeticionError` con
mensaje legible. Detalle en `03-arquitectura.md`.

### Mediciones (2026-11-17)

| Caso | Tiempo observado |
|---|---|
| Primera consulta tras arrancar el JVM | 1,5–1,6 s |
| `GET /api/tasks` en caliente | 38–62 ms |
| `PUT /api/tasks/{id}` | 65–167 ms |
| `DELETE /api/tasks/{id}` en caliente | 36–81 ms |
| 4 peticiones de artículos en paralelo | 143 ms |
| Clima en caliente | 61–122 ms |
| Clima justo tras arrancar | ~600 ms |
| Backend parado (proxy devuelve `502`) | 20–84 ms |

Las cifras dependen de la red y del proveedor del clima. La caché del clima se
desactiva poniendo `vigenciaCacheMs` a `0`.

## Compilar

~~~bash
# Backend (desde back_horus/)
./mvnw compile

# Frontend (desde frontend-horus/)
npx ng build
~~~

La salida del frontend se genera en `frontend-horus/dist/frontend-horus`.

> `ng build` tiene un presupuesto de **4 kB por hoja de estilos de componente**.
> Si se supera, aparece un warning. El bloque `.aviso-error` se movió a
> `src/styles.css` precisamente para no rebasarlo.

## Problemas conocidos al ejecutar

| Síntoma | Causa | Dónde se explica |
|---|---|---|
| La lista tarda en pintarse hasta que se hace clic | Falta de `zone.js` y de señales (zoneless) | `07-estado-y-pruebas.md` → D1 |
| Las tarjetas cambian de orden tras actualizar | `GET /api/tasks` sin `Sort` | `07-estado-y-pruebas.md` → D3 |
| La primera petición tarda ~1,5 s | Conexión a PostgreSQL y JIT | Este documento, *Mediciones* |

## Estructura del proyecto

- `back_horus/` — backend (Java + Spring Boot).
- `frontend-horus/` — frontend (Angular, componentes standalone).
- `docs/` — libro de documentación.
- `README.md` — presentación del proyecto.

## Estado

- Arranque del backend verificado: conecta con PostgreSQL y escucha en 8080.
- Frontend compilado con `ng build` sin warnings y servido en 4200 con proxy.
- Añadidos los parámetros de tareas y artículos (10 s) y las mediciones del
  2026-11-17.
- Última actualización: 2026-11-17.
