---
goal: Guía Rápida - Primeros Pasos para Integración de MongoDB
version: 1.0
date_created: 2026-01-14
owner: Dimitri2004
status: 'Planned'
---

# Guía Rápida: Integración de MongoDB en PracticaSimon

## 📋 Checklist de Pasos Inmediatos

### Semana 1: Configuración Inicial

#### ✅ Paso 1: Configurar MongoDB Atlas (1-2 horas)
```
1. Accede a https://www.mongodb.com/cloud/atlas
2. Crea unha conta (se non a tes)
3. Crea un proxecto novo
4. Crea un cluster (opción Free é suficiente)
5. Configura Network Access:
   - Vai a "Network Access" na sidebar
   - Engade "0.0.0.0/0" (acceso desde calquera IP)
   - Crea usuario con credenciais seguras
6. Obtén a connection string:
   - Vai a "Database" → "Connect"
   - Escolle "Drivers" → "Kotlin"
   - Copia a connection string (algo así):
     mongodb+srv://usuario:contraseña@cluster.mongodb.net/database?retryWrites=true&w=majority
```

#### ✅ Paso 2: Almacenar Credenciais Seguramente (30 minutos)
```
1. Na raíz do proxecto, crea: secrets.properties
2. Engade:
   MONGODB_URI=mongodb+srv://usuario:contraseña@cluster.mongodb.net/database?retryWrites=true&w=majority

3. En .gitignore, engade:
   secrets.properties

4. En app/build.gradle.kts, engade:
   def secretsFile = rootProject.file('secrets.properties')
   def secrets = new Properties()
   if (secretsFile.exists()) {
       secrets.load(new FileInputStream(secretsFile))
   }
   
   buildConfigField "String", "MONGODB_URI", "\"${secrets.getProperty('MONGODB_URI', '')}\""
```

#### ✅ Paso 3: Engadir Dependencias (1 hora)
```gradle
// gradle/libs.versions.toml
[versions]
mongodb-realm = "1.8.0"
androidx-work = "2.8.1"

[libraries]
mongodb-realm = { group = "io.realm.kotlin", name = "library-sync", version.ref = "mongodb-realm" }
androidx-work-runtime = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "androidx-work" }

// app/build.gradle.kts
plugins {
    // ...
    id("io.realm.kotlin") version "1.8.0"
}

dependencies {
    implementation(libs.mongodb-realm)
    implementation(libs.androidx-work-runtime)
    // ... resto de dependencias
}
```

#### ✅ Paso 4: Crear Estructura de Directorios (30 minutos)
```bash
mkdir -p app/src/main/java/gz/dam/trabajosimondize/data/repository
mkdir -p app/src/main/java/gz/dam/trabajosimondize/data/sync
mkdir -p app/src/main/java/gz/dam/trabajosimondize/util
```

---

### Semana 2-3: Implementación da Capa de Datos

#### ✅ Paso 5: Crear Modelo de Datos para MongoDB (1 hora)
**Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/model/RecordRemote.kt`

```kotlin
package gz.dam.trabajosimondize.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

class RecordRemote : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var score: Int = 0
    var fecha: String = LocalDateTime.now().toString()
    var userId: String = "" // Para multiplos usuarios no futuro
    var syncedAt: Long = System.currentTimeMillis()
}
```

#### ✅ Paso 6: Crear Interface Repository (1 hora)
**Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/repository/IRecordRepository.kt`

```kotlin
package gz.dam.trabajosimondize.data.repository

import gz.dam.trabajosimondize.data.model.RecordRemote

interface IRecordRepository {
    suspend fun createRecord(record: RecordRemote): Result<String>
    suspend fun readAllRecords(): Result<List<RecordRemote>>
    suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit>
    suspend fun deleteRecord(id: String): Result<Unit>
}
```

#### ✅ Paso 7: Implementar RecordRemoteRepository (2-3 horas)
**Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepository.kt`

```kotlin
package gz.dam.trabajosimondize.data.repository

