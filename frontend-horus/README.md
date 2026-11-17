# Horus — Frontend

Frontend del Proyecto Horus: **Angular 22.2** con TypeScript 6.0, RxJS 7.8 y
componentes **standalone** (sin `NgModules`). Consume la API REST del backend
Spring Boot a través de un proxy del servidor de desarrollo.

> Visión general del proyecto: [`../README.md`](../README.md).
> Arquitectura y decisiones del frontend: [`../docs/03-arquitectura.md`](../docs/03-arquitectura.md).

## Requisitos

- Node.js 24.21 y npm 11.19.
- Backend en marcha en `http://localhost:8080` para ver datos reales
  ([`../docs/04-entorno-desarrollo.md`](../docs/04-entorno-desarrollo.md)).

## Puesta en marcha

```bash
npm install     # solo la primera vez
npx ng serve    # http://localhost:4200
```

El servidor de desarrollo usa `proxy.conf.json`, configurado en el bloque
`serve` de `angular.json`:

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

Motivo: el backend **no tiene CORS**, así que el navegador no puede llamar
directamente a `:8080` desde `:4200`. Los servicios usan siempre rutas
relativas (`/api/tasks`, `/api/shopping-items`, `/api/clima`).

## Rutas

| Ruta | Pantalla |
|---|---|
| `/tareas` | Listado de tareas con filtros, artículos y progreso |
| `/tareas/nueva` | Formulario de creación |
| `/compra` | Lista de la compra |
| `/clima` | Consulta del clima por ciudad |
| `/despensa` | Marcada «en construcción» |
| `**` | Redirige a `/tareas` |

## Estructura

```
src/
├── main.ts               bootstrapApplication(AppComponent, appConfig)
├── index.html            Documento raíz (lang="es")
├── styles.css            Variables de tema claro/oscuro y estilos compartidos
├── public/theme-init.js  Aplica el tema guardado antes del primer pintado
└── app/
    ├── app.component.*   Raíz: menú lateral + contenedor + router-outlet
    ├── app.config.ts     provideRouter + provideHttpClient
    ├── app.routes.ts     Rutas de la SPA
    ├── core/
    │   ├── models/       tarea.model.ts · shopping-item.model.ts · clima.model.ts
    │   └── services/     tarea · shopping-item · clima · tema (.service.ts)
    │                     peticion-http.ts (tiempo límite y traducción de errores)
    ├── shared/
    │   ├── layout/       menu-lateral · contenedor-principal
    │   └── components/   chip-estado · estado-vacio · tarjeta-cristal
    │                     boton · barra-progreso
    └── features/
        ├── tareas/pages/    lista-tareas · nueva-tarea
        ├── compra/pages/    lista-compra
        ├── clima/pages/     clima
        └── despensa/pages/  despensa
```

Cada componente usa **tres archivos separados**: `.ts`, `.html` y `.css`. No hay
plantillas ni estilos embebidos.

## Compilar

```bash
npx ng build
```

Salida en `dist/frontend-horus`. Presupuesto de **4 kB** por hoja de estilos de
componente: si un `.css` crece por encima, aparece un warning; los estilos
compartidos (por ejemplo `.aviso-error`) van en `src/styles.css`.

## Tema claro/oscuro

- Variables CSS en `src/styles.css` y `data-theme="claro|oscuro"` en `<html>`.
- `public/theme-init.js` aplica el tema guardado **antes** del primer pintado.
- `TemaService` alterna el tema y lo guarda en `localStorage` (`horus-tema`).
- Sin elección previa se respeta `prefers-color-scheme`.

## Tiempos de espera y errores

`core/services/peticion-http.ts` define `conTiempoLimite()` (10 s),
`PeticionError` y `mensajeDeError()`. Envuelve las llamadas de tareas y
artículos, de modo que un backend caído produce un mensaje legible en lugar de
dejar la pantalla en «Cargando...». El clima tiene sus propios valores (20 s y
caché de 5 min).

## Limitaciones conocidas

- **La interfaz no se repinta sola tras una petición.** Angular v21+ es
  zoneless por defecto y este proyecto no carga `zone.js`; los componentes no
  usan señales para el estado de datos, así que los cambios se ven en el
  siguiente evento de plantilla (clic). Defecto **D1** en
  [`../docs/07-estado-y-pruebas.md`](../docs/07-estado-y-pruebas.md).
- Una petición de artículos por cada tarea al cargar `/tareas` (D2).
- Sin pruebas automatizadas.

---

*Última actualización: 2026-11-17.*
