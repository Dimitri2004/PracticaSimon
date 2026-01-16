# 📖 Guía: Cómo Crear los Issues en GitHub

Esta guía te ayudará a crear todos los 15 issues en tu repositorio de GitHub PracticaSimon.

---

## 🌐 Acceso al Repositorio

1. Abre: https://github.com/Dimitri2004/PracticaSimon
2. Haz clic en la pestaña **"Issues"**
3. Haz clic en el botón verde **"New issue"**

---

## 📋 Estructura de cada Issue

Cada issue tendrá la siguiente estructura:

```
TÍTULO: [TASK-XXX]: Descripción del trabajo

DESCRIPCIÓN:
[Texto en Markdown con la descripción completa]

ETIQUETAS (Labels):
[etiqueta1] [etiqueta2] [etiqueta3]
```

---

## 🎯 Fase 1: Preparación e Configuración Inicial

### Issue 1️⃣ - TASK-001

**Título:**
```
TASK-001: Crear conta en MongoDB Atlas
```

**Descripción:**
```markdown
## Descrición
Crear una cuenta en MongoDB Atlas y configurar el acceso inicial.

## Detalles de la tarea
- Ir a https://www.mongodb.com/cloud/atlas
- Crear una nueva cuenta con correo corporativo
- Verificar correo electrónico
- Completar el perfil de usuario

## Criterios de aceptación
- [ ] Cuenta creada en MongoDB Atlas
- [ ] Usuario verificado y activo
- [ ] Acceso a la consola de MongoDB Atlas disponible

## Fase
Fase 1: Preparación e Configuración Inicial
```

**Etiquetas:** `mongodb`, `database`, `cloud`, `feature`

---

### Issue 2️⃣ - TASK-002

**Título:**
```
TASK-002: Crear un cluster gratuito en MongoDB Atlas
```

**Descripción:**
```markdown
## Descrición
Crear y configurar un cluster de base de datos gratuito en MongoDB Atlas.

## Detalles de la tarea
- En la consola de MongoDB Atlas, seleccionar "Create a Deployment"
- Elegir el plan M0 (gratuito)
- Seleccionar región cercana (AWS region recomendada)
- Configurar nombre del cluster
- Completar la creación

## Criterios de aceptación
- [ ] Cluster creado y ejecutándose
- [ ] Estado del cluster es "RUNNING"
- [ ] Acceso a la URI de conexión disponible

## Fase
Fase 1: Preparación e Configuración Inicial
```

**Etiquetas:** `mongodb`, `database`, `feature`

---

### Issue 3️⃣ - TASK-003

**Título:**
```
TASK-003: Configurar reglas de acceso en MongoDB Atlas
```

**Descripción:**
```markdown
## Descrición
Configurar las reglas de acceso de red (Network Access) en MongoDB Atlas para permitir conexiones desde la aplicación Android.

## Detalles de la tarea
- En MongoDB Atlas, ir a "Network Access"
- Agregar dirección IP de la aplicación (inicialmente 0.0.0.0/0 para desarrollo)
- Crear lista blanca de IPs permitidas
- Verificar reglas de firewall

## Criterios de aceptación
- [ ] Reglas de Network Access configuradas
- [ ] Dirección IP permitida en la lista blanca
- [ ] Conexión desde cliente es posible

## ⚠️ Notas de seguridad
En producción, usar IPs específicas en lugar de 0.0.0.0/0

## Fase
Fase 1: Preparación e Configuración Inicial
```

**Etiquetas:** `mongodb`, `security`, `feature`

---

### Issue 4️⃣ - TASK-004

**Título:**
```
TASK-004: Crear usuario de base de datos con permisos mínimos
```

**Descripción:**
```markdown
## Descrición
Crear un usuario de base de datos en MongoDB Atlas con permisos mínimos necesarios para la aplicación.

## Detalles de la tarea
- En MongoDB Atlas, ir a "Database Access"
- Crear nuevo usuario de base de datos
- Usar nombre descriptivo (ej: android-app-user)
- Generar contraseña segura
- Asignar rol: readWriteAnyDatabase o específico para la base de datos
- Guardar credenciales en lugar seguro

## Criterios de aceptación
- [ ] Usuario creado en MongoDB Atlas
- [ ] Contraseña segura generada
- [ ] Permisos asignados correctamente
- [ ] Credenciales guardadas de forma segura

## Fase
Fase 1: Preparación e Configuración Inicial
```

**Etiquetas:** `mongodb`, `security`, `feature`

---

### Issue 5️⃣ - TASK-005

