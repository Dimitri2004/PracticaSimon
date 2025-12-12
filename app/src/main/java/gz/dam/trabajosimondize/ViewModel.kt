package gz.dam.trabajosimondize

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import gz.dam.trabajosimondize.BaseDatos.ControladorBaseDatos
import gz.dam.trabajosimondize.data.repository.InterfazRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel principal del juego Simon.
 *
 * Esta clase extiende [AndroidViewModel] para poder acceder al contexto de la aplicación
 * de forma segura, necesario para interactuar con el repositorio de datos.
 *
 * Gestiona todo el estado y la lógica del juego, comunicándose con la interfaz de usuario
 * a través de objetos observables como [MutableLiveData] y [MutableStateFlow].
 */
class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG_LOG: String = "miDebug"

    // --- Variables de Estado Observables por la UI ---

    /** Indica si se ha producido un error (p. ej., secuencia incorrecta), para que la UI reaccione. */
    val errorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    /** Representa el estado actual del juego (INICIO, GENERANDO, SIGUIENDO, etc.), crucial para la lógica de la UI. */
    val estadoLiveData: MutableLiveData<Estado> = MutableLiveData(Estado.INICIO)

    /** Mantiene la secuencia de colores representada como texto (usado principalmente para debugging). */
    private val secuenciaColor: MutableList<String> = mutableListOf<String>()
    val nombreColores: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>(secuenciaColor)

    /** La puntuación actual de la partida. */
    val puntuacion: MutableLiveData<Int> = MutableLiveData(0)

    /** El índice del botón que debe mostrarse como activo (para la animación de la secuencia). -1 si ninguno. */
    val botonActivo: MutableLiveData<Int> = MutableLiveData(-1)

    /** El número de la ronda actual. */
    val ronda = MutableLiveData(0)

    /** El récord de puntuación más alta, como un StateFlow para ser observado por la UI de Compose. */
    val record = MutableStateFlow(0)

    // --- Variables Internas de Lógica ---

    private val secuencia = mutableListOf<Int>() // La secuencia numérica de colores generada.
    private var indiceJugador : MutableLiveData<Int> = MutableLiveData(0) // El índice actual del jugador en la secuencia.
    private val colores: List<String> = listOf("rojo","verde","azul","amarillo")

    // El repositorio de datos. Gracias a la interfaz, podemos cambiar fácilmente
    // entre SharedPreferences y la Base de Datos sin tocar el resto del ViewModel.
    private val recordRepository: InterfazRecord = ControladorBaseDatos(application)

    init {
        // Al crear el ViewModel, se carga el récord guardado.
        record.value = obtenerRecord()
    }

    /**
     * Inicia una nueva partida o la reinicia desde cero.
     * Limpia la secuencia, resetea la puntuación y genera el primer color.
     */
    fun crearRandom() {
        estadoLiveData.value = Estado.GENERANDO
        ronda.value = 1
        secuencia.clear()
        puntuacion.value = 0
        val nuevo = (0..3).random()
        secuencia.add(nuevo)
        indiceJugador.value = 0
        mostrarSecuencia()
        Log.d(TAG_LOG, "Estado: ${estadoLiveData.value}")
    }

    /**
     * Muestra la secuencia de colores actual al jugador, activando los botones visualmente.
     * Es una corrutina que se ejecuta en el hilo principal para poder actualizar la UI.
     */
    fun mostrarSecuencia() {
        CoroutineScope(Dispatchers.Main).launch {
            for (color in secuencia) {
                botonActivo.value = color
                delay(200)
                botonActivo.value = -1
                delay(200)
            }
            delay(200)
            for (color in secuencia) {
                secuenciaColor.add(colores[color])
            }
            Log.d(TAG_LOG, "Generada secuencia Secuencia:${nombreColores.value}")
            estadoLiveData.value = Estado.SIGUIENDO
        }
    }

    /**
     * Añade un nuevo color a la secuencia y la muestra al jugador.
     * Se llama cuando el jugador completa una ronda con éxito.
     */
    fun generarSiguienteRonda() {
        estadoLiveData.value = Estado.GENERANDO
        secuenciaColor.clear()
        ronda.value = (ronda.value ?: 1) + 1
        val nuevo = (0..3).random()
        secuencia.add(nuevo)
        indiceJugador.value = 0
        Log.d(TAG_LOG, "Nueva secuencia creada. Estado: ${estadoLiveData.value}")
        mostrarSecuencia()
    }

    /**
     * Reinicia el estado del juego. Primero guarda la puntuación de la partida y comprueba
     * si hay un nuevo récord. Luego resetea todas las variables a sus valores iniciales.
     */
    fun reiniciarJuego() {
        guardarPuntuacionPartida()
        secuencia.clear()
        indiceJugador.value = 0
        puntuacion.value = 0
        ronda.value = 1
        estadoLiveData.value = Estado.INICIO
        Log.d(TAG_LOG, "Juego reiniciado. Estado :${estadoLiveData.value}")
    }

    /**
     * Comprueba si el botón pulsado por el jugador (`ordinal`) coincide con el
     * color correspondiente en la secuencia generada.
     *
     * @param ordinal El índice del color pulsado por el jugador.
     */
    fun comprobar(ordinal: Int) {
        if (estadoLiveData.value != Estado.SIGUIENDO) return

        secuenciaColor.clear()
        val indiceActual = indiceJugador.value ?: 0
        if (secuencia[indiceActual] == ordinal) {
            val nuevoIndice = indiceActual + 1
            indiceJugador.value = nuevoIndice
            if (nuevoIndice == secuencia.size) {
                puntuacion.value = (puntuacion.value ?: 0) + 1
                estadoLiveData.value = Estado.SECUENCIA_ACERTADA
                Log.d(TAG_LOG, "Secuencia acertada. Puntuacion: ${puntuacion.value}")
            }
        } else {
            estadoLiveData.value = Estado.GAMEOVER
            Log.d(TAG_LOG, "Secuencia incorrecta. Estado:${estadoLiveData.value}")
            errorLiveData.value = true
            reiniciarJuego()
        }
    }

    /**
     * Guarda la puntuación de la partida actual en el repositorio y actualiza el récord si se ha superado.
     */
    private fun guardarPuntuacionPartida() {
        val puntuacionActual = puntuacion.value ?: 0
        // Guardamos la puntuación de la partida actual en la base de datos.
        recordRepository.actualizarRecord(getApplication(), puntuacionActual, Date())

        // Comprobamos si esta puntuación supera el récord actual y actualizamos la UI.
        if (puntuacionActual > record.value) {
            record.value = puntuacionActual
            Log.d("DataP", "¡Nuevo Récord!: $puntuacionActual")
        }
    }

    /**
     * Obtiene el récord guardado desde el repositorio de datos.
     *
     * @return La puntuación del récord.
     */
    private fun obtenerRecord(): Int {
        val recordGuardado = recordRepository.obtenerRecord(getApplication())
        record.value = recordGuardado.valorRecord
        return recordGuardado.valorRecord
    }
}
