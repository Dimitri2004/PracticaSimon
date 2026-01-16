# Guía de Integración de MongoDB en PracticaSimon

## Descripción General

Esta guía describe cómo configurar e integrar **MongoDB Atlas (servicio en la nube)** con el proyecto PracticaSimon para almacenar récords en una base de datos remota.

## 1. Configuración de MongoDB Atlas

### 1.1 Crear una Cuenta en MongoDB Atlas

1. Acceder a [https://www.mongodb.com/cloud/atlas](https://www.mongodb.com/cloud/atlas)
2. Hacer clic en "Try Free" o "Sign Up"
3. Crear una cuenta con correo o Google/GitHub

### 1.2 Crear un Cluster

1. En el dashboard, hacer clic en "Create Deployment"
2. Seleccionar "Create a Free Cluster"
3. Elegir el proveedor (AWS, Google Cloud, Azure) - cualquiera funciona
4. Seleccionar una región cercana (ej: eu-west-1 para Europa)
5. Hacer clic en "Create Deployment"

### 1.3 Configurar Credenciales

1. En la pantalla de creación, se pedirá crear un usuario:
   - **Usuario**: Ej: `practicasimon_user`
   - **Contraseña**: Crear una segura (guardarla en `secrets.properties`)

2. Hacer clic en "Create User"

### 1.4 Configurar Network Access

1. En el menú izquierdo, ir a "Network Access"
2. Hacer clic en "Add IP Address"
3. Elegir "Allow Access from Anywhere" (para desarrollo)
   - **Nota**: En producción, especificar IPs concretas
4. Hacer clic en "Confirm"

### 1.5 Obtener la Connection String

1. Ir a la página del cluster
2. Hacer clic en "Connect"
3. Seleccionar "Connect your application"
4. Elegir "Kotlin" como driver
5. Copiar la connection string:
   ```
   mongodb+srv://<username>:<password>@cluster.mongodb.net/?retryWrites=true&w=majority
   ```
6. Reemplazar `<username>` y `<password>` con los valores creados

## 2. Configuración del Proyecto Android

### 2.1 Actualizar secrets.properties

1. Abrir o crear el archivo `/secrets.properties` en la raíz del proyecto
2. Actualizar los valores:

```properties
# MongoDB Atlas Connection String
MONGODB_URI=mongodb+srv://practicasimon_user:tu_contraseña@cluster0.xxxxx.mongodb.net/?retryWrites=true&w=majority

# Nombre de la base de datos
MONGODB_DATABASE=practicasimon

# Nombre de la colección
MONGODB_COLLECTION=records

# API Key de MongoDB (si se usa)
MONGODB_API_KEY=

# ID de App de Realm (si se usa Realm SDK)
REALM_APP_ID=
```

### 2.2 Verificar Dependencias

Las siguientes dependencias ya han sido agregadas al proyecto:

```gradle
// MongoDB Realm SDK
implementation(libs.mongodb.realm)

// Retrofit y Gson
implementation(libs.retrofit)
implementation(libs.retrofit.gson)
implementation(libs.okhttp)
implementation(libs.okhttp.logging)
implementation(libs.gson)

// WorkManager para sincronización
implementation(libs.androidx.work.runtime)
```

### 2.3 Verificar Permisos de Android

El archivo `AndroidManifest.xml` debe contener:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 3. Estructura del Proyecto

Nuevos archivos creados para la integración:

```
app/src/main/java/gz/dam/trabajosimondize/
├── data/
│   ├── model/
│   │   └── RecordRemote.kt (nuevo)
│   ├── repository/
│   │   ├── IRecordRepository.kt (nuevo)
│   │   ├── RecordLocalRepository.kt (nuevo)
│   │   ├── RecordRemoteRepository.kt (nuevo)
│   │   └── RecordSyncRepository.kt (nuevo)
│   ├── sync/
│   │   ├── SyncManager.kt (nuevo)
│   │   └── SyncWorker.kt (nuevo)
│   └── controller/
│       └── ControllerMongoSync.kt (nuevo)
└── util/
    └── NetworkUtil.kt (nuevo)
```

## 4. Uso en la Aplicación

### 4.1 Inicializar el Controlador

En `MyViewModel.kt`:

```kotlin
// Reemplazar el controlador actual
private val mongoController = ControllerMongoSync(
    applicationContext = getApplication(),
    mongoDbUri = "" // Se carga desde secrets.properties
)
```

### 4.2 Obtener Récord Máximo

```kotlin
// Igual que antes, pero ahora sincroniza con MongoDB
val record = mongoController.getRecord(context)
```

### 4.3 Guardar un Nuevo Récord

```kotlin
val puntuacion = 250
val fecha = LocalDateTime.now()
val resultado = mongoController.setRecord(puntuacion, fecha, context)
```

### 4.4 Sincronización Automática

```kotlin
// Iniciar sincronización periódica (cada 15 minutos)
mongoController.startAutoSync()

// Realizar sincronización manual
mongoController.syncNow()

// Detener sincronización
mongoController.stopAutoSync()
```

### 4.5 Verificar Conexión

```kotlin
val isOnline = mongoController.isNetworkAvailable()
val tipoConexion = mongoController.getConnectionType() // "WiFi", "Datos Móviles", etc.
```

## 5. Operaciones CRUD Completas

### Crear un Récord

```kotlin
val nuevoRecord = RecordRemote(
    puntuacion = 300,
    fecha = "15/01/2025 14:30:00"
)
val resultado = mongoController.syncRepository.createRecord(nuevoRecord)
```

### Leer Todos los Récords

```kotlin
val recordsResult = mongoController.getAllRecords()
recordsResult.onSuccess { records ->
    records.forEach { Log.d("Records", "${it.puntuacion} - ${it.fecha}") }
}.onFailure { error ->
    Log.e("Error", error.message ?: "Unknown error")
}
```

### Actualizar un Récord

```kotlin
val recordActualizado = RecordRemote(
    id = "123456",
    puntuacion = 350,
    fecha = "15/01/2025 15:00:00"
)
mongoController.syncRepository.updateRecord("123456", recordActualizado)
```

### Eliminar un Récord

```kotlin
mongoController.syncRepository.deleteRecord("123456")
```

## 6. Manejo de Errores

El proyecto implementa manejo robusto de errores:

```kotlin
val resultado = mongoController.getAllRecords()

when {
    resultado.isSuccess -> {
        val records = resultado.getOrNull()
        // Procesar datos
    }
    resultado.isFailure -> {
        val error = resultado.exceptionOrNull()
        Log.e("Error", error?.message ?: "Unknown error")
        // Mostrar error al usuario
    }
}
```

## 7. Funcionamiento Offline

El proyecto está diseñado para funcionar sin conexión:

1. **Modo Offline**: Los datos se guardan en Room (SQLite local)
2. **Modo Online**: Se sincroniza automáticamente con MongoDB
3. **Sincronización Pendiente**: Cuando vuelve la conexión, se sincronizan los datos pendientes

## 8. Verificación de la Configuración

### 8.1 Compilar el Proyecto

```bash
cd /home/dam/Escritorio/PracticaSimon2
./gradlew build
```

### 8.2 Verificar Conexión

En MongoDB Atlas:

1. Ir a "Database"
2. Hacer clic en "Browse Collections"
3. Crear una colección "records" si no existe
4. Insertar un documento de prueba:

```json
{
  "puntuacion": 100,
  "fecha": "15/01/2025 10:00:00",
  "sincronizado": true,
  "fechaCreacion": 1705319200000,
  "fechaModificacion": 1705319200000
}
```

## 9. Seguridad

### 9.1 Proteger Credenciales

- **secrets.properties** está en `.gitignore` - NO se commitea
- Las credenciales se cargan en tiempo de compilación
- En producción, usar variables de entorno o servidor de configuración

### 9.2 Validación de Datos

Todos los datos se validan antes de enviarse a MongoDB:

```kotlin
// RecordRemote valida automáticamente
val record = RecordRemote(puntuacion = -50, fecha = "invalid")
// Fallará en el servidor si los datos no cumplen reglas
```

## 10. Monitoreo y Debugging

### Logs Disponibles

El proyecto registra todas las operaciones:

```
D/RecordLocalRepository: Récord creado localmente con ID: 1
D/RecordRemoteRepository: Creando récord remoto: {...}
D/RecordSyncRepository: Récord creado en local y remoto con ID: 1
D/SyncManager: Sincronización periódica programada cada 15 minutos
D/NetworkUtil: Conexión a internet disponible: true
```

### Monitoreo en MongoDB Atlas

1. Ir a "Monitoring" en el dashboard
2. Ver estadísticas en tiempo real:
   - Operaciones por segundo
   - Latencia de red
   - Uso de almacenamiento
   - Operaciones de lectura/escritura

## 11. Troubleshooting

### Problema: "No Network Specified"

**Solución**: Agregar IP en Network Access de MongoDB Atlas

### Problema: "Authentication Failed"

**Solución**: Verificar usuario/contraseña en `secrets.properties`

### Problema: "Connection Timeout"

**Solución**: Verificar que MongoDB Atlas está activo y accesible

### Problema: Datos no se sincronizan

**Solución**: Activar sincronización manual con `mongoController.syncNow()`

## 12. Pasos Siguientes

1. ✅ Configurar MongoDB Atlas
2. ✅ Actualizar `secrets.properties`
3. ✅ Compilar el proyecto
4. ✅ Probar integración básica
5. 📋 Implementar UI para sincronización manual
6. 📋 Agregar estadísticas de sincronización
7. 📋 Implementar notificaciones de estado

## 13. Referencias

- [MongoDB Atlas Documentation](https://docs.mongodb.com/atlas/)
- [MongoDB Realm Kotlin SDK](https://www.mongodb.com/docs/realm/sdk/kotlin/)
- [Android Room Database](https://developer.android.com/training/data-storage/room)
- [Android WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)

---

**Última actualización**: 2025-01-15  
**Versión**: 1.0

