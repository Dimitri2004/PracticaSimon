# 📋 Guía: Creación de Issues para MongoDB Integration

**Fecha:** 20 de Enero de 2026  
**Propósito:** Explicar cómo y por qué se crean los issues del proyecto  
**Status:** ✅ Completado

---

## 🎯 Introducción

Este documento explica la **estrategia de creación de issues** para la integración de MongoDB en PracticaSimon2. Los issues son la **unidad de trabajo mínima** que garantiza trazabilidad, organización y colaboración efectiva.

---

## ¿POR QUÉ CREAMOS ISSUES?

### 1. **Trazabilidad Completa**
Los issues crean un registro auditable de:
- ✅ Qué trabajo se necesita hacer
- ✅ Quién lo está haciendo
- ✅ Cuándo se completó
- ✅ Qué cambios se hicieron

**Beneficio:** Cualquiera puede ver el historial completo del proyecto

### 2. **Organización y Planificación**
Con issues puedes:
- 📊 Agrupar trabajo por categorías (feat, bug, refactor, etc.)
- 🏷️ Asignar labels para filtrar rápidamente
- 👤 Asignar responsables
- 📅 Crear hitos (milestones)
- ⚡ Priorizar trabajo

**Beneficio:** El trabajo está organizado y no se pierde nada

### 3. **Colaboración de Equipo**
Los issues facilitan:
- 💬 Discusiones antes de implementar
- 👀 Revisión de código mediante PRs
- 🔗 Vinculación con commits y PRs
- 📝 Documentación del razonamiento

**Beneficio:** Todo el equipo entiende por qué se hace cada cambio

### 4. **Automatización y Flujo**
GitHub puede:
- 🔄 Cerrar issues automáticamente con PRs
- 📋 Mover issues entre estados
- 🤖 Ejecutar acciones automáticas
- 📊 Generar reportes de progreso

**Beneficio:** Menos trabajo manual, más eficiencia

---

## 📐 ESTRUCTURA DE ISSUES PARA MONGODB

### Categorías de Issues

| Categoría | Prefijo | Color | Ejemplo |
|-----------|---------|-------|---------|
| **Feature** | `feat:` | 🟢 Verde | `feat: Conectar app con MongoDB Atlas` |
| **Bug** | `bug:` | 🔴 Rojo | `bug: Error al sincronizar datos` |
| **Refactor** | `refactor:` | 🟡 Amarillo | `refactor: Limpiar código de API` |
| **Chore** | `chore:` | ⚫ Negro | `chore: Actualizar dependencias` |
| **Documentation** | `docs:` | 🔵 Azul | `docs: Documentar API REST` |
| **Testing** | `test:` | 🟣 Púrpura | `test: Implementar tests unitarios` |

---

## ✅ PLANTILLA DE ISSUE PARA MONGODB

### Encabezado
```
[CATEGORÍA] Descripción breve y clara
```

**Ejemplos correctos:**
- ✅ `[feat] Crear endpoint POST para guardar partidas en MongoDB`
- ✅ `[bug] La sincronización de datos falla sin conexión a internet`
- ✅ `[docs] Documentar estructura de colecciones MongoDB`

**Ejemplos incorrectos:**
- ❌ `MongoDB stuff` (muy vago)
- ❌ `Nueva feature` (no especifica qué)
- ❌ `ERROR!!` (no es profesional)

---

## 📝 DESCRIPCIÓN DEL ISSUE (Plantilla Completa)

### Sección: DESCRIPCIÓN

```markdown
## 📝 Descripción
Explicación clara de qué necesita hacerse y por qué es importante.

Incluye:
- Contexto del problema
- Impacto en el proyecto
- Requisitos específicos
```

**Ejemplo:**
```markdown
## 📝 Descripción
Se necesita crear un repositorio de datos remoto para comunicarse con MongoDB Atlas.
Actualmente la app solo usa Room (SQLite local).

Esto permitirá:
- Sincronizar datos entre dispositivos
- Eliminar dependencia de BD local
- Escalar la app a múltiples usuarios
```

---

### Sección: TAREAS (Checklist)

```markdown
## ✅ Tareas
- [ ] Crear interfaz IRecordRepository
- [ ] Implementar RecordRemoteRepository
- [ ] Agregar dependencias de Retrofit
- [ ] Crear modelos de datos remoto
- [ ] Configurar autenticación con MongoDB
- [ ] Implementar métodos CRUD
- [ ] Hacer testing
- [ ] Documentar cambios
```