import android.content.Context
import gz.dam.trabajosimondize.data.model.RecordRemote
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecordRemoteRepository(private val context: Context) : IRecordRepository {
    private val realm: Realm = getRealm()
    
    private fun getRealm(): Realm {
        // Aquí se configuraría Realm con MongoDB Sync
        // De momento, usamos Realm local para ilustrar
        return Realm.open()
    }

    override suspend fun createRecord(record: RecordRemote): Result<String> = 
        withContext(Dispatchers.IO) {
            try {
                var recordId = ""
                realm.write {
                    val newRecord = copyToRealm(record)
                    recordId = newRecord._id.toHexString()
                }
                Result.success(recordId)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun readAllRecords(): Result<List<RecordRemote>> = 
        withContext(Dispatchers.IO) {
            try {
                val records = realm.query<RecordRemote>().find()
                Result.success(records)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun updateRecord(id: String, record: RecordRemote): Result<Unit> = 
        withContext(Dispatchers.IO) {
            try {
                realm.write {
                    val existing = query<RecordRemote>("_id == $0", id).first.find()
                    if (existing != null) {
                        existing.score = record.score
                        existing.fecha = record.fecha
                        existing.syncedAt = System.currentTimeMillis()
                    }
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun deleteRecord(id: String): Result<Unit> = 
        withContext(Dispatchers.IO) {
            try {
                realm.write {
                    val toDelete = query<RecordRemote>("_id == $0", id).first.find()
                    toDelete?.let { delete(it) }
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
```

#### ✅ Paso 8: Crear Utilidade de Rede (1 hora)
**Arquivo:** `app/src/main/java/gz/dam/trabajosimondize/util/NetworkUtil.kt`

```kotlin
package gz.dam.trabajosimondize.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) 
            as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
```

---

### Semana 3-4: Integración na UI

#### ✅ Paso 9: Actualizar MyViewModel.kt (2 horas)

```kotlin
// Engadir ao inicio da clase
private val recordRepository = RecordRemoteRepository(application)

// Engadir nuevas LiveData
private val _syncStatus = MutableLiveData<SyncStatus>()
val syncStatus: LiveData<SyncStatus> = _syncStatus

// Engadir métodos
fun syncWithMongoDB() {
    viewModelScope.launch {
        _syncStatus.value = SyncStatus.SYNCING
        try {
            val result = recordRepository.readAllRecords()
            result.onSuccess { records ->
                // Procesar récords descargados
                _syncStatus.value = SyncStatus.SUCCESS
            }
            result.onFailure { error ->
                _syncStatus.value = SyncStatus.ERROR(error.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            _syncStatus.value = SyncStatus.ERROR(e.message ?: "Unknown error")
        }
    }
}

fun uploadCurrentRecord(score: Int) {
    viewModelScope.launch {
        try {
            val record = RecordRemote().apply {
                this.score = score
                this.fecha = LocalDateTime.now().toString()
            }
            val result = recordRepository.createRecord(record)
            result.onSuccess {
                Log.d("MongoDB", "Récord gardado en MongoDB")
            }
        } catch (e: Exception) {
            Log.e("MongoDB", "Error ao gardar récord", e)
        }
    }
}

// Data class para estado de sincronización
sealed class SyncStatus {
    object IDLE : SyncStatus()
    object SYNCING : SyncStatus()
    object SUCCESS : SyncStatus()
    data class ERROR(val message: String) : SyncStatus()
}
```

#### ✅ Paso 10: Actualizar Interfaz.kt (1-2 horas)

Engadir botóns e indicadores visuais:

```kotlin
// Engadir botón de sincronización
Button(
    onClick = { viewModel.syncWithMongoDB() },
    modifier = Modifier.padding(16.dp)
) {
    Text("Sincronizar con MongoDB")
}

// Mostrar estado
when (val status = viewModel.syncStatus.observeAsState().value) {
    is SyncStatus.SYNCING -> {
        CircularProgressIndicator()
        Text("Sincronizando...")
    }
    is SyncStatus.SUCCESS -> {
        Text("✅ Sincronización completa", color = Color.Green)
    }
    is SyncStatus.ERROR -> {
        Text("❌ Error: ${status.message}", color = Color.Red)
    }
    else -> {}
}
```

#### ✅ Paso 11: Engadir Permisos (15 minutos)

**Arquivo:** `app/src/main/AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

### Semana 4-5: Probas

#### ✅ Paso 12: Tests Básicos (3 horas)

```kotlin
// app/src/test/java/gz/dam/trabajosimondize/data/repository/RecordRemoteRepositoryTest.kt

import junit.framework.TestCase.assertTrue
import org.junit.Test

class RecordRemoteRepositoryTest {
    
    @Test
    fun testCreateRecord() {
        // Implementar test de creación
    }
    
    @Test
    fun testReadAllRecords() {
        // Implementar test de lectura
    }
}
```

---

## 📊 Diagrama de Fluxo de Datos

```
┌─────────────────────────────────────────────────────────────────┐
│                        APLICACIÓN ANDROID                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────┐         ┌──────────────────┐                  │
│  │  MainActivity│◄───────►│   MyViewModel    │                  │
│  │  Interfaz.kt │         │   (Observer)     │                  │
│  └──────────────┘         └──────────────────┘                  │
│         △                          △                             │
│         │                          │                             │
│         └──────────┬───────────────┘                             │
│                    │                                             │
│         ┌──────────▼────────────┐                               │
│         │RecordSyncRepository   │                               │
│         │(Interface lógica)     │                               │
│         └──────────┬────────────┘                               │
│                    │                                             │
│        ┌───────────┴───────────┐                                │
│        │                       │                                │
│  ┌─────▼──────────┐    ┌──────▼────────────┐                   │
│  │ LOCAL STORAGE  │    │ REMOTE STORAGE    │                   │
│  │                │    │                   │                   │
│  │ ┌────────────┐ │    │ ┌─────────────┐   │                   │
│  │ │ Room/SQLite│ │    │ │MongoDB Atlas│   │                   │
│  │ │ (Offline)  │ │    │ │  (Cloud)    │   │                   │
│  │ └────────────┘ │    │ └─────────────┘   │                   │
│  └────────────────┘    └───────────────────┘                   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔒 Seguridade: Checklist

- ✅ Crear `secrets.properties` para credenciais
- ✅ Engadir `secrets.properties` a `.gitignore`
- ✅ Usar BuildConfig para acceder a credenciais
- ✅ Habilitar cifrado en tránsito (HTTPS automático en MongoDB)
- ✅ Crear usuario con permisos mínimos en MongoDB
- ✅ Validar datos antes de enviar á nube
- ✅ Implementar timeout para conexións

---

## 🛠️ Troubleshooting Común

### Problema: "Cannot connect to MongoDB"
**Solución:**
1. Verifica que a connection string é correcta en `secrets.properties`
2. Comprueba que Network Access en MongoDB Atlas permite conexións
3. Verifica que o dispositivo ten internet

### Problema: "Corrutina lanzada sin dispatcher"
**Solución:**
- Asegúrate de usar `viewModelScope.launch` en lugar de solo `launch`
- Especifica `Dispatchers.IO` para operacións de rede

### Problema: "RecordRemote is not a RealmObject"
**Solución:**
- Asegúrate de que hereda de `RealmObject`
- Compila co plugin de Realm engadido en gradle

---

## 📚 Recursos Útiles

| Recurso | Enlace |
|---------|--------|
| MongoDB Realm SDK Kotlin | https://www.mongodb.com/docs/realm/sdk/kotlin/ |
| MongoDB Atlas Console | https://cloud.mongodb.com/ |
| Android Room vs Realm | https://www.mongodb.com/docs/realm/sdk/kotlin/realm-vs-room/ |
| Kotlin Coroutines | https://kotlinlang.org/docs/coroutines-overview.html |

---

## Próximas Accións

1. **Semana 1:** Completar Pasos 1-4 (Configuración)
2. **Semana 2:** Completar Pasos 5-8 (Capa de Datos)
3. **Semana 3:** Completar Pasos 9-11 (UI + Permisos)
4. **Semana 4:** Completar Paso 12 (Probas)
5. **Semana 5:** Optimización e deployment

---

**Última actualización:** 2026-01-14  
**Estado:** Listo para comenzar 🚀

