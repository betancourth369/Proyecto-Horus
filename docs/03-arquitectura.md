# 03 — Arquitectura

## Estado actual (verificado)

- Un único módulo: `back_horus/`.
- Una clase principal: `BackHorusApplication`.
- Sin entidades, repositorios, servicios ni controladores implementados.
- Los paquetes por capas **existen, pero están vacíos**.

### Estructura de carpetas (actual)

```
back_horus/
└── src/
    ├── main/
    │   ├── java/com/project/back_horus/
    │   │   ├── BackHorusApplication.java
    │   │   ├── controller/      (vacío)
    │   │   ├── entity/          (vacío)
    │   │   ├── repository/      (vacío)
    │   │   └── service/         (vacío)
    │   └── resources/
    │       ├── application.properties
    │       ├── static/          (vacío)
    │       └── templates/       (vacío)
    └── test/
        └── java/com/project/back_horus/
            └── BackHorusApplicationTests.java
```

> Git no registra directorios vacíos: esos paquetes no aparecerán en el
> repositorio hasta que contengan al menos un archivo.

## Arquitectura prevista (no implementada)

- Monolito modular.
- Capas: `controller` → `service` → `repository` → `entity`.
- Comunicación mediante API REST.
- Frontend Angular separado (fase posterior).

## Pendiente

- Definir los módulos del monolito.
- Poblar las capas con la primera funcionalidad.

## Estado

- Documento de arquitectura. Lo previsto no está implementado.
- Última actualización: 2026-10-21.
