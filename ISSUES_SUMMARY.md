# Issues del Plan MongoDB Integration - Resumen Visual

## 📊 Resumen General
- **Total de Issues:** 15
- **Fases cubiertas:** 6 de 8
- **Estado actual:** Preparado para crear en GitHub
- **Documento base:** `plan/feature-mongodb-integration-1.0.md`

---

## 🔧 FASE 1: Preparación e Configuración Inicial (8 issues)

### ✅ TASK-001
**Crear conta en MongoDB Atlas**
- Priority: 🔴 **ALTA**
- Labels: `mongodb` `database` `cloud` `feature`
- Descripción: Crear cuenta inicial en MongoDB Atlas

### ✅ TASK-002
**Crear un cluster gratuito en MongoDB Atlas**
- Priority: 🔴 **ALTA**
- Labels: `mongodb` `database` `feature`
- Descripción: Crear cluster M0 (gratuito) en MongoDB Atlas

### ✅ TASK-003
**Configurar reglas de acceso en MongoDB Atlas**
- Priority: 🔴 **ALTA**
- Labels: `mongodb` `security` `feature`
- Descripción: Configurar Network Access para permitir conexiones

### ✅ TASK-004
**Crear usuario de base de datos con permisos mínimos**
- Priority: 🔴 **ALTA**
- Labels: `mongodb` `security` `feature`
- Descripción: Crear usuario de BD con permisos readWrite

### ✅ TASK-005
**Obtener connection string de MongoDB Atlas**
- Priority: 🔴 **ALTA**
- Labels: `mongodb` `configuration` `feature`
- Descripción: Obtener y guardar connection string en secrets.properties

### ✅ TASK-006
**Agregar MongoDB Realm SDK en gradle/libs.versions.toml**
- Priority: 🟠 **MEDIA**
- Labels: `gradle` `dependencies` `feature`
- Descripción: Agregar versión de Realm SDK (1.8.0)

### ✅ TASK-007
**Actualizar app/build.gradle.kts con dependencias de Realm/MongoDB**
- Priority: 🟠 **MEDIA**
- Labels: `gradle` `build` `feature`
- Descripción: Agregar plugin de Realm y dependencias

### ✅ TASK-008
**Sincronizar Gradle y verificar compilación**
- Priority: 🟠 **MEDIA**
- Labels: `gradle` `build` `verification`
- Descripción: Ejecutar ./gradlew build y verificar que compila

---

## 🏗️ FASE 2: Creación da Arquitectura de Datos (3 issues)

### ✅ TASK-009
**Crear clase RecordRemote.kt para representación en MongoDB**
- Priority: 🟠 **MEDIA**
- Labels: `data-model` `mongodb` `architecture`
- Descripción: Data class para representar récords en MongoDB
- Archivo: `app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`

### ✅ TASK-010
**Crear interface IRecordRepository.kt con contrato CRUD**
- Priority: 🟠 **MEDIA**
- Labels: `repository` `architecture` `interface`
- Descripción: Interface que define operaciones CRUD
- Archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`

### ✅ TASK-011
**Crear RecordLocalRepository.kt para acceso a datos locales (Room)**
- Priority: 🟠 **MEDIA**
- Labels: `repository` `room` `local-data`
- Descripción: Implementación de repositorio para Room
- Archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordLocalRepository.kt`

---

## 🔐 FASE 3: Implementación de Operacións CRUD (2 issues)