**Por qué:** Permite desglosar el trabajo grande en tareas pequeñas y trackear el progreso

---

### Sección: CRITERIOS DE ACEPTACIÓN

```markdown
## ✔️ Criterios de Aceptación
- [ ] El código compila sin errores
- [ ] Los tests unitarios pasan (100%)
- [ ] Se puede conectar a MongoDB desde la app
- [ ] Los datos se sincronizen correctamente
- [ ] La documentación está actualizada
- [ ] El código sigue las convenciones del proyecto
```

**Por qué:** Define cuándo el issue está realmente **"hecho"**

---

### Sección: CONTEXTO TÉCNICO

```markdown
## 🔧 Detalles Técnicos
- **Archivo(s) a crear:** RecordRemoteRepository.kt, models/RemoteRecord.kt
- **Archivo(s) a modificar:** build.gradle.kts, AndroidManifest.xml
- **Dependencias:** Retrofit 2.x, OkHttp 4.x
- **Rama recomendada:** `feature/mongodb-repository`
```

**Por qué:** Los desarrolladores saben exactamente qué toca modificar

---

### Sección: REFERENCIAS

```markdown
## 📚 Referencias
- Plan relacionado: PLAN_DESARROLLO_MONGODB.md (Fase 2)
- Documentación: https://docs.mongodb.com/realm/sdk/kotlin/
- Issue relacionado: #15, #20
- PR anterior: #18
```

**Por qué:** Conecta el trabajo con el contexto más amplio

---

## 📊 EJEMPLO COMPLETO DE ISSUE

```markdown
# [feat] Implementar repositorio remoto con MongoDB

## 📝 Descripción
Se necesita crear la capa de datos remota que comunique la app con MongoDB Atlas.
Esto reemplazará las queries de Room para datos que deben sincronizarse entre dispositivos.

**Impacto:**
- Permite múltiples usuarios con sincronización
- Elimina la necesidad de SQLite local
- Base para análisis de datos en el servidor

## ✅ Tareas
- [ ] Crear interfaz `IRecordRepository` con métodos CRUD
- [ ] Implementar `RecordRemoteRepository` con Retrofit
- [ ] Agregar modelos de datos: `RemoteRecord`, `RemoteGame`
- [ ] Configurar autenticación JWT
- [ ] Crear tests unitarios
- [ ] Documentar en README

## ✔️ Criterios de Aceptación
- [ ] Compila sin errores
- [ ] Los 5 tests unitarios pasan
- [ ] Se puede hacer login y obtener datos
- [ ] La sincronización es < 2 segundos
- [ ] Documentación con ejemplos
- [ ] Code review aprobado

## 🔧 Detalles Técnicos
**Archivos:**
- `app/src/main/kotlin/com/example/practicasimon/data/remote/RecordRemoteRepository.kt`
- `app/src/main/kotlin/com/example/practicasimon/data/models/RemoteRecord.kt`

**Dependencias a agregar:**
```gradle
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.10.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

**Rama:** `feature/mongodb-remote-repo`

## 📚 Referencias
- Plan: PLAN_DESARROLLO_MONGODB.md
- Fase: 2 (Desarrollo Backend)
- Estima: 4-6 horas
- PR relacionado: Será linkedado cuando se cree

