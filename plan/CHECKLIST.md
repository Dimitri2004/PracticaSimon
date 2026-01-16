# ✅ Lista de Control Interactiva - MongoDB Integration

**Data de Comenzo:** 2026-01-14  
**Obxectivo:** Integración completa de MongoDB en PracticaSimon  
**Estatus:** Planned  

---

## 📅 SEMANA 1: Preparación e Configuración (1-2 días)

### FASE 1: Configuración Inicial

- [ ] **TASK-001** - Crear conta en MongoDB Atlas
  - [ ] Acceder a https://www.mongodb.com/cloud/atlas
  - [ ] Crear novo proxecto
  - [ ] Seleccionar opción Free
  - **Tempo Estimado:** 20 minutos
  - **Links:** [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)

- [ ] **TASK-002** - Crear cluster en MongoDB
  - [ ] Elixir región (próxima ó usuario final)
  - [ ] Elegir tier Free
  - [ ] Esperar a que se cree (5-10 minutos)
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ En proceso

- [ ] **TASK-003** - Configurar Network Access
  - [ ] Ir a "Network Access" → "Add IP Address"
  - [ ] Agregar "0.0.0.0/0" (acceso desde calquera IP)
  - [ ] Confirmar cambios
  - **Tempo Estimado:** 10 minutos
  - **⚠️ Nota:** Só en desenvolvemento; en produción restrinxir

- [ ] **TASK-004** - Crear usuario de base de datos
  - [ ] Ir a "Database Access"
  - [ ] Crear novo usuario
  - [ ] Usar usuario e contraseña segura
  - [ ] Guardar credenciais seguramente
  - **Tempo Estimado:** 10 minutos
  - **⚠️ Importante:** Non olvidar contraseña

- [ ] **TASK-005** - Obtener connection string
  - [ ] Ir a "Databases" → "Connect"
  - [ ] Elixir "Drivers" → "Kotlin"
  - [ ] Copiar connection string
  - [ ] Sustituír `<password>` pola contraseña
  - **Tempo Estimado:** 5 minutos
  - **Exemplo:** `mongodb+srv://usuario:pass@cluster.mongodb.net/db?retryWrites=true&w=majority`

- [ ] **TASK-006** - Engadir MongoDB Realm SDK en libs.versions.toml
  - [ ] Engadir: `mongodb-realm = "1.8.0"`
  - [ ] Engadir: `androidx-work = "2.8.1"`
  - [ ] Sincronizar Gradle
  - **Arquivo:** `gradle/libs.versions.toml`
  - **Tempo Estimado:** 10 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-007** - Actualizar app/build.gradle.kts
  - [ ] Engadir plugin: `alias(libs.plugins.realm)`
  - [ ] Engadir dependencia: `implementation(libs.mongodb-realm)`
  - [ ] Engadir dependencia: `implementation(libs.androidx-work-runtime)`
  - [ ] Engadir BuildConfig para secrets
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-008** - Sincronizar Gradle e validar
  - [ ] Executar: `./gradlew sync`
  - [ ] Verificar que non hai erros
  - [ ] Executar: `./gradlew build`
  - **Comando:** `./gradlew clean build`
  - **Tempo Estimado:** 5-10 minutos
  - **Status:** ⏳ Pendente

---

## 📅 SEMANA 2-3: Capa de Datos (2-3 días)

### FASE 2: Creación da Arquitectura de Datos

- [ ] **TASK-009** - Crear RecordRemote.kt
  - [ ] Crear arquivo: `app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`
  - [ ] Extender de RealmObject
  - [ ] Engadir campos: score, fecha, userId, syncedAt, isSynced, isDeleted
  - [ ] Engadir anotación @PrimaryKey
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Pendente
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 3

- [ ] **TASK-010** - Crear IRecordRepository.kt
  - [ ] Crear interface con métodos CRUD
  - [ ] Métodos: createRecord, readRecord, readAllRecords, updateRecord, deleteRecord, syncRecords
  - [ ] Engadir documentación KDoc
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Pendente
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 4

- [ ] **TASK-011** - Crear RecordLocalRepository.kt
  - [ ] Implementar IRecordRepository para Room
  - [ ] Usar DAO existente de Room
  - [ ] Convertir entre RecordEntity e RecordRemote
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordLocalRepository.kt`
  - **Tempo Estimado:** 25 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-012** - Crear RecordRemoteRepository.kt
  - [ ] Implementar IRecordRepository para MongoDB
  - [ ] Usar Realm para operacións
  - [ ] Engadir manexo de excepcións
  - [ ] Engadir logging
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`
  - **Tempo Estimado:** 35 minutos
  - **Status:** ⏳ Pendente
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 5

