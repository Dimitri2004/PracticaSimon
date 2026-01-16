# GitHub Issues - Plantilla para MongoDB Integration

Esta es una plantilla con todos los issues que se necesitan crear en GitHub basados en el plan de integración de MongoDB. Puedes copiar y pegar cada sección en GitHub Issues manualmente.

---

## ISSUE 1: TASK-001 - Crear conta en MongoDB Atlas

### Título
`TASK-001: Crear conta en MongoDB Atlas`

### Descrición
Crear una cuenta en MongoDB Atlas y configurar el acceso inicial.

### Detalles de la tarea
- Ir a https://www.mongodb.com/cloud/atlas
- Crear una nueva cuenta con correo corporativo
- Verificar correo electrónico
- Completar el perfil de usuario

### Criterios de aceptación
- [x] Cuenta creada en MongoDB Atlas
- [x] Usuario verificado y activo
- [x] Acceso a la consola de MongoDB Atlas disponible

### Etiquetas
`mongodb` `database` `cloud` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 2: TASK-002 - Crear un cluster gratuito en MongoDB Atlas

### Título
`TASK-002: Crear un cluster gratuito en MongoDB Atlas`

### Descrición
Crear y configurar un cluster de base de datos gratuito en MongoDB Atlas.

### Detalles de la tarea
- En la consola de MongoDB Atlas, seleccionar "Create a Deployment"
- Elegir el plan M0 (gratuito)
- Seleccionar región cercana (AWS region recomendada)
- Configurar nombre del cluster
- Completar la creación

### Criterios de aceptación
- [x] Cluster creado y ejecutándose
- [x] Estado del cluster es "RUNNING"
- [x] Acceso a la URI de conexión disponible

### Etiquetas
`mongodb` `database` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 3: TASK-003 - Configurar reglas de acceso en MongoDB Atlas

### Título
`TASK-003: Configurar reglas de acceso en MongoDB Atlas`

### Descrición
Configurar las reglas de acceso de red (Network Access) en MongoDB Atlas para permitir conexiones desde la aplicación Android.

### Detalles de la tarea
- En MongoDB Atlas, ir a "Network Access"
- Agregar dirección IP de la aplicación (inicialmente 0.0.0.0/0 para desarrollo)
- Crear lista blanca de IPs permitidas
- Verificar reglas de firewall

### Criterios de aceptación
- [x] Reglas de Network Access configuradas
- [x] Dirección IP permitida en la lista blanca
- [x] Conexión desde cliente es posible

### Notas de seguridad
En producción, usar IPs específicas en lugar de 0.0.0.0/0

### Etiquetas
`mongodb` `security` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 4: TASK-004 - Crear usuario de base de datos con permisos mínimos

### Título
`TASK-004: Crear usuario de base de datos con permisos mínimos`

### Descrición
Crear un usuario de base de datos en MongoDB Atlas con permisos mínimos necesarios para la aplicación.

### Detalles de la tarea
- En MongoDB Atlas, ir a "Database Access"
- Crear nuevo usuario de base de datos
- Usar nombre descriptivo (ej: android-app-user)
- Generar contraseña segura
- Asignar rol: readWriteAnyDatabase o específico para la base de datos
- Guardar credenciales en lugar seguro

### Criterios de aceptación
- [x] Usuario creado en MongoDB Atlas
- [x] Contraseña segura generada
- [x] Permisos asignados correctamente
- [x] Credenciales guardadas de forma segura

### Etiquetas
`mongodb` `security` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 5: TASK-005 - Obtener connection string de MongoDB Atlas

### Título
`TASK-005: Obtener connection string de MongoDB Atlas`

### Descrición
Obtener y configurar la cadena de conexión (connection string) de MongoDB Atlas para la aplicación Android.

### Detalles de la tarea
- En MongoDB Atlas, seleccionar el cluster
- Hacer clic en "Connect"
- Elegir "Connect your application"
- Copiar la connection string
- Reemplazar <password> con la contraseña del usuario creado
- Reemplazar <username> con el nombre de usuario
- Guardar en secrets.properties del proyecto

### Formato esperado
```
mongodb+srv://username:password@cluster0.xxxxx.mongodb.net/database_name?retryWrites=true&w=majority
```

### Criterios de aceptación
- [x] Connection string obtenida
- [x] Credenciales reemplazadas correctamente
- [x] Guardada en secrets.properties
- [x] Acceso a MongoDB verificado

### Etiquetas
`mongodb` `configuration` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 6: TASK-006 - Agregar MongoDB Realm SDK en gradle/libs.versions.toml

### Título
`TASK-006: Agregar MongoDB Realm SDK en gradle/libs.versions.toml`

