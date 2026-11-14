# 🏠 Horus

**Asistente inteligente para el hogar**

> Sistema central que coordina distintas inteligencias artificiales y permite, de forma progresiva, interactuar con el hogar.

<br>

![Java](https://img.shields.io/badge/Java-27-orange?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-7.4.5-59666C?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.6-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Estado](https://img.shields.io/badge/estado-en%20desarrollo-ffb000?style=flat-square)
![Licencia](https://img.shields.io/badge/licencia-MIT-green?style=flat-square)

---

## 📍 Estado

> **En desarrollo activo.** El backend está operativo con tres funcionalidades: gestión de tareas, lista de la compra y consulta de clima. Angular, Docker, servicios de IA y domótica están **previstos, no implementados**.

| Componente | Estado |
|---|---|
| Backend Spring Boot + JPA + PostgreSQL | ✅ Arranca, conecta y sirve en `:8080` |
| Capas `controller` · `entity` · `repository` · `service` · `dto` · `exception` | ✅ Implementadas |
| Endpoints REST (`/api/tasks`) | ✅ CRUD completo: `GET`, `POST`, `GET/{id}`, `PUT/{id}`, `DELETE/{id}` |
| Endpoints REST (`/api/shopping-items`) | ✅ CRUD completo: `GET`, `POST`, `GET/{id}`, `PUT/{id}`, `DELETE/{id}` |
| Endpoints REST (`/api/clima`) | ✅ `GET` con consulta externa a WeatherAPI |
| Validación de entrada con `@Valid` y `@NotBlank` | ✅ Implementada (`400 Bad Request` con detalle) |
| Manejo global de excepciones (`GlobalExceptionHandler`) | ✅ Implementado |
| Tests | ⏳ Solo el test de contexto |
| Frontend Angular | ⏳ Fase posterior |
| Docker / IA / domótica | ⏳ Previsto |

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
| Consulta externa | WeatherAPI (`back_horus/src/main/java/com/project/back_horus/service/ClimaService.java`) |
| IDE | IntelliJ IDEA |

## 📁 Estructura

```
Project Horus/
├── back_horus/   Backend · Java + Spring Boot + PostgreSQL
│   ├── controller/   Controladores REST (@RestController)
│   │   ├── TaskController.java
│   │   ├── ShoppingItemController.java
│   │   └── ClimaController.java
│   ├── dto/          Objetos de transferencia de datos (@Data)
│   │   ├── TaskDTO.java
│   │   ├── ShoppingItemDTO.java
│   │   └── ClimaDTO.java
│   ├── entity/       Entidades JPA (@Entity)
│   │   ├── Task.java
│   │   ├── ShoppingItem.java
│   │   └── ClimaDTO.java (usado como DTO, no entidad)
│   ├── repository/   Repositorios Spring Data JPA
│   │   ├── TaskRepository.java
│   │   ├── ShoppingItemRepository.java
│   │   └── (Clima: consulta a API externa, sin repositorio)
│   ├── service/      Lógica de negocio y conversión DTO ↔ Entity
│   │   ├── TaskService.java
│   │   ├── ShoppingItemService.java
│   │   └── ClimaService.java
│   ├── exception/    Manejo global de excepciones (@RestControllerAdvice)
│   │   └── GlobalExceptionHandler.java
│   └── ...
├── docs/         Documentación técnica (libro del proyecto)
└── README.md
```

## 📚 Documentación

| | |
|---|---|
| [Visión y alcance](./docs/01-vision.md) | Visión del proyecto, alcance y evolución |
| [Stack tecnológico](./docs/02-stack-tecnologico.md) | Tecnologías actuales y previstas |
| [Arquitectura](./docs/03-arquitectura.md) | Arquitectura de capas y flujo de datos |
| [Entorno de desarrollo](./docs/04-entorno-desarrollo.md) | Cómo arrancar y configurar el proyecto |
| [Base de datos](./docs/05-base-de-datos.md) | Esquema y persistencia |
| [Conceptos aprendidos](./docs/06-conceptos.md) | Glosario y conceptos técnicos |
| [Registro de decisiones](./docs/decisiones/) | Registro de decisiones técnicas |
| [Índice completo](./docs/README.md) | Índice de toda la documentación |

`docs/` alimenta además un cuaderno de **NotebookLM** que sirve de guía de referencia y aprendizaje del proyecto.

> Para poner el proyecto en marcha → [Entorno de desarrollo](./docs/04-entorno-desarrollo.md).

## 🗺️ Hoja de ruta

- [x] Base del backend y conexión a base de datos
- [x] Credenciales fuera del código
- [x] Primera funcionalidad completa: *entidad → repositorio → servicio → controlador* (tareas)
- [x] Validación de entrada con DTO y `GlobalExceptionHandler`
- [x] Segunda funcionalidad: lista de la compra (`ShoppingItem`)
- [x] Tercera funcionalidad: consulta de clima mediante API externa (`/api/clima`)
- [ ] Tests unitarios y de integración
- [ ] Frontend Angular
- [ ] Docker Compose
- [ ] Coordinación de servicios de IA
- [ ] Integración con dispositivos del hogar

## 📄 Licencia

[MIT](LICENSE) — puedes usar, copiar, modificar y distribuir este proyecto indicando la autoría.