- [ ] **TASK-013** - Crear RecordSyncRepository.kt
  - [ ] Combinar lóxica local e remota
  - [ ] Implementar sincronización bidirecional
  - [ ] Manexar offline-first
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordSyncRepository.kt`
  - **Tempo Estimado:** 30 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-014** - Crear SyncManager.kt
  - [ ] Xestionar sincronización automática
  - [ ] Usar WorkManager para tarefas
  - [ ] Disparar a cada 5 minutos (configurable)
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/sync/SyncManager.kt`
  - **Tempo Estimado:** 30 minutos
  - **Status:** ⏳ Pendente

### FASE 3: Operacións CRUD

- [ ] **TASK-015** - Implementar create() en RecordRemoteRepository
  - [ ] Crear novo record en MongoDB
  - [ ] Retornar ID do novo registro
  - [ ] Manexar erros de rede
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-012

- [ ] **TASK-016** - Implementar read() en RecordRemoteRepository
  - [ ] Ler un registro específico por ID
  - [ ] Manexar caso de non encontrado
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-012

- [ ] **TASK-017** - Implementar update() en RecordRemoteRepository
  - [ ] Actualizar registro existente
  - [ ] Actualizar timestamp de sincronización
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-012

- [ ] **TASK-018** - Implementar delete() en RecordRemoteRepository
  - [ ] Eliminar (ou marcar como eliminado) un registro
  - [ ] Manejar soft delete
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-012

- [ ] **TASK-019** - Implementar manexo de excepcións
  - [ ] Capturar excepcións de rede
  - [ ] Capturar excepcións de base de datos
  - [ ] Usar Result<T> para retornar erros
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-012

- [ ] **TASK-020** - Crear NetworkUtil.kt
  - [ ] Función isNetworkAvailable()
  - [ ] Función isWiFiConnected()
  - [ ] Función isMobileConnected()
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Pendente
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 6

- [ ] **TASK-021** - Implementar fallback a datos locais
  - [ ] Se non hai conexión, usar Room
  - [ ] Se hai conexión, usar MongoDB
  - [ ] Sincronizar automáticamente
  - **Tempo Estimado:** 25 minutos
  - **Status:** ⏳ Dependencia de TASK-020

---

## 📅 SEMANA 3-4: Integración UI (2-3 días)

### FASE 5: Integración coa UI

- [ ] **TASK-029** - Inyectar RecordSyncRepository en MyViewModel
  - [ ] Crear instancia de repository
  - [ ] Pasar contexto se é necesario
  - **Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/main/MyViewModel.kt`
  - **Tempo Estimado:** 10 minutos
  - **Status:** ⏳ Dependencia de TASK-013

- [ ] **TASK-030** - Crear corrutina para cargar récords
  - [ ] Función `syncWithMongoDB()`
  - [ ] Usar `viewModelScope.launch`
  - [ ] Actualizar LiveData con resultados
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-029
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 9

- [ ] **TASK-031** - Implementar función para gardar novos récords
  - [ ] Función `uploadCurrentRecord(score: Int)`
  - [ ] Crear RecordRemote a partir do score
  - [ ] Chamar repository.createRecord()
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-029
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 9

- [ ] **TASK-032** - Crear pantalla de sincronización
  - [ ] LiveData para estado: IDLE, SYNCING, SUCCESS, ERROR
  - [ ] Mostrar CircularProgressIndicator mentres se sincroniza
  - [ ] Mostrar mensaxe de éxito ou erro
  - **Tempo Estimado:** 30 minutos
  - **Status:** ⏳ Dependencia de TASK-030

- [ ] **TASK-033** - Engadir botón "Descargar de MongoDB"
  - [ ] Button que chama syncWithMongoDB()
  - [ ] Situado en Interfaz.kt
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-032

- [ ] **TASK-034** - Engadir botón "Subir a MongoDB"
  - [ ] Button que chama uploadCurrentRecord()
  - [ ] Mostrar score actual
  - [ ] Situado en Interfaz.kt
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-032

- [ ] **TASK-035** - Mostrar erros de conexión
  - [ ] Mostrar mensaxes amigables ó usuario
  - [ ] Usar colores e iconos claros
  - [ ] Non mostrar stack traces completos
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-032

- [ ] **TASK-036** - Indicador visual de sincronización
  - [ ] Mostrar se datos son locais ou remotos
  - [ ] Icono ou badxe de estatus
  - [ ] Color verde para sincronizado, gris para pendente
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-032

### FASE 6: Seguridade e Configuración

- [ ] **TASK-037** - Crear secrets.properties
  - [ ] Crear arquivo na raíz: `secrets.properties`
  - [ ] Engadir: `MONGODB_URI=mongodb+srv://...`
  - [ ] Gardalo de forma segura
  - **Arquivo:** `secrets.properties` (Raíz do proxecto)
  - **Tempo Estimado:** 5 minutos
  - **Status:** ⏳ Pendente
  - **⚠️ IMPORTANTE:** Non commitear a git!