## 💬 Notas
Este issue es prerequisito para [#25: Integración en UI]
```

---

## 🏷️ LABELS (ETIQUETAS)

Los labels ayudan a categorizar y filtrar issues:

| Label | Color | Uso |
|-------|-------|-----|
| `enhancement` | 🟢 Verde | Nuevas features |
| `bug` | 🔴 Rojo | Bugs a arreglar |
| `documentation` | 🔵 Azul | Docs a escribir |
| `good first issue` | 🟡 Amarillo | Para nuevos contribuidores |
| `help wanted` | 🟠 Naranja | Se necesita ayuda |
| `in progress` | 🟣 Púrpura | Alguien está trabajando |
| `blocked` | ⚫ Negro | Espera algo para continuar |
| `mongodb` | 🌿 Verde oscuro | Relacionado con MongoDB |
| `high priority` | 🔴 Rojo oscuro | Urgente |

---

## 👤 ASIGNACIÓN DE ISSUES

### Cuándo asignar un issue a alguien:
- ✅ Cuando hay un voluntario claro
- ✅ Cuando el issue es pequeño y específico
- ✅ Cuando es parte de sprint planning

### Cuándo NO asignar:
- ❌ Cuando es un issue grande sin desglose
- ❌ Cuando espera validación de requisitos
- ❌ Cuando está bloqueado por otro issue

---

## 🔄 CICLO DE VIDA DE UN ISSUE

### Estado 1: CREADO (Open)
```
┌─────────────────────────┐
│ Issue creado            │
│ Sin asignar             │
│ Esperando revisión      │
└──────────────┬──────────┘
               │
```

### Estado 2: EN PROGRESO (In Progress)
```
┌─────────────────────────┐
│ Asignado a desarrollador│
│ Branch creada           │
│ Trabajo en progreso     │
└──────────────┬──────────┘
               │
```

### Estado 3: EN REVISIÓN (Pull Request)
```
┌─────────────────────────┐
│ PR creada y linked      │
│ Code review en proceso  │
│ Cambios solicitados     │
└──────────────┬──────────┘
               │
```

### Estado 4: COMPLETADO (Closed)
```
┌─────────────────────────┐
│ PR merged               │
│ Issue cerrado           │
│ Trabajo completado      │
└─────────────────────────┘
```

---

## 🔗 VINCULAR ISSUES Y PULL REQUESTS

### En el mensaje de commit:
```bash
git commit -m "feat: Implement MongoDB repository

- Create RecordRemoteRepository class
- Add Retrofit interceptors
- Add authentication headers

Closes #23"
```

**Efecto:** GitHub vinculará automáticamente el commit con el issue #23

### En la descripción de PR:
```markdown
# Implementar repositorio remoto MongoDB

Cierra #23, relacionado con #15 y #20

## Cambios
- Nuevo archivo: RecordRemoteRepository.kt
- Modelos: RemoteRecord.kt, RemoteGame.kt
- Tests: RecordRemoteRepositoryTest.kt

## Pruebas
- ✅ Tests unitarios: 5/5 pasan
- ✅ Integración: Conexión exitosa a MongoDB
```

**Beneficio:** Cuando el PR se merge, GitHub cierra automáticamente el issue

---

## 📈 MEJORES PRÁCTICAS

### ✅ HAZLO
- 📝 Usa títulos claros y descriptivos
- 🎯 Uno issue = un objetivo claro
- 📋 Incluye checklist de tareas
- 🏷️ Usa labels relevantes
- 🔗 Vincula issues relacionados
- 📚 Referencia documentación
- 💡 Explica el "por qué"

### ❌ NO HAGAS
- ❌ Issues vagos: "Arreglar cosas"
- ❌ Mega-issues con 50 tareas
- ❌ Olvidar criterios de aceptación
- ❌ Crear issues sin descripción
- ❌ Ignorar issues sin revisar

---

## 🚀 EJEMPLO: CREACIÓN RÁPIDA

### Paso 1: Ir a GitHub
Abrir: https://github.com/Dimitri2004/PracticaSimon/issues/new

### Paso 2: Llenar la plantilla
```
Título: [feat] Crear endpoint POST para guardar puntuaciones

Descripción:
## 📝 Descripción
Se necesita un endpoint en MongoDB para guardar las puntuaciones de los usuarios.

## ✅ Tareas
- [ ] Crear modelo ScoreDB
- [ ] Implementar método save()
- [ ] Hacer tests

## ✔️ Criterios
- [ ] Compila sin errores
- [ ] Tests pasan
- [ ] Documentado
```

### Paso 3: Agregar labels
- `enhancement`
- `mongodb`
- `high priority`

### Paso 4: Crear issue
Click en "Create issue"

### Paso 5: Verlo en el proyecto
El issue aparece en https://github.com/Dimitri2004/PracticaSimon/issues

---

## 📊 MÉTRICAS DE ISSUES

Con issues puedes analizar:

| Métrica | Para qué sirve |
|---------|---|
| **Issues por sprint** | Velocidad del equipo |
| **Tiempo promedio de cierre** | Eficiencia |
| **Issues bloqueadas** | Cuellos de botella |
| **Distribution por etiqueta** | Dónde va el esfuerzo |

---

## 🎓 CONCLUSIÓN

Los **issues son tu aliado** para:
- 📍 Mantener el proyecto organizado
- 🎯 No perder trabajo en progreso
- 💬 Comunicar cambios al equipo
- 📊 Medir progreso
- 🔍 Revisar decisiones futuras

**Crea issues abundantemente.** No duele, solo ayuda.

---

**Última actualización:** 2026-01-20  
**Versión:** 1.0

