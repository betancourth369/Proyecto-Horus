# 🏠 Horus

**Asistente inteligente para el hogar**

> Sistema central que coordina distintas inteligencias artificiales y permite, de forma progresiva, interactuar con el hogar.

<br>

![Java](https://img.shields.io/badge/Java-27-orange?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-7.4.5-59666C?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.6-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-22.2-DD0031?style=flat-square&logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-6.0-3178C6?style=flat-square&logo=typescript&logoColor=white)
![Estado](https://img.shields.io/badge/estado-en%20desarrollo-ffb000?style=flat-square)
![Licencia](https://img.shields.io/badge/licencia-MIT-green?style=flat-square)

---

## 📍 Estado

> **En desarrollo activo.** Backend operativo (tareas, lista de la compra y clima) y frontend Angular que consume la API. Docker, servicios de IA y domótica están **previstos, no implementados**.

| Componente | Estado |
|---|---|
| Backend Spring Boot + JPA + PostgreSQL | ✅ Arranca, conecta y sirve en `:8080` |
| Capas `controller` · `dto` · `entity` · `repository` · `service` · `exception` | ✅ Implementadas |
| CRUD de tareas (`/api/tasks`) | ✅ `GET`, `POST`, `GET/{id}`, `PUT/{id}`, `DELETE/{id}` |
| CRUD de artículos (`/api/shopping-items`) | ✅ Incluye rutas anidadas por tarea |
| Relación `Task 1 — N ShoppingItem` | ✅ Implementada y verificada |
| Clima (`/api/clima`) | ✅ Consulta externa a WeatherAPI con tiempos de espera |
| Validación con `@Valid` / `@NotBlank` | ✅ `400` con el detalle del campo |
| Manejo global de errores (`GlobalExceptionHandler`) | ✅ `400`, `404`, `409` con JSON |
| Frontend Angular (standalone) | ✅ Compila y se sirve en `:4200` |
| Proxy `/api` → `:8080` | ✅ `frontend-horus/proxy.conf.json` |
| Tema claro/oscuro | ✅ Selector en el menú, guardado en `localStorage` |
| Tests | ⏳ Solo el test de contexto |
| Verificación visual en navegador | ⏳ Pendiente |
| Docker / IA / domótica | ⏳ Previsto |

> **Limitaciones conocidas** (detalle en [`docs/07-estado-y-pruebas.md`](./docs/07-estado-y-pruebas.md)):
> el frontend no repinta solo tras una petición (Angular zoneless, defecto D1),
> la lista de tareas hace una petición de artículos por tarea (D2) y el orden de
> `GET /api/tasks` no está definido (D3).

## 🧱 Stack

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 27 *(non-LTS)* |
| Framework | Spring Boot 4.1.1 |
| Build | Maven Wrapper (`./mvnw`, no requiere instalar Maven) |
| Persistencia | Spring Data JPA · Hibernate 7.4.5.Final |
| Servidor | Tomcat 11.0.24 *(embebido)* |
| Base de datos | PostgreSQL 18.6 *(local)* |
| Validación | Jakarta Bean Validation (`spring-boot-starter-validation`) |
| Consulta externa | WeatherAPI (`ClimaService`) |
| Frontend | Angular 22.2 · TypeScript 6.0 · RxJS 7.8 (standalone, **zoneless**) |
| Servidor de desarrollo | `@angular/build` en `:4200`, con proxy hacia `:8080` |
| Runtime del frontend | Node.js 24.21 · npm 11.19 |
| IDE | IntelliJ IDEA |

## 📁 Estructura

```
Project Horus/
├── back_horus/                    Backend · Java + Spring Boot + PostgreSQL
│   └── src/main/java/com/project/back_horus/
│       ├── BackHorusApplication.java
│       ├── controller/
│       │   ├── TaskController.java
│       │   ├── ShoppingItemController.java
│       │   └── ClimaController.java
│       ├── dto/
│       │   ├── TaskDTO.java
│       │   ├── ShoppingItemDTO.java
│       │   └── ClimaDTO.java
│       ├── entity/
│       │   ├── Task.java
│       │   └── ShoppingItem.java
│       ├── repository/
│       │   ├── TaskRepository.java
│       │   └── ShoppingItemRepository.java
│       ├── service/
│       │   ├── TaskService.java
│       │   ├── ShoppingItemService.java
│       │   └── ClimaService.java
│       └── exception/
│           ├── BusinessException.java
│           ├── ResourceNotFoundException.java
│           ├── InvalidQuantityException.java
│           ├── DuplicateResourceException.java
│           └── GlobalExceptionHandler.java
├── frontend-horus/                Frontend · Angular 22.2 (standalone)
│   ├── proxy.conf.json            Redirige /api → http://localhost:8080
│   ├── public/theme-init.js       Aplica el tema antes del primer pintado
│   └── src/
│       ├── main.ts                bootstrapApplication(AppComponent)
│       ├── styles.css             Variables de tema y estilos compartidos
│       └── app/
│           ├── app.component.*    Raíz: menú lateral + router-outlet
│           ├── app.config.ts      provideRouter + provideHttpClient
│           ├── app.routes.ts      Rutas de la SPA
│           ├── core/
│           │   ├── models/        tarea · shopping-item · clima
│           │   └── services/      tarea · shopping-item · clima · tema
│           │                      + peticion-http.ts (tiempo límite)
│           ├── shared/            layout/ · components/ reutilizables
│           └── features/          tareas · compra · clima · despensa
├── docs/                          Documentación técnica (libro del proyecto)
└── README.md
```

> `ClimaDTO` es un **DTO**, no una entidad: el clima no se persiste. No existen
> `ClimaEntity` ni `ClimaRepository`.

## 🚀 Puesta en marcha rápida

1. **PostgreSQL** en `:5432` con la base `horus_db` creada.
2. Variables de entorno: `DB_PASSWORD` y `WEATHERAPI_KEY`.
3. **Backend**, desde `back_horus/`:
   ```bash
   ./mvnw spring-boot:run
   ```
4. **Frontend**, desde `frontend-horus/` (otra terminal):
   ```bash
   npm install
   npx ng serve
   ```
5. Abrir `http://localhost:4200`.

> Guía completa, puertos, tiempos de espera y resolución de problemas →
> [`docs/04-entorno-desarrollo.md`](./docs/04-entorno-desarrollo.md).

## 📚 Documentación

| | |
|---|---|
| [Visión y alcance](./docs/01-vision.md) | Visión, alcance confirmado e implementado vs previsto |
| [Stack tecnológico](./docs/02-stack-tecnologico.md) | Tecnologías actuales y previstas |
| [Arquitectura](./docs/03-arquitectura.md) | Backend, frontend, endpoints y flujo de datos |
| [Entorno de desarrollo](./docs/04-entorno-desarrollo.md) | Cómo arrancar y configurar el proyecto |
| [Base de datos](./docs/05-base-de-datos.md) | Esquema real y persistencia |
| [Conceptos aprendidos](./docs/06-conceptos.md) | Glosario y conceptos técnicos |
| [Estado, pruebas y defectos](./docs/07-estado-y-pruebas.md) | Auditoría, mediciones y defectos abiertos |
| [Registro de decisiones](./docs/decisiones/) | Decisiones técnicas (ADR) |
| [Índice completo](./docs/README.md) | Índice de toda la documentación |

`docs/` alimenta además un cuaderno de **NotebookLM** que sirve de guía de referencia y aprendizaje del proyecto (procedimiento en [`docs/README.md`](./docs/README.md)).

## 🗺️ Hoja de ruta

- [x] Base del backend y conexión a base de datos
- [x] Credenciales fuera del código
- [x] Primera funcionalidad completa: *entidad → repositorio → servicio → controlador* (tareas)
- [x] Validación de entrada con DTO y `GlobalExceptionHandler`
- [x] Segunda funcionalidad: lista de la compra (`ShoppingItem`)
- [x] Tercera funcionalidad: consulta de clima mediante API externa (`/api/clima`)
- [x] Relación `Task` ↔ `ShoppingItem` con rutas anidadas
- [x] Frontend Angular con proxy hacia el backend y diseño claro/oscuro
- [x] Tiempos de espera y mensajes de error en tareas, artículos y clima
- [ ] Corregir el repintado del frontend (defecto D1)
- [ ] Verificación visual del frontend en navegador
- [ ] Tests unitarios y de integración
- [ ] Docker Compose
- [ ] Coordinación de servicios de IA
- [ ] Integración con dispositivos del hogar

## 📄 Licencia

[MIT](LICENSE) — puedes usar, copiar, modificar y distribuir este proyecto indicando la autoría.

---

*Última actualización de este README: 2026-11-17.*
