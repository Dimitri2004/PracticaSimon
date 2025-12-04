package gz.dam.trabajosimondize

import android.app.Application
import android.content.Context
import android.os.Looper
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.logging.Handler


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG_LOG: String = "miDebug"


    // Estado del juego
    val errorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val estadoLiveData: MutableLiveData<Estado> = MutableLiveData(Estado.INICIO)
    private val secuenciaColor: MutableList<String> = mutableListOf<String>()

    val nombreColores: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>(secuenciaColor)

    // Secuencia de colores (0=Rojo,1=Verde,2=Azul,3=Amarillo)
    private val secuencia = mutableListOf<Int>()
    private var indiceJugador : MutableLiveData<Int> = MutableLiveData(0)
    // Lista fija de nombres de colores por índice (0=Rojo,1=Verde,2=Azul,3=Amarillo)
    val colores: MutableList<String> =mutableListOf("rojo","verde","azul","amarillo")

    // Puntuación
    val puntuacion: MutableLiveData<Int> = MutableLiveData(0)

    // Botón activo para animación
    val botonActivo: MutableLiveData<Int> = MutableLiveData(-1)

    val ronda = MutableLiveData(0)

    val record = MutableStateFlow(0)

    var data = Date()

    init {
        record.value=obtenerRecord()
    }
    // Genera un nuevo color y muestra la secuencia
    fun crearRandom() {
        estadoLiveData.value = Estado.GENERANDO
        ronda.value = 1
        secuencia.clear()
        puntuacion.value = 0
        // Generar color aleatorio y agregar a la secuencia
        val nuevo = (0..3).random()
        secuencia.add(nuevo)
        indiceJugador.value= 0
        mostrarSecuencia()
        Log.d(TAG_LOG, "Estado: ${estadoLiveData.value}")

    }

    fun mostrarSecuencia() {
        CoroutineScope(Dispatchers.Main).launch {
            for (color in secuencia) {
                botonActivo.value = color
                delay(200)  // Duración del color activo
                botonActivo.value = -1
                delay(200)  // Pausa entre colores
            }
            delay(200)
            for (color in secuencia) {
                secuenciaColor.add(colores[color])
            }
            Log.d(TAG_LOG, "Generada secuencia Secuencia:${nombreColores.value}")

            estadoLiveData.value = Estado.SIGUIENDO
        }

    }
    fun generarSiguienteRonda() {
        estadoLiveData.value= Estado.SIGUIENDO
        secuenciaColor.clear()
        ronda.value = (ronda.value ?: 1) + 1
        val nuevo = (0..3).random()      // generamos un nuevo color ale
        secuencia.add(nuevo) // lo añadimos a la secuencia
        indiceJugador.value= 0          // reiniciamos el índice del jugador
        Log.d( TAG_LOG, "Nueva secuencia creada. Estado: ${estadoLiveData.value}")
        mostrarSecuencia()              // mostramos la secuencia actualizada
    }
    fun reiniciarJuego() {
        // Reiniciar todas las variables del juego
        secuencia.clear()
        indiceJugador.value = 0
        puntuacion.value = 0
        ronda.value = 1
        estadoLiveData.value = Estado.INICIO
        Log.d( TAG_LOG, "Juego reiniciado. Estado :${estadoLiveData.value}" )
    }
    fun comprobar(ordinal: Int) {

//        estadosAuxiliares()
        secuenciaColor.clear()
        if (estadoLiveData.value != Estado.SIGUIENDO) return
        var indiceJugador = indiceJugador.value ?: 0
        if (secuencia[indiceJugador] == ordinal) {
            indiceJugador= indiceJugador + 1
            this.indiceJugador.value= indiceJugador
            if (indiceJugador == secuencia.size) {
                estadoLiveData.value= Estado.ADIVINANDO
                // Secuencia completa correcta
                puntuacion.value = (puntuacion.value ?: 0) + 1
                // Generar siguiente ronda automáticamente
                Log.d(TAG_LOG, "Secuencia acertada. ESTADO: ${estadoLiveData.value} Puntuacion: ${puntuacion.value}")
                generarSiguienteRonda()
            }
        } else {
            estadoLiveData.value= Estado.INICIO
            Log.d(TAG_LOG, "Secuencia incorrecta. Estado:${estadoLiveData.value}")
            errorLiveData.value=true
            reiniciarJuego()
        }
    }

    fun esRecord(){
        if (puntuacion.value > obtenerRecord()){
            Log.d("DataP", "Hola $data")
            ControladorPreference.actualizarRecord(getApplication(),puntuacion.value,data)
            record.value = puntuacion.value
            Log.d("DataP", "NUEVA"+ControladorPreference.obtenerRecord(getApplication()).toString())
        }
    }

    fun obtenerRecord():Int{
        record.value = ControladorPreference.obtenerRecord(getApplication()).valorRecord
        return record.value
    }


}