### ✅ TASK-015
**Implementar operación create() en RecordRemoteRepository**
- Priority: 🔴 **ALTA**
- Labels: `crud` `mongodb` `create`
- Descripción: Crear RecordRemoteRepository e implementar CREATE
- Archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`

### ✅ TASK-020
**Crear NetworkUtil.kt para verificar conexión a internet**
- Priority: 🟠 **MEDIA**
- Labels: `network` `utility` `connectivity`
- Descripción: Utilidad para detectar estado de conexión de red
- Archivo: `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`

---

## 🎨 FASE 5: Integración coa UI (1 issue)

### ✅ TASK-033
**Agregar botón "Descargar de MongoDB" en Interfaz.kt**
- Priority: 🟠 **MEDIA**
- Labels: `ui` `compose` `download`
- Descripción: Agregar botón para descargar datos desde MongoDB
- Archivo: `app/src/main/java/gz/dam/trabajosimondize/main/Interfaz.kt`

---

## 🔒 FASE 6: Seguridade e Configuración (1 issue)

### ✅ TASK-041
**Configurar permisos de Android necesarios**
- Priority: 🟠 **MEDIA**
- Labels: `android-manifest` `permissions` `configuration`
- Descripción: Agregar permisos INTERNET y ACCESS_NETWORK_STATE
- Archivo: `app/src/main/AndroidManifest.xml`

---

## 📋 Tabla de Seguimiento

| Nº | TASK | Título | Estado | Fase | Priority |
|---|------|--------|--------|------|----------|
| 1 | TASK-001 | Crear conta en MongoDB Atlas | Planned | 1 | 🔴 Alta |
| 2 | TASK-002 | Crear cluster gratuito | Planned | 1 | 🔴 Alta |
| 3 | TASK-003 | Configurar Network Access | Planned | 1 | 🔴 Alta |
| 4 | TASK-004 | Crear usuario de BD | Planned | 1 | 🔴 Alta |
| 5 | TASK-005 | Obtener connection string | Planned | 1 | 🔴 Alta |
| 6 | TASK-006 | Agregar SDK en libs.versions.toml | Planned | 1 | 🟠 Media |
| 7 | TASK-007 | Actualizar build.gradle.kts | Planned | 1 | 🟠 Media |
| 8 | TASK-008 | Sincronizar Gradle | Planned | 1 | 🟠 Media |
| 9 | TASK-009 | Crear RecordRemote.kt | Planned | 2 | 🟠 Media |
| 10 | TASK-010 | Crear IRecordRepository.kt | Planned | 2 | 🟠 Media |
| 11 | TASK-011 | Crear RecordLocalRepository.kt | Planned | 2 | 🟠 Media |
| 12 | TASK-015 | Implementar create() | Planned | 3 | 🔴 Alta |
| 13 | TASK-020 | Crear NetworkUtil.kt | Planned | 3 | 🟠 Media |
| 14 | TASK-033 | Agregar botón descargar | Planned | 5 | 🟠 Media |
| 15 | TASK-041 | Configurar permisos | Planned | 6 | 🟠 Media |

---

## 📂 Estructura de Archivos a Crear

```
app/src/main/java/gz/dam/trabajosimondize/
├── data/
│   ├── model/
│   │   └── RecordRemote.kt (NUEVO - TASK-009)
│   ├── repository/
│   │   ├── IRecordRepository.kt (NUEVO - TASK-010)
│   │   ├── RecordLocalRepository.kt (NUEVO - TASK-011)
│   │   └── RecordRemoteRepository.kt (NUEVO - TASK-015)
│   └── sync/
│       └── (Futuro para sincronización)
├── util/
│   └── NetworkUtil.kt (NUEVO - TASK-020)
└── main/
    ├── Interfaz.kt (MODIFICAR - TASK-033)
    └── MyViewModel.kt (MODIFICAR - TASK-005)

app/src/main/
└── AndroidManifest.xml (MODIFICAR - TASK-041)

gradle/
└── libs.versions.toml (MODIFICAR - TASK-006)

app/
└── build.gradle.kts (MODIFICAR - TASK-007)
```

---

## 🚀 Orden Recomendado de Ejecución

### Primera Etapa (Configuración Base)
1. TASK-001 → Crear cuenta MongoDB
2. TASK-002 → Crear cluster
3. TASK-003 → Configurar Network Access
4. TASK-004 → Crear usuario
5. TASK-005 → Connection string

### Segunda Etapa (Dependencias)
6. TASK-006 → libs.versions.toml
7. TASK-007 → build.gradle.kts
8. TASK-008 → Sincronizar Gradle

### Tercera Etapa (Código Base)
9. TASK-009 → RecordRemote.kt
10. TASK-010 → IRecordRepository.kt
11. TASK-011 → RecordLocalRepository.kt

### Cuarta Etapa (Implementación)
12. TASK-015 → RecordRemoteRepository.kt
13. TASK-020 → NetworkUtil.kt

### Quinta Etapa (UI & Permisos)
14. TASK-041 → Permisos AndroidManifest
15. TASK-033 → Botón descargar

---

## 📝 Notas Importantes

- **Seguridad:** Las TASK de FASE 1 (TASK-001 a TASK-005) requieren credenciales reales de MongoDB Atlas
- **Orden crítico:** Las TASK de FASE 1 deben completarse ANTES de las demás
- **Dependencias:** TASK-006 y TASK-007 deben ejecutarse antes de TASK-009, TASK-010, TASK-011
- **Testing:** Cada TASK debe tener tests unitarios asociados (no incluidos en esta plantilla)

---

## 🔗 Referencias

- Plan completo: `plan/feature-mongodb-integration-1.0.md`
- Plantilla detallada: `GITHUB_ISSUES_TEMPLATE.md`
- Documentación MongoDB: https://www.mongodb.com/docs/
- Realm Kotlin SDK: https://www.mongodb.com/docs/realm/sdk/kotlin/

---

**Generado:** 2026-01-16  
**Version:** 1.0  
**Estado:** ✅ Listo para crear en GitHub