**Título:**
```
TASK-005: Obtener connection string de MongoDB Atlas
```

**Descripción:**
```markdown
## Descrición
Obtener y configurar la cadena de conexión (connection string) de MongoDB Atlas para la aplicación Android.

## Detalles de la tarea
- En MongoDB Atlas, seleccionar el cluster
- Hacer clic en "Connect"
- Elegir "Connect your application"
- Copiar la connection string
- Reemplazar <password> con la contraseña del usuario creado
- Reemplazar <username> con el nombre de usuario
- Guardar en `secrets.properties` del proyecto

## Formato esperado
```
mongodb+srv://username:password@cluster0.xxxxx.mongodb.net/database_name?retryWrites=true&w=majority
```

## Criterios de aceptación
- [ ] Connection string obtenida
- [ ] Credenciales reemplazadas correctamente
- [ ] Guardada en secrets.properties
- [ ] Acceso a MongoDB verificado

## Fase
Fase 1: Preparación e Configuración Inicial
```

**Etiquetas:** `mongodb`, `configuration`, `feature`

---

### Issue 6️⃣ - TASK-006

**Título:**
```
TASK-006: Agregar MongoDB Realm SDK en gradle/libs.versions.toml
```

**Descripción:**
```markdown
## Descrición
Actualizar el archivo `gradle/libs.versions.toml` para agregar la versión de MongoDB Realm SDK.

## Detalles de la tarea
- Abrir archivo `gradle/libs.versions.toml`
- En la sección `[versions]`, agregar:
  ```
  mongodb-realm = "1.8.0"
  ```
- En la sección `[libraries]`, agregar:
  ```
  mongodb-realm = { group = "io.realm.kotlin", name = "library-sync", version.ref = "mongodb-realm" }
  ```
- Sincronizar Gradle

## Criterios de aceptación
- [ ] Versión agregada a [versions]
- [ ] Librería agregada a [libraries]
- [ ] Gradle sincroniza sin errores
- [ ] Referencias de versión correctas

## Fase
Fase 1: Preparación e Configuración Inicial

## Archivo a modificar
`gradle/libs.versions.toml`
```

**Etiquetas:** `gradle`, `dependencies`, `feature`

---

### Issue 7️⃣ - TASK-007

**Título:**
```
TASK-007: Actualizar app/build.gradle.kts con dependencias de Realm/MongoDB
```

**Descripción:**
```markdown
## Descrición
Actualizar el archivo `app/build.gradle.kts` para agregar el plugin de Realm y las dependencias de MongoDB.

## Detalles de la tarea
- Abrir archivo `app/build.gradle.kts`
- En la sección `plugins`, agregar:
  ```kotlin
  id("io.realm.kotlin") version "1.8.0"
  ```
- En la sección `dependencies`, agregar:
  ```kotlin
  implementation(libs.mongodb-realm)
  ```
- Sincronizar Gradle y resolver conflictos si existen
- Verificar que no hay errores de compilación

## Criterios de aceptación
- [ ] Plugin de Realm agregado
- [ ] Dependencia mongodb-realm agregada
- [ ] Gradle sincroniza sin errores
- [ ] Sin conflictos de versiones
- [ ] Proyecto compila exitosamente

## Fase
Fase 1: Preparación e Configuración Inicial

## Archivo a modificar
`app/build.gradle.kts`
```

**Etiquetas:** `gradle`, `build`, `feature`

---

### Issue 8️⃣ - TASK-008

**Título:**
```
TASK-008: Sincronizar Gradle y verificar compilación
```

**Descripción:**
```markdown
## Descrición
Sincronizar Gradle y verificar que el proyecto compila correctamente con todas las dependencias de MongoDB.

## Detalles de la tarea
- Ejecutar `./gradlew clean`
- Ejecutar `./gradlew build`
- Revisar el archivo de salida para errores
- Resolver cualquier conflicto de dependencias
- Ejecutar `./gradlew assemble` para verificar la compilación completa

## Criterios de aceptación
- [ ] Gradle sincroniza exitosamente
- [ ] `./gradlew build` se completa sin errores
- [ ] No hay warnings críticos
- [ ] APK se construye correctamente
- [ ] Todas las dependencias están resueltas

## Fase
Fase 1: Preparación e Configuración Inicial
```

**Etiquetas:** `gradle`, `build`, `verification`

---

## 🏗️ Fase 2: Creación da Arquitectura de Datos

### Issue 9️⃣ - TASK-009

**Título:**
```
TASK-009: Crear clase RecordRemote.kt para representación en MongoDB
```

