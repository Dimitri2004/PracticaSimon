# 🏷️ Etiquetas (Labels) Necesarias para GitHub

Este archivo lista todas las etiquetas que necesitas crear en tu repositorio GitHub antes de asignarlas a los issues.

## 📋 Cómo Crear Etiquetas en GitHub

1. Ve a tu repositorio: https://github.com/Dimitri2004/PracticaSimon
2. Haz clic en **"Labels"** (dentro de la sección Issues)
3. Haz clic en **"New label"**
4. Completa los campos: Nombre, Descripción, Color
5. Haz clic en **"Create label"**

---

## 🎨 Etiquetas por Categoría

### 🔵 Categoría: MongoDB
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `mongodb` | Relacionado con MongoDB o Realm SDK | #0288D1 | TASK-001 a 007, 009, 015, 033 |
| `database` | Operaciones de base de datos | #FF6F00 | TASK-001, 002, 011 |
| `cloud` | Servicios en la nube | #7B1FA2 | TASK-001 |

### 🟢 Categoría: Build & Gradle
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `gradle` | Relacionado con Gradle o build system | #0F7C3D | TASK-006, 007, 008 |
| `build` | Proceso de compilación o construcción | #28A745 | TASK-007, 008 |
| `dependencies` | Gestión de dependencias | #1F883D | TASK-006 |

### 🟡 Categoría: Arquitectura y Datos
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `architecture` | Decisiones o cambios arquitectónicos | #FFB300 | TASK-009, 010, 011 |
| `data-model` | Modelos de datos o entidades | #FFC107 | TASK-009 |
| `repository` | Patrón Repository o acceso a datos | #FF9800 | TASK-010, 011 |
| `local-data` | Datos locales o almacenamiento local | #FF6F00 | TASK-011 |

### 🔴 Categoría: CRUD Operations
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `crud` | Operaciones Create, Read, Update, Delete | #D32F2F | TASK-015 |
| `create` | Operación de creación (CREATE) | #F44336 | TASK-015 |
| `read` | Operación de lectura (READ) | #EF5350 | (Futuro) |
| `update` | Operación de actualización (UPDATE) | #E57373 | (Futuro) |
| `delete` | Operación de eliminación (DELETE) | #FFCDD2 | (Futuro) |

### 🔐 Categoría: Seguridad y Red
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `security` | Asuntos de seguridad | #D32F2F | TASK-003, 004 |
| `network` | Conectividad de red | #1976D2 | TASK-020 |
| `connectivity` | Estado de conexión | #42A5F5 | TASK-020 |
| `permissions` | Permisos de aplicación | #5C6BC0 | TASK-041 |

### 🎨 Categoría: UI/UX
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `ui` | Interfaz de usuario | #00BCD4 | TASK-033 |
| `compose` | Jetpack Compose o UI framework | #00ACC1 | TASK-033 |
| `download` | Funcionalidad de descarga | #009688 | TASK-033 |

### ⚙️ Categoría: Configuración
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `configuration` | Archivos de configuración | #9C27B0 | TASK-005, 041 |
| `android-manifest` | AndroidManifest.xml | #BA68C8 | TASK-041 |
| `utility` | Clases de utilidad o helper | #CE93D8 | TASK-020 |

### 📌 Categoría: Fase/Estado
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `feature` | Nueva característica | #0366D6 | TASK-001 a 041 (todos) |
| `phase-1` | Fase 1: Preparación | #BFD4F5 | TASK-001 a 008 |
| `phase-2` | Fase 2: Arquitectura | #DCEDC1 | TASK-009 a 011 |
| `phase-3` | Fase 3: CRUD | #FFE0B2 | TASK-015, 020 |
| `phase-5` | Fase 5: UI | #B3E5FC | TASK-033 |
| `phase-6` | Fase 6: Seguridad | #FFCCBC | TASK-041 |

### ⚡ Categoría: Prioridad
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `priority-high` | Prioridad alta - Crítica | #D32F2F | TASK-001-005, 015 |
| `priority-medium` | Prioridad media - Importante | #F57F17 | TASK-006-011, 020, 033, 041 |
| `priority-low` | Prioridad baja - Opcional | #689F38 | (Futuro) |

### 🏷️ Categoría: Tipo de Trabajo
| Etiqueta | Descripción | Color Sugerido | Usado en |
|----------|-------------|---|---|
| `verification` | Verificación o testing | #4527A0 | TASK-008 |
| `interface` | Interfaces o contratos | #5E35B1 | TASK-010 |

---

## 📊 Resumen Total

**Total de etiquetas recomendadas:** 35

| Categoría | Cantidad | Colores |
|-----------|----------|---------|
| MongoDB | 3 | Azules |
| Build & Gradle | 3 | Verdes |
| Arquitectura | 4 | Naranjas |
| CRUD | 5 | Rojos |
| Seguridad | 4 | Rojos/Azules |
| UI/UX | 3 | Cian/Teal |
| Configuración | 3 | Púrpura |
| Fase/Estado | 7 | Variados |
| Prioridad | 3 | Variados |
| Tipo | 2 | Púrpura |
| **TOTAL** | **37** | **-** |

