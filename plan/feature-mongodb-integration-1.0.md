---
goal: Integración de MongoDB sen ser local no proxecto de PracticaSimon
version: 1.0
date_created: 2026-01-14
last_updated: 2026-01-14
owner: Dimitri2004
status: 'Planned'
tags: ['feature', 'database', 'mongodb', 'cloud', 'integration']
---

# Introducción

![Status: Planned](https://img.shields.io/badge/status-Planned-blue)

Este plan describe a integración de **MongoDB (servizo en nube)** no proxecto de PracticaSimon, reemplazando ou complementando a base de datos local SQLite/Room con unha solución na nube. O obxectivo é permitir almacenar, editar e descargar datos de récords desde un servidor MongoDB remoto, manténdose a capacidade de acceso local cando non haxa conexión a internet.

---

## 1. Requisitos & Restriccións

### Requisitos Funcionais

- **REQ-001**: Capacidade de conectar coa base de datos MongoDB en nube (MongoDB Atlas ou equivalente)
- **REQ-002**: Sincronización de datos de récords entre a base de datos local (Room) e MongoDB
- **REQ-003**: Crear novos récords e gardaos en MongoDB
- **REQ-004**: Ler récords almacenados en MongoDB
- **REQ-005**: Actualizar récords existentes en MongoDB
- **REQ-006**: Eliminar récords de MongoDB
- **REQ-007**: Descargar datos de MongoDB ó dispositivo local
- **REQ-008**: Funcionalidade de backup e sincronización automática

### Requisitos Non Funcionais

- **RNF-001**: Manter compatibilidade con Android 30+ (minSdk actual)
- **RNF-002**: Non aumentar significativamente o tamaño da aplicación
- **RNF-003**: Implementar manexo de erros de conexión á rede
- **RNF-004**: Requiere Kotlin como linguaxe principal (xa existente no proxecto)

### Restriccións

- **CON-001**: Precisarase dunha conta de MongoDB Atlas ou servizo equivalente en nube
- **CON-002**: Requiere conexión a internet para operacións en nube
- **CON-003**: A aplicación debe ser usable en modo offline con datos locais

### Seguridade

- **SEC-001**: Gardación segura de credenciais de MongoDB (sen exponerlas no código)
- **SEC-002**: Validación de datos antes de enviar a MongoDB
- **SEC-003**: Encriptación de datos sensibles en tránsito (HTTPS/TLS)

### Directrices de Deseño

- **GUD-001**: Mantener a arquitectura MVVM actual do proxecto
- **GUD-002**: Utilizar Coroutines para operacións asincrónicas
- **GUD-003**: Implementar patrón Repository para abstraer a fonte de datos
- **GUD-004**: Separación clara entre capa de datos local e remota

---

## 2. Pasos de Implementación

### Fase 1: Preparación e Configuración Inicial

**GOAL-001**: Configurar MongoDB Atlas e as dependencias necesarias no proxecto

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-001 | Crear conta en MongoDB Atlas (https://www.mongodb.com/cloud/atlas) | | |
| TASK-002 | Crear un cluster gratuíto en MongoDB Atlas | | |
| TASK-003 | Configurar reglas de acceso (Network Access) en MongoDB Atlas | | |
| TASK-004 | Crear usuario de base de datos con permisos mínimos necesarios | | |
| TASK-005 | Obter connection string de MongoDB Atlas | | |
| TASK-006 | Engadir versión de MongoDB Realm SDK en `gradle/libs.versions.toml` | | |
| TASK-007 | Actualizar `app/build.gradle.kts` coas dependencias de Realm/MongoDB | | |
| TASK-008 | Sincronizar Gradle e verificar que non hai erros de compilación | | |

**Detalles de TASK-006 (gradle/libs.versions.toml):**
```
[versions]
mongodb-realm = "1.8.0"  # Ou a última versión dispoñible

[libraries]
mongodb-realm = { group = "io.realm.kotlin", name = "library-sync", version.ref = "mongodb-realm" }
```

**Detalles de TASK-007 (app/build.gradle.kts):**
- Engadir plugin: `id("io.realm.kotlin") version "1.8.0"`
- Engadir dependencia: `implementation(libs.mongodb-realm)`

---

### Fase 2: Creación da Arquitectura de Datos

**GOAL-002**: Deseñar e implementar as capas de datos (local e remota)

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-009 | Crear clase `RecordRemote.kt` para representación de datos en MongoDB | | |
| TASK-010 | Crear interface `IRecordRepository.kt` co contrato de operacións CRUD | | |
| TASK-011 | Crear clase `RecordLocalRepository.kt` para acceso a datos locais (Room) | | |
| TASK-012 | Crear clase `RecordRemoteRepository.kt` para acceso a datos en MongoDB | | |
| TASK-013 | Crear clase `RecordSyncRepository.kt` que combina datos locais e remotos | | |
| TASK-014 | Implementar `SyncManager.kt` para xestionar sincronización | | |

**Estructura de directorios a crear:**
```
app/src/main/java/gz/dam/trabajosimondize/data/
├── model/
│   ├── RecordRemote.kt (novo)
│   └── ... (existentes)
├── repository/
│   ├── IRecordRepository.kt (novo)
│   ├── RecordLocalRepository.kt (novo)
│   ├── RecordRemoteRepository.kt (novo)
│   └── RecordSyncRepository.kt (novo)
├── sync/
│   └── SyncManager.kt (novo)
└── ... (existentes)
```

---

### Fase 3: Implementación de Operacións CRUD

**GOAL-003**: Implementar as operacións de creación, lectura, actualización e eliminación en MongoDB

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-015 | Implementar `create()` en RecordRemoteRepository | | |
| TASK-016 | Implementar `read()` en RecordRemoteRepository | | |
| TASK-017 | Implementar `update()` en RecordRemoteRepository | | |
| TASK-018 | Implementar `delete()` en RecordRemoteRepository | | |
| TASK-019 | Implementar manexo de excepcións de rede en cada método | | |
| TASK-020 | Crear `NetworkUtil.kt` para verificar conexión a internet | | |
| TASK-021 | Implementar fallback a datos locais cando non hai conexión | | |

**Métodos base a implementar:**
```kotlin
// Interface IRecordRepository.kt
interface IRecordRepository {
    suspend fun createRecord(record: RecordRemote): Result<String>
    suspend fun readRecord(id: String): Result<RecordRemote>
    suspend fun readAllRecords(): Result<List<RecordRemote>>
    suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit>
    suspend fun deleteRecord(id: String): Result<Unit>
}
```

---

### Fase 4: Sincronización de Datos

**GOAL-004**: Establecer mecanismo de sincronización bidirecional entre datos locais e remotos

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-022 | Implementar sincronización unidirecional: local → remoto | | |
| TASK-023 | Implementar sincronización unidirecional: remoto → local | | |
| TASK-024 | Crear sistema de timestamps para controlar versións | | |
| TASK-025 | Implementar resolución de conflitos (última modificación gana) | | |
| TASK-026 | Executar sincronización automática cada 5 minutos (WorkManager) | | |
| TASK-027 | Engadir sincronización manual (botón na UI) | | |
| TASK-028 | Implementar notificacións de estado de sincronización | | |

**Dependencias necesarias (TASK-026):**
- `androidx.work:work-runtime-ktx:2.8.1` para WorkManager

---

### Fase 5: Integración coa UI

**GOAL-005**: Integrar as operacións de MongoDB na interface de usuario existente

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-029 | Inyectar RecordSyncRepository en MyViewModel.kt | | |
| TASK-030 | Crear corrutina en ViewModel para cargar récords desde MongoDB | | |
| TASK-031 | Implementar función para gardar novos récords (local + remoto) | | |
| TASK-032 | Crear pantalla de sincronización co estado (loading, success, error) | | |
| TASK-033 | Engadir botón "Descargar de MongoDB" en Interfaz.kt | | |
| TASK-034 | Engadir botón "Subir a MongoDB" en Interfaz.kt | | |
| TASK-035 | Mostrar erros de conexión de forma amigable ó usuario | | |
| TASK-036 | Implementar indicador visual de datos sincronizados vs. locais | | |

---

### Fase 6: Seguridade e Configuración

**GOAL-006**: Asegurar as credenciais e implementar configuración segura

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-037 | Crear arquivo `secrets.properties` (ignorado en git) | | |
| TASK-038 | Almacenar connection string de MongoDB en `secrets.properties` | | |
| TASK-039 | Cargar credenciais en tempo de compilación sen exponerlas | | |
| TASK-040 | Implementar validación de datos antes de enviar a MongoDB | | |
| TASK-041 | Configurar permisos de Android necesarios (INTERNET) | | |
| TASK-042 | Implementar timeout para conexións a MongoDB | | |

**Permisos necesarios en AndroidManifest.xml:**
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

### Fase 7: Probas e Validación

**GOAL-007**: Probar toda a funcionalidade de integración con MongoDB

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-043 | Crear tests unitarios para RecordRemoteRepository | | |
| TASK-044 | Crear tests para sincronización de datos | | |
| TASK-045 | Probar creación de récords en MongoDB desde a app | | |
| TASK-046 | Probar lectura de récords desde MongoDB | | |
| TASK-047 | Probar actualización de récords en MongoDB | | |
| TASK-048 | Probar eliminación de récords de MongoDB | | |
| TASK-049 | Probar descarga de datos con conexión limitada | | |
| TASK-050 | Probar modo offline (sen conexión a internet) | | |
| TASK-051 | Validar que Room segue funcionando coma capa local | | |

---

### Fase 8: Documentación e Despliegue

**GOAL-008**: Documentar a implementación e preparar para produción

| Tarea | Descrición | Completada | Data |
|------|-----------|-----------|------|
| TASK-052 | Documentar processo de configuración de MongoDB Atlas | | |
| TASK-053 | Crear guía de uso para desenvolvedores | | |
| TASK-054 | Documentar estructura de datos en MongoDB | | |
| TASK-055 | Crear README con pasos para reproducir a integración | | |
| TASK-056 | Revisar e optimizar consumo de datos | | |
| TASK-057 | Preparar versión para release | | |

---

## 3. Alternativas Consideradas

- **ALT-001**: Usar **Firebase Realtime Database** - Descartada porque MongoDB proporciona máis control e flexibilidade
- **ALT-002**: Usar **REST API personalizado** - Máis complexo e requeriría servidor propio; Realm SDK oferece sincronización automática
- **ALT-003**: Reemplazar Room por Room + Retrofit + MongoDB - Mantemos Room para offline-first, Realm para sincronización
- **ALT-004**: Usar **Supabase (PostgreSQL)** - Válido pero MongoDB é mellor para estruturas non normalizadas como récords de xogo

---

## 4. Dependencias Externas

- **DEP-001**: MongoDB Atlas (ou servidor MongoDB remoto) - https://www.mongodb.com/cloud/atlas
- **DEP-002**: MongoDB Realm Kotlin SDK - `io.realm.kotlin:library-sync:1.8.0+`
- **DEP-003**: AndroidX WorkManager - `androidx.work:work-runtime-ktx:2.8.1+`
- **DEP-004**: Kotlin Coroutines - `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9+` (xa existente)
- **DEP-005**: Gson ou Serialization para conversión de datos - Opcional pero recomendado

---

## 5. Arquivos a Afectar/Crear

### Arquivos Novos

- **FILE-001**: `app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`
  - Descrición: Clase data que representa un récord en MongoDB
  
- **FILE-002**: `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`
  - Descrición: Interface que define operacións CRUD

- **FILE-003**: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordLocalRepository.kt`
  - Descrición: Implementación de operacións en SQLite/Room

- **FILE-004**: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`
  - Descrición: Implementación de operacións en MongoDB

- **FILE-005**: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordSyncRepository.kt`
  - Descrición: Combina operacións de ambas fontes de datos

- **FILE-006**: `app/src/main/java/gz/dam/trabajosimondize/data/sync/SyncManager.kt`
  - Descrición: Xestiona a sincronización automática

- **FILE-007**: `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`
  - Descrición: Utilidades para verificar conexión a rede

- **FILE-008**: `app/src/main/java/gz/dam/trabajosimondize/data/sync/SyncWorker.kt`
  - Descrición: Tarefa en segundo plano para sincronización

- **FILE-009**: `secrets.properties` (raíz do proxecto)
  - Descrición: Almacenamento de credenciais (NON incluír en git)

### Arquivos a Modificar

- **FILE-010**: `gradle/libs.versions.toml`
  - Cambio: Engadir versión de MongoDB Realm SDK
  - Liñas: Seción `[versions]` e `[libraries]`

- **FILE-011**: `app/build.gradle.kts`
  - Cambio: Engadir plugin de Realm e dependencias
  - Liñas: Plugin `id("io.realm.kotlin")` e `implementation(libs.mongodb-realm)`

- **FILE-012**: `app/src/main/java/gz/dam/trabajosimondize/main/MyViewModel.kt`
  - Cambio: Inyectar RecordSyncRepository e crear métodos para sincronización
  - Liñas: Inyección de dependencias e novas corrutinas

- **FILE-013**: `app/src/main/java/gz/dam/trabajosimondize/main/Interfaz.kt`
  - Cambio: Engadir botóns para "Descargar" e "Sincronizar" con MongoDB
  - Liñas: Novos composables para interacción

- **FILE-014**: `app/src/main/AndroidManifest.xml`
  - Cambio: Engadir permisos de INTERNET e ACCESS_NETWORK_STATE
  - Liñas: Novos `<uses-permission>`

---

## 6. Tests Necesarios

### Tests Unitarios

- **TEST-001**: `RecordRemoteRepositoryTest.kt`
  - Probar creación de récords en MongoDB
  - Validar conexión e manexo de erros

- **TEST-002**: `RecordSyncRepositoryTest.kt`
  - Probar sincronización correcta entre local e remoto
  - Probar resolución de conflitos

- **TEST-003**: `NetworkUtilTest.kt`
  - Verificar detección correcta de conexión á rede

- **TEST-004**: `SyncManagerTest.kt`
  - Probar disparo automático de sincronización
  - Validar estado de sincronización

### Tests de Integración

- **TEST-005**: Probas end-to-end con MongoDB real
  - Crear, ler, actualizar, eliminar datos
  - Verificar consistencia entre local e remoto

- **TEST-006**: Probas de conexión offline
  - Verificar que a app segue funcionando sen internet
  - Validar sincronización cando voltea a conexión

---

## 7. Riscos & Asuncións

### Riscos

- **RISK-001**: **Perda de datos en sincronización**
  - Mitixación: Implementar sistema de versionado e resolución de conflitos
  
- **RISK-002**: **Custo de MongoDB Atlas**
  - Mitixación: Plan gratuíto cubre casos de uso moderados
  
- **RISK-003**: **Latencia de rede**
  - Mitixación: Implementar caché local e operacións asincrónicas

- **RISK-004**: **Exposición de credenciais**
  - Mitixación: Almacenar en `secrets.properties` e nunca en git

- **RISK-005**: **Compatibilidade con versións antigas de Android**
  - Mitixación: Manter minSdk=30 como xa existe

### Asuncións

- **ASSUMPTION-001**: O usuario terá conexión a internet polo menos ocasionalmente
- **ASSUMPTION-002**: MongoDB Atlas estará accesible durante o ciclo de vida da app
- **ASSUMPTION-002**: Os datos de récords son non-críticos (non requiren cifrado extremo)
- **ASSUMPTION-004**: A sincronización cada 5 minutos é aceptable (non tempo real)

---

## 8. Lecturas Adicionales / Recursos

### Documentación Oficial

- [MongoDB Realm Kotlin SDK Docs](https://www.mongodb.com/docs/realm/sdk/kotlin/)
- [MongoDB Atlas Setup Guide](https://www.mongodb.com/docs/atlas/getting-started/)
- [Android Room Guide](https://developer.android.com/training/data-storage/room) (xa coñecida)
- [Android WorkManager Guide](https://developer.android.com/topic/libraries/architecture/workmanager)

### Guías de Implementación

- [Offline-First Architecture](https://www.mongodb.com/docs/realm/sdk/kotlin/sync/)
- [Kotlin Coroutines Best Practices](https://kotlinlang.org/docs/coroutines-overview.html)
- [Android Security Best Practices](https://developer.android.com/privacy-and-security)

### Exemplo de Proxectos

- [MongoDB Realm Kotlin Examples](https://github.com/mongodb/realm-kotlin)
- [PracticaSimon na mesma repositorio (rama de MongoDB)](https://github.com/Dimitri2004/PracticaSimon)

---

## Cronograma Estimado

| Fase | Duración | Status |
|------|----------|--------|
| Fase 1: Preparación | 1-2 días | Planned |
| Fase 2: Arquitectura | 2-3 días | Planned |
| Fase 3: CRUD | 2-3 días | Planned |
| Fase 4: Sincronización | 3-4 días | Planned |
| Fase 5: UI Integration | 2-3 días | Planned |
| Fase 6: Seguridade | 1-2 días | Planned |
| Fase 7: Probas | 2-3 días | Planned |
| Fase 8: Documentación | 1-2 días | Planned |
| **Total** | **14-22 días** | **Planned** |

---

## Notas Finais

Este plan proporciona unha guía completa para integrar MongoDB sen ser local no proxecto PracticaSimon. A implementación por fases permite ir avanzando de forma controlada e validando cada paso. A capacidade offline-first con Room como capa local asegura que a app segue sendo usable incluso sen conexión a MongoDB.

Para consultas ou cambios, refírese á sección de Riscos & Asuncións ou cree un novo issue no repositorio.

