<div align="center">

# 🏠 Horus

**Asistente inteligente para el hogar**

> Sistema central que coordina distintas inteligencias artificiales y permite,
> de forma progresiva, interactuar con el hogar.

<br>

![Java](https://img.shields.io/badge/Java-27-orange?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-7.4.5-59666C?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.6-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Estado](https://img.shields.io/badge/estado-en%20desarrollo-ffb000?style=flat-square)
![Licencia](https://img.shields.io/badge/licencia-MIT-green?style=flat-square)

</div>

---

## 📍 Estado

> **En desarrollo activo.** El stack se está ampliando: Angular, Docker,
> servicios de IA y domótica están **previstos, no implementados**. Hoy solo
> existe la base del backend.

| Componente | Estado |
|---|---|
| Backend Spring Boot + JPA + PostgreSQL | ✅ Arranca, conecta y sirve en `:8080` |
| Capas `controller` · `entity` · `repository` · `service` | 📦 Paquetes creados, aún vacíos |
| Endpoints REST | ⏳ Pendiente |
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
| IDE | IntelliJ IDEA |

## 📁 Estructura

```
Project Horus/
├── back_horus/   Backend · Java + Spring Boot + PostgreSQL
├── docs/         Documentación técnica (libro del proyecto)
└── README.md
```

## 📚 Documentación

| | |
|---|---|
| [Visión y alcance](docs/01-vision.md) | [Stack tecnológico](docs/02-stack-tecnologico.md) |
| [Arquitectura](docs/03-arquitectura.md) | [Entorno de desarrollo](docs/04-entorno-desarrollo.md) |
| [Base de datos](docs/05-base-de-datos.md) | [Conceptos técnicos](docs/06-conceptos.md) |
| [Registro de decisiones](docs/decisiones/) | [Índice completo](docs/README.md) |

`docs/` alimenta además un cuaderno de **NotebookLM** que sirve de guía de
aprendizaje del proyecto.

> Para poner el proyecto en marcha → [Entorno de desarrollo](docs/04-entorno-desarrollo.md).

## 🗺️ Hoja de ruta

- [x] Base del backend y conexión a base de datos
- [x] Credenciales fuera del código
- [ ] Primera funcionalidad completa: *entidad → repositorio → servicio → controlador*
- [ ] Tests unitarios y de integración
- [ ] Frontend Angular
- [ ] Docker Compose
- [ ] Coordinación de servicios de IA
- [ ] Integración con dispositivos del hogar

## 📄 Licencia

[MIT](LICENSE) — puedes usar, copiar, modificar y distribuir este proyecto
indicando la autoría.