**Descripción:**
```markdown
## Descrición
Crear una clase data (RecordRemote.kt) que represente la estructura de un récord en MongoDB.

## Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`
- Definir data class con propiedades:
  - id: String (ObjectId de MongoDB)
  - name: String
  - score: Int
  - duration: Long
  - timestamp: Long
  - synced: Boolean
  - createdAt: Long
  - updatedAt: Long
- Agregar anotaciones de Realm si es necesario
- Documentar la clase

## Estructura esperada
```kotlin
@RealmObject
data class RecordRemote(
    @PrimaryKey
    val _id: ObjectId = ObjectId(),
    val name: String = "",
    val score: Int = 0,
    val duration: Long = 0L,
    val timestamp: Long = System.currentTimeMillis(),
    val synced: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
```

## Criterios de aceptación
- [ ] Clase creada en la ruta correcta
- [ ] Todas las propiedades definidas
- [ ] Anotaciones de Realm aplicadas
- [ ] Compilación exitosa
- [ ] Estructura coherente con Room

## Fase
Fase 2: Creación da Arquitectura de Datos

## Archivo a crear
`app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`
```

**Etiquetas:** `data-model`, `mongodb`, `architecture`

---

### Issue 🔟 - TASK-010

**Título:**
```
TASK-010: Crear interface IRecordRepository.kt con contrato CRUD
```

**Descripción:**
```markdown
## Descrición
Crear una interface que define el contrato de operaciones CRUD para trabajar con récords.

## Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`
- Definir interface con métodos:
  - suspend fun createRecord(record: RecordRemote): Result<String>
  - suspend fun readRecord(id: String): Result<RecordRemote>
  - suspend fun readAllRecords(): Result<List<RecordRemote>>
  - suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit>
  - suspend fun deleteRecord(id: String): Result<Unit>
- Usar sealed class Result para manejo de errores

## Criterios de aceptación
- [ ] Interface creada con todos los métodos
- [ ] Métodos usan suspend para operaciones asincrónicas
- [ ] Uso de Result para manejo de errores
- [ ] Documentación clara
- [ ] Compatible con Kotlin Coroutines

## Fase
Fase 2: Creación da Arquitectura de Datos

## Archivo a crear
`app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`
```

**Etiquetas:** `repository`, `architecture`, `interface`

---

### Issue 1️⃣1️⃣ - TASK-011

**Título:**
```
TASK-011: Crear RecordLocalRepository.kt para acceso a datos locales (Room)
```

**Descripción:**
```markdown
## Descrición
Crear la implementación del repositorio para operaciones de datos locales usando Room.

## Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordLocalRepository.kt`
- Implementar interface IRecordRepository
- Inyectar RecordDao (ya existente en Room)
- Implementar métodos CRUD usando las operaciones de Room
- Convertir RecordEntity de Room a RecordRemote cuando sea necesario

## Métodos a implementar
- createRecord(): Insertar en Room
- readRecord(): Obtener un registro por ID
- readAllRecords(): Obtener todos los registros
- updateRecord(): Actualizar un registro
- deleteRecord(): Eliminar un registro

## Criterios de aceptación
- [ ] Clase creada correctamente
- [ ] Todos los métodos implementados
- [ ] Conversión entre Room y modelo remoto
- [ ] Manejo de excepciones
- [ ] Compilación exitosa

## Fase
Fase 2: Creación da Arquitectura de Datos

## Archivo a crear
`app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordLocalRepository.kt`
```

**Etiquetas:** `repository`, `room`, `local-data`

---

## 🔐 Fase 3: Implementación de Operacións CRUD

### Issue 1️⃣2️⃣ - TASK-015

**Título:**
```
TASK-015: Implementar operación create() en RecordRemoteRepository
```

**Descripción:**
```markdown
## Descrición
Crear la implementación del repositorio para operaciones de datos remotos usando MongoDB y completar la operación CREATE.

## Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`
- Implementar interface IRecordRepository
- Implementar método createRecord()
- Conectar con Realm SDK para insertar en MongoDB
- Validar datos antes de insertar
- Manejar excepciones de conexión y timeout
- Retornar el ID del documento creado

## Implementación esperada
```kotlin
override suspend fun createRecord(record: RecordRemote): Result<String> {
    return try {
        val realm = Realm.open(config)
        realm.write {
            val inserted = copyToRealm(record)
            Result.success(inserted._id.toString())
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

## Criterios de aceptación
- [ ] Método implementado correctamente
- [ ] Validación de datos pre-inserción
- [ ] Manejo de errores de conexión
- [ ] Retorno correcto del ID creado
- [ ] Tests unitarios pasan

## Fase
Fase 3: Implementación de Operacións CRUD

## Archivo a crear
`app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`
```