- [ ] **TASK-038** - Almacenar credentials seguramente
  - [ ] Cargar desde BuildConfig (non hardcoded)
  - [ ] Usar secrets.properties só en desenvolvemento
  - [ ] En produción, usar variábles de ambiente
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-007

- [ ] **TASK-039** - Cargar credenciais en tempo de compilación
  - [ ] BuildConfig.MONGODB_URI
  - [ ] Verificar que está dispoñible
  - **Arquivo:** `app/build.gradle.kts`
  - **Tempo Estimado:** 10 minutos
  - **Status:** ⏳ Dependencia de TASK-037

- [ ] **TASK-040** - Implementar validación de datos
  - [ ] Validar score (positivo, < límite)
  - [ ] Validar formato de data
  - [ ] Validar antes de enviar a MongoDB
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-041** - Configurar permisos de Android
  - [ ] Engadir `<uses-permission android:name="android.permission.INTERNET" />`
  - [ ] Engadir `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />`
  - **Arquivo:** `app/src/main/AndroidManifest.xml`
  - **Tempo Estimado:** 5 minutos
  - **Status:** ⏳ Pendente
  - **Referencia:** `codigo-ejemplos-mongodb.md` sección 8

- [ ] **TASK-042** - Implementar timeout para conexións
  - [ ] Timeout de 30 segundos para operacións
  - [ ] Retry automático (máximo 3 intentos)
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Pendente

---

## 📅 SEMANA 4-5: Probas (2-3 días)

### FASE 7: Probas e Validación

- [ ] **TASK-043** - Tests unitarios para RecordRemoteRepository
  - [ ] Probar createRecord()
  - [ ] Probar readAllRecords()
  - [ ] Probar updateRecord()
  - [ ] Probar deleteRecord()
  - **Arquivo:** `app/src/test/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepositoryTest.kt`
  - **Tempo Estimado:** 45 minutos
  - **Status:** ⏳ Dependencia de TASK-012

- [ ] **TASK-044** - Tests para sincronización
  - [ ] Probar sincronización local → remoto
  - [ ] Probar sincronización remoto → local
  - [ ] Probar resolución de conflitos
  - **Arquivo:** `app/src/test/java/gz/dam/trabajosimondize/data/repository/RecordSyncRepositoryTest.kt`
  - **Tempo Estimado:** 45 minutos
  - **Status:** ⏳ Dependencia de TASK-013

- [ ] **TASK-045** - Probar creación en MongoDB
  - [ ] Crear un récord desde a app
  - [ ] Verificar en MongoDB Atlas que aparece
  - [ ] Verificar campos correctos
  - **Tipo:** Manual - UI Testing
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-034

- [ ] **TASK-046** - Probar lectura de MongoDB
  - [ ] Insertar dato en MongoDB manualmente
  - [ ] Sincronizar desde a app
  - [ ] Verificar que aparece en pantalla
  - **Tipo:** Manual - UI Testing
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-033

- [ ] **TASK-047** - Probar actualización en MongoDB
  - [ ] Actualizar un récord desde a app
  - [ ] Verificar en MongoDB Atlas
  - **Tipo:** Manual - UI Testing
  - **Tempo Estimado:** 10 minutos
  - **Status:** ⏳ Dependencia de TASK-030

- [ ] **TASK-048** - Probar eliminación
  - [ ] Eliminar un récord
  - [ ] Verificar soft delete en MongoDB
  - **Tipo:** Manual - UI Testing
  - **Tempo Estimado:** 10 minutos
  - **Status:** ⏳ Dependencia de TASK-030

- [ ] **TASK-049** - Probar con conexión limitada
  - [ ] Apagar WiFi, quedar só con datos móviles
  - [ ] Probar sincronización
  - [ ] Verificar que funciona correctamente
  - **Tipo:** Manual - Integración
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Dependencia de TASK-036

- [ ] **TASK-050** - Probar modo offline
  - [ ] Desactivar totalmente a conexión
  - [ ] Intentar sincronizar
  - [ ] Verificar que app segue usable con datos locais
  - [ ] Volver a conectar
  - [ ] Verificar sincronización automática
  - **Tipo:** Manual - Integración
  - **Tempo Estimado:** 30 minutos
  - **Status:** ⏳ Dependencia de TASK-036

