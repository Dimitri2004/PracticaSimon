package gz.dam.trabajosimondize

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import gz.dam.trabajosimondize.data.repository.ControladorPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date


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
        esRecord() // Comprobamos si hay nuevo récord antes de reiniciar
        // Reiniciar todas las variables del juego
        secuencia.clear()
        indiceJugador.value = 0
        puntuacion.value = 0
        ronda.value = 1
        estadoLiveData.value = Estado.INICIO
        Log.d( TAG_LOG, "Juego reiniciado. Estado :${estadoLiveData.value}" )
    }
    fun comprobar(ordinal: Int) {
        if (estadoLiveData.value != Estado.SIGUIENDO) return

        secuenciaColor.clear()
        val indiceActual = indiceJugador.value ?: 0
        if (secuencia[indiceActual] == ordinal) {
            val nuevoIndice = indiceActual + 1
            indiceJugador.value = nuevoIndice
            if (nuevoIndice == secuencia.size) {
                // Secuencia completa correcta
                puntuacion.value = (puntuacion.value ?: 0) + 1
                estadoLiveData.value = Estado.SECUENCIA_ACERTADA // <- CAMBIO AQUÍ
                Log.d(TAG_LOG, "Secuencia acertada. Puntuacion: ${puntuacion.value}")
            }
        } else {
            estadoLiveData.value= Estado.GAMEOVER
            Log.d(TAG_LOG, "Secuencia incorrecta. Estado:${estadoLiveData.value}")
            errorLiveData.value=true
            reiniciarJuego()
        }
    }

    fun esRecord(){
        if ((puntuacion.value ?: 0) > obtenerRecord()){
            Log.d("DataP", "Hola $data")
            ControladorPreference.actualizarRecord(getApplication(),puntuacion.value ?: 0,data)
            record.value = puntuacion.value ?: 0
            Log.d("DataP", "NUEVA"+ControladorPreference.obtenerRecord(getApplication()).toString())
        }
    }

    fun obtenerRecord():Int{
        record.value = ControladorPreference.obtenerRecord(getApplication()).valorRecord
        return record.value
    }


}