---

## 🎨 Paleta de Colores Recomendada

Aquí te muestro cómo GitHub asigna colores automáticamente:

```
🔵 Azules (Networking, Información)
  - #0288D1: mongodb
  - #1976D2: network
  - #0366D6: feature
  - #42A5F5: connectivity

🟢 Verdes (Build, Positivo)
  - #0F7C3D: gradle
  - #28A745: build
  - #1F883D: dependencies
  - #689F38: priority-low

🔴 Rojos (CRUD, Critical)
  - #D32F2F: security, crud
  - #F44336: create
  - #EF5350: read
  - #E57373: update
  - #FFCDD2: delete
  - #F57F17: priority-medium

🟡 Naranjas (Datos)
  - #FFB300: architecture
  - #FFC107: data-model
  - #FF9800: repository
  - #FF6F00: database, local-data
  - #FFE0B2: phase-3

🟣 Púrpura (Configuración, Abstracto)
  - #7B1FA2: cloud
  - #9C27B0: configuration
  - #BA68C8: android-manifest
  - #CE93D8: utility
  - #4527A0: verification
  - #5E35B1: interface

🔷 Cyan/Teal (UI)
  - #00BCD4: ui
  - #00ACC1: compose
  - #009688: download
  - #B3E5FC: phase-5

🔶 Otros
  - #FF6F00: cloud
  - #DCEDC1: phase-2
  - #BFD4F5: phase-1
  - #FFCCBC: phase-6
```

---

## 📋 Checklist de Creación

- [ ] Crear etiqueta `mongodb`
- [ ] Crear etiqueta `database`
- [ ] Crear etiqueta `cloud`
- [ ] Crear etiqueta `gradle`
- [ ] Crear etiqueta `build`
- [ ] Crear etiqueta `dependencies`
- [ ] Crear etiqueta `architecture`
- [ ] Crear etiqueta `data-model`
- [ ] Crear etiqueta `repository`
- [ ] Crear etiqueta `local-data`
- [ ] Crear etiqueta `crud`
- [ ] Crear etiqueta `create`
- [ ] Crear etiqueta `security`
- [ ] Crear etiqueta `network`
- [ ] Crear etiqueta `connectivity`
- [ ] Crear etiqueta `permissions`
- [ ] Crear etiqueta `ui`
- [ ] Crear etiqueta `compose`
- [ ] Crear etiqueta `download`
- [ ] Crear etiqueta `configuration`
- [ ] Crear etiqueta `android-manifest`
- [ ] Crear etiqueta `utility`
- [ ] Crear etiqueta `feature`
- [ ] Crear etiqueta `phase-1`
- [ ] Crear etiqueta `phase-2`
- [ ] Crear etiqueta `phase-3`
- [ ] Crear etiqueta `phase-5`
- [ ] Crear etiqueta `phase-6`
- [ ] Crear etiqueta `priority-high`
- [ ] Crear etiqueta `priority-medium`
- [ ] Crear etiqueta `priority-low`
- [ ] Crear etiqueta `verification`
- [ ] Crear etiqueta `interface`

---

## 🚀 Orden de Creación Recomendado

### Paso 1: Etiquetas Esenciales (8)
1. `feature`
2. `mongodb`
3. `gradle`
4. `architecture`
5. `security`
6. `ui`
7. `configuration`
8. `crud`

### Paso 2: Etiquetas de Categoría (11)
9. `database`
10. `cloud`
11. `build`
12. `dependencies`
13. `data-model`
14. `repository`
15. `local-data`
16. `network`
17. `connectivity`
18. `permissions`
19. `compose`

### Paso 3: Etiquetas de Detalle (9)
20. `download`
21. `android-manifest`
22. `utility`
23. `verification`
24. `interface`
25. `create`
26. `read`
27. `update`
28. `delete`

### Paso 4: Etiquetas de Prioridad y Fase (9)
29. `priority-high`
30. `priority-medium`
31. `priority-low`
32. `phase-1`
33. `phase-2`
34. `phase-3`
35. `phase-5`
36. `phase-6`

---

## 💡 Consejos

- **Cohesión:** Usa colores similares para categorías relacionadas
- **Claridad:** Los nombres de etiquetas deben ser descriptivos pero cortos
- **Consistencia:** Usa prefijos (priority-, phase-) para agrupar etiquetas relacionadas
- **Limitación:** No crees más de 50 etiquetas (GitHub recomienda 10-50)

---

**Generado:** 2026-01-16  
**Versión:** 1.0  
**Total de etiquetas:** 37

*Nota: Puedes crear menos etiquetas si lo prefieres. Las marcadas con "Futuro" son opcionales para ahora.*