### Descrición
Actualizar el archivo gradle/libs.versions.toml para agregar la versión de MongoDB Realm SDK.

### Detalles de la tarea
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

### Criterios de aceptación
- [x] Versión agregada a [versions]
- [x] Librería agregada a [libraries]
- [x] Gradle sincroniza sin errores
- [x] Referencias de versión correctas

### Etiquetas
`gradle` `dependencies` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 7: TASK-007 - Actualizar app/build.gradle.kts con dependencias de Realm/MongoDB

### Título
`TASK-007: Actualizar app/build.gradle.kts con dependencias de Realm/MongoDB`

### Descrición
Actualizar el archivo app/build.gradle.kts para agregar el plugin de Realm y las dependencias de MongoDB.

### Detalles de la tarea
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

### Criterios de aceptación
- [x] Plugin de Realm agregado
- [x] Dependencia mongodb-realm agregada
- [x] Gradle sincroniza sin errores
- [x] Sin conflictos de versiones
- [x] Proyecto compila exitosamente

### Etiquetas
`gradle` `build` `feature`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 8: TASK-008 - Sincronizar Gradle y verificar compilación

### Título
`TASK-008: Sincronizar Gradle y verificar compilación`

### Descrición
Sincronizar Gradle y verificar que el proyecto compila correctamente con todas las dependencias de MongoDB.

### Detalles de la tarea
- Ejecutar `./gradlew clean`
- Ejecutar `./gradlew build`
- Revisar el archivo de salida para errores
- Resolver cualquier conflicto de dependencias
- Ejecutar `./gradlew assemble` para verificar la compilación completa

### Criterios de aceptación
- [x] Gradle sincroniza exitosamente
- [x] `./gradlew build` se completa sin errores
- [x] No hay warnings críticos
- [x] APK se construye correctamente
- [x] Todas las dependencias están resueltas

### Etiquetas
`gradle` `build` `verification`

### Fase
Fase 1: Preparación e Configuración Inicial

---

## ISSUE 9: TASK-009 - Crear clase RecordRemote.kt para representación en MongoDB

### Título
`TASK-009: Crear clase RecordRemote.kt para representación en MongoDB`

### Descrición
Crear una clase data (RecordRemote.kt) que represente la estructura de un récord en MongoDB.

### Detalles de la tarea
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

### Estructura esperada
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

### Criterios de aceptación
- [x] Clase creada en la ruta correcta
- [x] Todas las propiedades definidas
- [x] Anotaciones de Realm aplicadas
- [x] Compilación exitosa
- [x] Estructura coherente con Room

### Etiquetas
`data-model` `mongodb` `architecture`

### Fase
Fase 2: Creación da Arquitectura de Datos

---

## ISSUE 10: TASK-010 - Crear interface IRecordRepository.kt con contrato CRUD

### Título
`TASK-010: Crear interface IRecordRepository.kt con contrato CRUD`

### Descrición
Crear una interface que define el contrato de operaciones CRUD para trabajar con récords.

### Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`
- Definir interface con métodos:
  - suspend fun createRecord(record: RecordRemote): Result<String>
  - suspend fun readRecord(id: String): Result<RecordRemote>
  - suspend fun readAllRecords(): Result<List<RecordRemote>>
  - suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit>
  - suspend fun deleteRecord(id: String): Result<Unit>
- Usar sealed class Result para manejo de errores

### Criterios de aceptación
- [x] Interface creada con todos los métodos
- [x] Métodos usan suspend para operaciones asincrónicas
- [x] Uso de Result para manejo de errores
- [x] Documentación clara
- [x] Compatible con Kotlin Coroutines

### Etiquetas
`repository` `architecture` `interface`

### Fase
Fase 2: Creación da Arquitectura de Datos

---

## ISSUE 11: TASK-011 - Crear RecordLocalRepository.kt para acceso a datos locales (Room)

### Título
`TASK-011: Crear RecordLocalRepository.kt para acceso a datos locales (Room)`

### Descrición
Crear la implementación del repositorio para operaciones de datos locales usando Room.

### Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordLocalRepository.kt`
- Implementar interface IRecordRepository
- Inyectar RecordDao (ya existente en Room)
- Implementar métodos CRUD usando las operaciones de Room
- Convertir RecordEntity de Room a RecordRemote cuando sea necesario

### Métodos a implementar
- createRecord(): Insertar en Room
- readRecord(): Obtener un registro por ID
- readAllRecords(): Obtener todos los registros
- updateRecord(): Actualizar un registro
- deleteRecord(): Eliminar un registro

### Criterios de aceptación
- [x] Clase creada correctamente
- [x] Todos los métodos implementados
- [x] Conversión entre Room y modelo remoto
- [x] Manejo de excepciones
- [x] Compilación exitosa

