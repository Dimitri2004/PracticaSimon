package gz.dam.trabajosimondize

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyViewModel() : ViewModel() {
    // Aquí iría la implementación del ViewModel
    private val TAG_LOG = "miDebug"

    // Estado del juego

    val estadoLiveData: MutableLiveData<Estado> = MutableLiveData(Estado.INICIO)

    // Secuencia de colores (0=Rojo,1=Verde,2=Azul,3=Amarillo)
    private val secuencia = mutableListOf<Int>()
    private var indiceJugador : MutableLiveData<Int> = MutableLiveData(0)

    // Puntuación
    val puntuacion: MutableLiveData<Int> = MutableLiveData(0)

    // Botón activo para animación
    val botonActivo: MutableLiveData<Int> = MutableLiveData(-1)

    val ronda: MutableLiveData<Int> = MutableLiveData(0)

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
        Log.d(TAG_LOG, "Nueva secuencia: $secuencia")
    }

    // Muestra la secuencia al jugador
    fun mostrarSecuencia() {
        CoroutineScope(Dispatchers.Main).launch {
            for (color in secuencia) {
                botonActivo.value = color
                delay(200)  // Duración del color activo
                botonActivo.value = -1
                delay(150)  // Pausa entre colores
            }
            estadoLiveData.value = Estado.SIGUIENDO
        }

    }


}