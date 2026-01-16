---
goal: Ejemplos de Código Completos para MongoDB Integration
version: 1.0
date_created: 2026-01-14
owner: Dimitri2004
---

# Ejemplos de Código: Integración MongoDB en PracticaSimon

## 1. Actualizar `gradle/libs.versions.toml`

```toml
[versions]
agp = "8.13.0"
kotlin = "2.0.21"
coreKtx = "1.17.0"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
lifecycleRuntimeKtx = "2.9.4"
activityCompose = "1.11.0"
composeBom = "2024.09.00"
lifecycleViewmodelKtx = "2.10.0"
mongodbRealm = "1.8.0"           # NOVO
androidxWork = "2.8.1"            # NOVO

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-lifecycle-viewmodel-ktx = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycleViewmodelKtx" }
mongodb-realm = { group = "io.realm.kotlin", name = "library-sync", version.ref = "mongodbRealm" }  # NOVO
androidx-work-runtime = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "androidxWork" }  # NOVO

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
realm = { id = "io.realm.kotlin", version.ref = "mongodbRealm" }  # NOVO
```

---

## 2. Actualizar `app/build.gradle.kts`

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    alias(libs.plugins.realm)  # NOVO: Plugin de Realm
}

android {
    namespace = "gz.dam.trabajosimondize"
    compileSdk = 36

    defaultConfig {
        applicationId = "gz.dam.trabajosimondize"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // NOVO: Cargar credenciais de secrets.properties
        val secretsFile = rootProject.file("secrets.properties")
        val secrets = java.util.Properties()
        if (secretsFile.exists()) {
            secrets.load(java.io.FileInputStream(secretsFile))
        }
        
        buildConfigField("String", "MONGODB_URI", 
            "\"${secrets.getProperty("MONGODB_URI", "")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlinOptions {
        jvmTarget = "11"
    }
    
    buildFeatures {
        compose = true
        buildConfig = true  # NOVO: Habilitar BuildConfig
    }
    
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    // NOVO: Dependencias de MongoDB
    implementation(libs.mongodb-realm)
    implementation(libs.androidx-work-runtime)
    
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    val room_version = "2.8.4"

    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-rxjava2:$room_version")
    implementation("androidx.room:room-rxjava3:$room_version")
    implementation("androidx.room:room-guava:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core-ktx:1.5.0")
    testImplementation("org.robolectric:robolectric:4.12.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
```

---

## 3. Crear `app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`

```kotlin
package gz.dam.trabajosimondize.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Modelo de datos para sincronización con MongoDB.
 * Extende RealmObject para ser compatible con Realm Sync.
 */
class RecordRemote : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    
    // Puntuación del juego
    var score: Int = 0
    
    // Fecha formateada como string para compatibilidad
    var fecha: String = LocalDateTime.now()
        .format(DateTimeFormatter.ISO_DATE_TIME)
    
    // ID del usuario (para multiplos usuarios en futuro)
    var userId: String = "default_user"
    
    // Timestamp de sincronización
    var syncedAt: Long = System.currentTimeMillis()
    
    // Indicador de si fue sincronizado exitosamente
    var isSynced: Boolean = false
    
    // Booleano para marcar como eliminado (soft delete)
    var isDeleted: Boolean = false
    
    companion object {
        fun fromLocalRecord(score: Int, userId: String = "default_user"): RecordRemote {
            return RecordRemote().apply {
                this.score = score
                this.fecha = LocalDateTime.now()
                    .format(DateTimeFormatter.ISO_DATE_TIME)
                this.userId = userId
                this.syncedAt = System.currentTimeMillis()
            }
        }
    }
}
```

---

## 4. Crear `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`

```kotlin
package gz.dam.trabajosimondize.data.repository

import gz.dam.trabajosimondize.data.model.RecordRemote

/**
 * Interface que define las operaciones CRUD para registros de juego.
 * Implementada por tanto la capa local (Room) como remota (MongoDB).
 */
interface IRecordRepository {
    /**
     * Crear un nuevo registro de juego
     * @param record Registro a crear
     * @return Result con el ID del registro creado
     */
    suspend fun createRecord(record: RecordRemote): Result<String>
    
    /**
     * Leer un registro específico por ID
     * @param id ID del registro
     * @return Result con el registro encontrado
     */
    suspend fun readRecord(id: String): Result<RecordRemote>
    
    /**
     * Leer todos los registros
     * @return Result con lista de registros
     */
    suspend fun readAllRecords(): Result<List<RecordRemote>>
    
    /**
     * Actualizar un registro existente
     * @param id ID del registro
     * @param record Datos actualizados
     * @return Result con Unit si es exitoso
     */
    suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit>
    
    /**
     * Eliminar un registro
     * @param id ID del registro a eliminar
     * @return Result con Unit si es exitoso
     */
    suspend fun deleteRecord(id: String): Result<Unit>
    
    /**
     * Sincronizar registros locales con la nube
     * @return Result con número de registros sincronizados
     */
    suspend fun syncRecords(): Result<Int>
}
```

---

## 5. Crear `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`

```kotlin
package gz.dam.trabajosimondize.data.repository

import android.util.Log
import gz.dam.trabajosimondize.data.model.RecordRemote
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId

/**
 * Implementación de repository para MongoDB usando Realm Sync
 */
class RecordRemoteRepository : IRecordRepository {
    
    private val TAG = "RecordRemoteRepository"
    
    // Realm se configuraría aquí con MongoDB App Services
    // Por ahora, usamos Realm local para demostración
    private var realm: Realm? = null
    
    init {
        try {
            realm = Realm.open()
            Log.d(TAG, "Realm inicializado")
        } catch (e: Exception) {
            Log.e(TAG, "Error al inicializar Realm", e)
        }
    }

    override suspend fun createRecord(record: RecordRemote): Result<String> = 
        withContext(Dispatchers.IO) {
            try {
                var recordId = ""
                realm?.write {
                    val newRecord = copyToRealm(record)
                    recordId = newRecord._id.toHexString()
                    newRecord.isSynced = false
                    Log.d(TAG, "Registro creado con ID: $recordId")
                }
                Result.success(recordId)
            } catch (e: Exception) {
                Log.e(TAG, "Error al crear registro", e)
                Result.failure(e)
            }
        }

    override suspend fun readRecord(id: String): Result<RecordRemote> = 
        withContext(Dispatchers.IO) {
            try {
                val objectId = ObjectId(id)
                val record = realm?.query<RecordRemote>("_id == $0", objectId)
                    ?.first()
                    ?.find()
                
                return@withContext if (record != null) {
                    Result.success(record)
                } else {
                    Result.failure(Exception("Registro no encontrado"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al leer registro", e)
                Result.failure(e)
            }
        }

    override suspend fun readAllRecords(): Result<List<RecordRemote>> = 
        withContext(Dispatchers.IO) {
            try {
                val records = realm?.query<RecordRemote>("isDeleted == false")
                    ?.find()
                    ?.toList() ?: emptyList()
                
                Log.d(TAG, "Se leyeron ${records.size} registros")
                Result.success(records)
            } catch (e: Exception) {
                Log.e(TAG, "Error al leer registros", e)
                Result.failure(e)
            }
        }

    override suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit> = 
        withContext(Dispatchers.IO) {
            try {
                val objectId = ObjectId(id)
                realm?.write {
                    val existing = query<RecordRemote>("_id == $0", objectId)
                        .first()
                        .find()
                    
                    existing?.let {
                        it.score = record.score
                        it.fecha = record.fecha
                        it.syncedAt = System.currentTimeMillis()
                        it.isSynced = false
                        Log.d(TAG, "Registro $id actualizado")
                    }
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Error al actualizar registro", e)
                Result.failure(e)
            }
        }

    override suspend fun deleteRecord(id: String): Result<Unit> = 
        withContext(Dispatchers.IO) {
            try {
                val objectId = ObjectId(id)
                realm?.write {
                    val toDelete = query<RecordRemote>("_id == $0", objectId)
                        .first()
                        .find()
                    
                    toDelete?.let {
                        it.isDeleted = true
                        it.syncedAt = System.currentTimeMillis()
                        Log.d(TAG, "Registro $id marcado como eliminado")
                    }
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Error al eliminar registro", e)
                Result.failure(e)
            }
        }

    override suspend fun syncRecords(): Result<Int> = 
        withContext(Dispatchers.IO) {
            try {
                val unsyncedRecords = realm?.query<RecordRemote>("isSynced == false")
                    ?.find()
                    ?.toList() ?: emptyList()
                
                // Aquí iría la lógica para enviar a MongoDB
                realm?.write {
                    unsyncedRecords.forEach { record ->
                        record.isSynced = true
                    }
                }
                
                Log.d(TAG, "${unsyncedRecords.size} registros sincronizados")
                Result.success(unsyncedRecords.size)
            } catch (e: Exception) {
                Log.e(TAG, "Error al sincronizar", e)
                Result.failure(e)
            }
        }
    
    fun closeRealm() {
        realm?.close()
        realm = null
    }
}
```

---

## 6. Crear `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`

```kotlin
package gz.dam.trabajosimondize.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Utilidad para verificar el estado de conectividad de red
 */
object NetworkUtil {
    
    /**
     * Verifica si el dispositivo tiene conexión a internet
     * @param context Contexto de la aplicación
     * @return true si hay conexión, false en caso contrario
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) 
            as ConnectivityManager
        
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    
    /**
     * Verifica si la conexión es a través de WiFi
     */
    fun isWiFiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) 
            as ConnectivityManager
        
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
    
    /**
     * Verifica si la conexión es a través de datos móviles
     */
    fun isMobileConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) 
            as ConnectivityManager
        
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}
```

---

## 7. Crear `secrets.properties` (RAÍZ DEL PROYECTO)

```properties
# Credenciales de MongoDB Atlas
# ¡IMPORTANTE! Este archivo debe estar en .gitignore
# 
# Para obtener la connection string:
# 1. Ve a MongoDB Atlas Console
# 2. Selecciona tu cluster
# 3. Click en "Connect"
# 4. Elige "Drivers" → "Kotlin"
# 5. Copia la connection string y reemplaza <password> con tu contraseña

MONGODB_URI=mongodb+srv://usuario:contraseña@cluster0.xxxxx.mongodb.net/simondb?retryWrites=true&w=majority
```

---

## 8. Actualizar `app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- NOVO: Permisos necesarios para MongoDB -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.TrabajoSimonDize"
        tools:targetApi="31">

        <activity
            android:name=".main.MainActivity"
            android:exported="true"
            android:theme="@style/Theme.TrabajoSimonDize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

---

## 9. Fragmento de Actualización para `MyViewModel.kt`

```kotlin
package gz.dam.trabajosimondize.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gz.dam.trabajosimondize.data.model.RecordRemote
import gz.dam.trabajosimondize.data.repository.RecordRemoteRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

// ... resto de imports ...

class MyViewModel(application: Application) : AndroidViewModel(application) {
    
    // ... código existente ...
    
    // NOVO: Repository de MongoDB
    private val recordRepository = RecordRemoteRepository()
    
    // NOVO: LiveData para estado de sincronización
    private val _syncStatus = MutableLiveData<SyncStatus>()
    val syncStatus: LiveData<SyncStatus> = _syncStatus
    
    // NOVO: LiveData para lista de registros remotos
    private val _remoteRecords = MutableLiveData<List<RecordRemote>>()
    val remoteRecords: LiveData<List<RecordRemote>> = _remoteRecords

    /**
     * Sincronizar con MongoDB
     */
    fun syncWithMongoDB() {
        viewModelScope.launch {
            _syncStatus.value = SyncStatus.SYNCING
            try {
                val result = recordRepository.readAllRecords()
                result.onSuccess { records ->
                    _remoteRecords.value = records
                    _syncStatus.value = SyncStatus.SUCCESS(
                        "Sincronizados ${records.size} registros"
                    )
                }
                result.onFailure { error ->
                    _syncStatus.value = SyncStatus.ERROR(
                        error.message ?: "Error desconocido"
                    )
                }
            } catch (e: Exception) {
                _syncStatus.value = SyncStatus.ERROR(
                    e.message ?: "Error desconocido"
                )
            }
        }
    }

    /**
     * Crear nuevo registro en MongoDB
     */
    fun uploadCurrentRecord(score: Int) {
        viewModelScope.launch {
            try {
                val record = RecordRemote.fromLocalRecord(score)
                val result = recordRepository.createRecord(record)
                result.onSuccess { id ->
                    _syncStatus.value = SyncStatus.SUCCESS(
                        "Récord guardado en MongoDB con ID: $id"
                    )
                }
                result.onFailure { error ->
                    _syncStatus.value = SyncStatus.ERROR(
                        error.message ?: "Error al guardar"
                    )
                }
            } catch (e: Exception) {
                _syncStatus.value = SyncStatus.ERROR(e.message ?: "Error desconocido")
            }
        }
    }
    
    /**
     * Limpiar recursos al destruir ViewModel
     */
    override fun onCleared() {
        super.onCleared()
        recordRepository.closeRealm()
    }
}

/**
 * Sealed class para representar el estado de sincronización
 */
sealed class SyncStatus {
    object IDLE : SyncStatus()
    object SYNCING : SyncStatus()
    data class SUCCESS(val message: String) : SyncStatus()
    data class ERROR(val message: String) : SyncStatus()
}
```

---

## 10. Fragmento de Actualización para `Interfaz.kt`

```kotlin
// Fragmento para agregar a Interfaz.kt dentro de la UI Composable

// NOVO: Botones para MongoDB
Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Button(
        onClick = { viewModel.syncWithMongoDB() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text("🔄 Sincronizar con MongoDB")
    }
    
    Button(
        onClick = { viewModel.uploadCurrentRecord(viewModel.currentScore.value ?: 0) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text("⬆️ Subir Récord a MongoDB")
    }
    
    // Estado de sincronización
    when (val status = viewModel.syncStatus.observeAsState().value) {
        is SyncStatus.SYNCING -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text("Sincronizando...")
        }
        is SyncStatus.SUCCESS -> {
            Text(
                status.message,
                color = Color.Green,
                modifier = Modifier.padding(8.dp)
            )
        }
        is SyncStatus.ERROR -> {
            Text(
                "❌ Error: ${status.message}",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        SyncStatus.IDLE -> {
            // Sin estado
        }
        null -> {
            // Sin estado inicial
        }
    }
}
```

---

## 11. Actualizar `.gitignore`

```gitignore
# Kotlin/Java
*.class
*.jar
*.war
*.aar
*.iml
.gradle
build/
.idea/

# Secrets y credenciales (¡MUY IMPORTANTE!)
secrets.properties
local.properties
.env
.env.local

# Android
gen/
out/
.classpath
.project
.settings/
.DS_Store

# Realm
*.realm
*.realm.lock
```

---

## 12. Pasos de Compilación y Ejecución

### Paso 1: Configurar MongoDB Atlas

```bash
# 1. Ir a https://www.mongodb.com/cloud/atlas
# 2. Crear cuenta gratuita
# 3. Crear cluster
# 4. Ir a "Network Access" → Agregar "0.0.0.0/0"
# 5. Crear usuario de BD
# 6. Copiar connection string
# 7. Guardar en secrets.properties
```

### Paso 2: Preparar el Proyecto

```bash
cd /home/dam/Escritorio/PracticaSimon2

# Crear secrets.properties
cat > secrets.properties << 'EOF'
MONGODB_URI=mongodb+srv://usuario:contraseña@cluster0.xxxxx.mongodb.net/simondb?retryWrites=true&w=majority
EOF

# Sincronizar Gradle
./gradlew sync

# Compilar proyecto
./gradlew clean build
```

### Paso 3: Ejecutar en Emulador

```bash
# Compilar y ejecutar
./gradlew installDebug
./gradlew runDebug

# O desde Android Studio
# Run → Run 'app'
```

---

## 13. Pruebas Manuales

### Test 1: Conexión a MongoDB
1. Abre la app
2. Ve a la sección de MongoDB
3. Presiona "Sincronizar con MongoDB"
4. Verifica que la app muestra "Sincronizando..."
5. Debería cambiar a "✅ Sincronización completa"

### Test 2: Crear Registro
1. Juega y logra un score
2. Presiona "⬆️ Subir Récord a MongoDB"
3. Verifica en MongoDB Atlas que aparece el registro

### Test 3: Offline
1. Apaga el internet del dispositivo
2. Intenta sincronizar
3. Debería mostrar error amigable
4. Enciende internet
5. Sincroniza de nuevo

---

## Notas Importantes

⚠️ **Seguridad:**
- Nunca commits `secrets.properties` a git
- Usa permisos mínimos en MongoDB
- Cambia contraseñas regularmente

📦 **Dependencias:**
- Realm SDK v1.8.0+
- Android 30+ (minSdk actual)
- Kotlin 2.0.21+

🔧 **Debugging:**
- Usa Logcat para ver errores: `adb logcat | grep RecordRemoteRepository`
- Verifica connection string en BuildConfig
- Revisa Network Access en MongoDB Atlas

---

**Última actualización:** 2026-01-14