**Etiquetas:** `crud`, `mongodb`, `create`

---

### Issue 1️⃣3️⃣ - TASK-020

**Título:**
```
TASK-020: Crear NetworkUtil.kt para verificar conexión a internet
```

**Descripción:**
```markdown
## Descrición
Crear una clase utilitaria para verificar el estado de la conexión a internet del dispositivo.

## Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`
- Implementar métodos para:
  - Verificar si hay conexión a internet
  - Obtener tipo de conexión (WiFi, Mobile, None)
  - Monitorear cambios de estado de red
- Usar ConnectivityManager de Android
- Hacer clase observable si es posible

## Métodos esperados
```kotlin
object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean
    fun getNetworkType(context: Context): NetworkType
    fun observeNetworkStatus(context: Context): Flow<Boolean>
}

enum class NetworkType {
    WIFI, MOBILE, NONE
}
```

## Criterios de aceptación
- [ ] Clase creada en la ruta correcta
- [ ] Métodos de verificación implementados
- [ ] Soporte para API 30+
- [ ] Sin bloqueos en el hilo principal
- [ ] Tests unitarios pasan

## Fase
Fase 3: Implementación de Operacións CRUD

## Archivo a crear
`app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`
```

**Etiquetas:** `network`, `utility`, `connectivity`

---

## 🎨 Fase 5: Integración coa UI

### Issue 1️⃣4️⃣ - TASK-033

**Título:**
```
TASK-033: Agregar botón "Descargar de MongoDB" en Interfaz.kt
```

**Descripción:**
```markdown
## Descrición
Agregar un botón en la interfaz de usuario que permita al usuario descargar datos desde MongoDB.

## Detalles de la tarea
- Abrir archivo: `app/src/main/java/gz/dam/trabajosimondize/main/Interfaz.kt`
- Crear función composable para botón de descarga
- Conectar con MyViewModel para ejecutar descarga
- Mostrar estado de carga (loading, success, error)
- Actualizar lista de récords con datos descargados

## Comportamiento esperado
- Botón deshabilitado mientras se descarga
- Mostrar spinner/progress mientras está en progreso
- Toast con mensajes de éxito/error
- Actualizar lista de récords automáticamente

## Criterios de aceptación
- [ ] Botón agregado en la UI
- [ ] Funcionalidad de descarga conectada
- [ ] Estados visuales claros
- [ ] Manejo de errores visible
- [ ] Sin bloqueos de UI

## Fase
Fase 5: Integración coa UI

## Archivo a modificar
`app/src/main/java/gz/dam/trabajosimondize/main/Interfaz.kt`
```

**Etiquetas:** `ui`, `compose`, `download`

---

## 🔒 Fase 6: Seguridade e Configuración

### Issue 1️⃣5️⃣ - TASK-041

**Título:**
```
TASK-041: Configurar permisos de Android necesarios
```

**Descrición:**
```markdown
## Descrición
Configurar los permisos necesarios en AndroidManifest.xml para permitir acceso a internet y estado de red.

## Detalles de la tarea
- Abrir archivo: `app/src/main/AndroidManifest.xml`
- Agregar los siguientes permisos:
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  ```
- Verificar que los permisos sean necesarios
- Documentar por qué se necesita cada permiso

## Ubicación
Los permisos deben agregarse antes de la etiqueta `<application>`

## Criterios de aceptación
- [ ] Permisos INTERNET agregados
- [ ] Permisos ACCESS_NETWORK_STATE agregados
- [ ] Manifest válido y compila
- [ ] Documentación de permisos
- [ ] Sin permisos innecesarios

## Fase
Fase 6: Seguridade e Configuración

## Archivo a modificar
`app/src/main/AndroidManifest.xml`
```

**Etiquetas:** `android-manifest`, `permissions`, `configuration`

---

## ✅ Próximos Pasos

Una vez creados todos los issues:

1. **Asignar** issues a ti mismo o a un equipo
2. **Establecer** prioridades
3. **Crear** milestones para cada fase
4. **Vincular** issues relacionados
5. **Comenzar** a trabajar según el orden recomendado

---

## 📞 Soporte

Si tienes problemas al crear los issues:

1. Verifica que tienes permisos en el repositorio
2. Asegúrate de estar autenticado en GitHub
3. Consulta la documentación oficial de GitHub Issues

---

**Documento generado:** 2026-01-16  
**Última actualización:** 2026-01-16  
**Estado:** ✅ Listo para usar