- [ ] **TASK-051** - Validar Room segue funcionando
  - [ ] Verificar que datos se gardan localmente
  - [ ] Buscar datos en Room
  - [ ] Confirmar sincronización con MongoDB
  - **Tipo:** Manual - Integración
  - **Tempo Estimado:** 15 minutos
  - **Status:** ⏳ Dependencia de TASK-032

---

## 📅 SEMANA 5: Documentación (1-2 días)

### FASE 8: Documentación e Despliegue

- [ ] **TASK-052** - Documentar configuración MongoDB Atlas
  - [ ] Pasos para crear conta
  - [ ] Pasos para crear cluster
  - [ ] Pasos para obter connection string
  - [ ] Captura de pantallas
  - **Arquivo:** `docs/MONGODB_SETUP.md` (crear)
  - **Tempo Estimado:** 30 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-053** - Crear guía de uso para desenvolvedores
  - [ ] Descricción de paquetes
  - [ ] Como usar RecordRepository
  - [ ] Como engadir novas operacións
  - **Arquivo:** `docs/DEVELOPER_GUIDE.md` (crear)
  - **Tempo Estimado:** 45 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-054** - Documentar estructura de datos en MongoDB
  - [ ] Collección: records
  - [ ] Campos: _id, score, fecha, userId, syncedAt, isSynced, isDeleted
  - [ ] Índices necesarios
  - **Arquivo:** `docs/MONGODB_SCHEMA.md` (crear)
  - **Tempo Estimado:** 20 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-055** - Crear README con pasos completos
  - [ ] Como configurar o proxecto
  - [ ] Como executar en emulador
  - [ ] Como usar MongoDB
  - [ ] Troubleshooting común
  - **Arquivo:** `docs/README_MONGODB.md` (crear)
  - **Tempo Estimado:** 45 minutos
  - **Status:** ⏳ Pendente

- [ ] **TASK-056** - Revisar e optimizar consumo de datos
  - [ ] Medir tamaño de datos transferidos
  - [ ] Optimizar queries
  - [ ] Implementar paginación se é necesario
  - **Tempo Estimado:** 60 minutos
  - **Status:** ⏳ Dependencia de TASK-050

- [ ] **TASK-057** - Preparar versión para release
  - [ ] Cambiar version code en build.gradle.kts
  - [ ] Cambiar version name (v1.1)
  - [ ] Executar build en release mode
  - [ ] Probar en dispositivo real se é posible
  - **Arquivo:** `app/build.gradle.kts`
  - **Tempo Estimado:** 30 minutos
  - **Status:** ⏳ Pendente

---

## 📊 Resumen de Progreso

### Completado
- ✅ Creación de plans de implementación

### En Progreso
- ⏳ Ninguna tarea iniciada aínda

### Pendente
- ⬜ 57 tarefas de implementación

---

## 🎯 Resumo por Fase

| Fase | Nome | Tarefas | Status | Tempo Est. |
|------|------|---------|--------|-----------|
| 1 | Configuración Inicial | 8 | ⬜ Planned | 1-2 días |
| 2 | Arquitectura de Datos | 6 | ⬜ Planned | 2-3 días |
| 3 | Operacións CRUD | 7 | ⬜ Planned | 2-3 días |
| 4 | Sincronización | 7 | ⬜ Planned | 3-4 días |
| 5 | Integración UI | 8 | ⬜ Planned | 2-3 días |
| 6 | Seguridade | 6 | ⬜ Planned | 1-2 días |
| 7 | Probas | 9 | ⬜ Planned | 2-3 días |
| 8 | Documentación | 6 | ⬜ Planned | 1-2 días |
| **TOTAL** | **8 Fases** | **58 Tarefas** | **Planned** | **14-22 días** |

---

## 💡 Dicas de Uso

1. **Marca as tarefas conforme as completes** - Usa [x] en lugar de [ ]
2. **Engade datas reais de comenzo e fin** - Para comparar con estimacións
3. **Documenta problemas** - En unha sección de "Problemas Encontrados"
4. **Actualiza o status** - Cambia "Planned" por "In Progress" e "Completed"
5. **Vincula aos pull requests** - Engade PR number cando completes tarefas

---

## 📞 Contacto & Soporte

- **Plan Completo:** `feature-mongodb-integration-1.0.md`
- **Guía Rápida:** `guia-rapida-mongodb.md`
- **Código Exemplos:** `codigo-ejemplos-mongodb.md`
- **Índice:** `README.md` en directory `/plan`

---

**Última actualización:** 2026-01-14  
**Status:** ✅ Listo para comenzar  
**Comezar aquí:** ↑ Marca as tarefas que vas completando