### Etiquetas
`repository` `room` `local-data`

### Fase
Fase 2: Creación da Arquitectura de Datos

---

## ISSUE 12: TASK-015 - Implementar operación create() en RecordRemoteRepository

### Título
`TASK-015: Implementar operación create() en RecordRemoteRepository`

### Descrición
Crear la implementación del repositorio para operaciones de datos remotos usando MongoDB y completar la operación CREATE.

### Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`
- Implementar interface IRecordRepository
- Implementar método createRecord()
- Conectar con Realm SDK para insertar en MongoDB
- Validar datos antes de insertar
- Manejar excepciones de conexión y timeout
- Retornar el ID del documento creado

### Implementación esperada
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

### Criterios de aceptación
- [x] Método implementado correctamente
- [x] Validación de datos pre-inserción
- [x] Manejo de errores de conexión
- [x] Retorno correcto del ID creado
- [x] Tests unitarios pasan

### Etiquetas
`crud` `mongodb` `create`

### Fase
Fase 3: Implementación de Operacións CRUD

---

## ISSUE 13: TASK-020 - Crear NetworkUtil.kt para verificar conexión a internet

### Título
`TASK-020: Crear NetworkUtil.kt para verificar conexión a internet`

### Descrición
Crear una clase utilitaria para verificar el estado de la conexión a internet del dispositivo.

### Detalles de la tarea
- Crear archivo: `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`
- Implementar métodos para:
  - Verificar si hay conexión a internet
  - Obtener tipo de conexión (WiFi, Mobile, None)
  - Monitorear cambios de estado de red
- Usar ConnectivityManager de Android
- Hacer clase observable si es posible

### Métodos esperados
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

### Criterios de aceptación
- [x] Clase creada en la ruta correcta
- [x] Métodos de verificación implementados
- [x] Soporte para API 30+
- [x] Sin bloqueos en el hilo principal
- [x] Tests unitarios pasan

### Etiquetas
`network` `utility` `connectivity`

### Fase
Fase 3: Implementación de Operacións CRUD

---

## ISSUE 14: TASK-033 - Agregar botón "Descargar de MongoDB" en Interfaz.kt

### Título
`TASK-033: Agregar botón "Descargar de MongoDB" en Interfaz.kt`

### Descrición
Agregar un botón en la interfaz de usuario que permita al usuario descargar datos desde MongoDB.

### Detalles de la tarea
- Abrir archivo: `app/src/main/java/gz/dam/trabajosimondize/main/Interfaz.kt`
- Crear función composable para botón de descarga
- Conectar con MyViewModel para ejecutar descarga
- Mostrar estado de carga (loading, success, error)
- Actualizar lista de récords con datos descargados

### Comportamiento esperado
- Botón deshabilitado mientras se descarga
- Mostrar spinner/progress mientras está en progreso
- Toast con mensajes de éxito/error
- Actualizar lista de récords automáticamente

### Criterios de aceptación
- [x] Botón agregado en la UI
- [x] Funcionalidad de descarga conectada
- [x] Estados visuales claros
- [x] Manejo de errores visible
- [x] Sin bloqueos de UI

### Etiquetas
`ui` `compose` `download`

### Fase
Fase 5: Integración coa UI

---

## ISSUE 15: TASK-041 - Configurar permisos de Android necesarios

### Título
`TASK-041: Configurar permisos de Android necesarios`

### Descrición
Configurar los permisos necesarios en AndroidManifest.xml para permitir acceso a internet y estado de red.

### Detalles de la tarea
- Abrir archivo: `app/src/main/AndroidManifest.xml`
- Agregar los siguientes permisos:
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  ```
- Verificar que los permisos sean necesarios
- Documentar por qué se necesita cada permiso

### Ubicación
Los permisos deben agregarse antes de la etiqueta `<application>`

### Criterios de aceptación
- [x] Permisos INTERNET agregados
- [x] Permisos ACCESS_NETWORK_STATE agregados
- [x] Manifest válido y compila
- [x] Documentación de permisos
- [x] Sin permisos innecesarios

### Etiquetas
`android-manifest` `permissions` `configuration`

### Fase
Fase 6: Seguridade e Configuración

---

# Cómo usar esta plantilla

1. Ve a tu repositorio: https://github.com/Dimitri2004/PracticaSimon/issues
2. Haz clic en "New issue"
3. Copia el título de cada sección
4. Copia la descripción y estructura
5. Selecciona las etiquetas correspondientes
6. Crea el issue

**Nota:** Puedes crear todos los issues en lotes o individuales según tu preferencia.

---

**Fecha de generación:** 2026-01-16  
**Plan base:** feature-mongodb-integration-1.0.md  
**Total de issues:** 15

