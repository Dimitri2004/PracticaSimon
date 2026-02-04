package gz.dam.trabajosimondize.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.application
import gz.dam.trabajosimondize.data.controller.ControllerRoomSQLite
import gz.dam.trabajosimondize.data.record.Record
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG_LOG: String = "miDebug"

    val controllerSQLite = ControllerRoomSQLite(application)

    val errorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val estadoLiveData: MutableLiveData<Estado> = MutableLiveData(Estado.INICIO)
    private val secuenciaColor: MutableList<String> = mutableListOf<String>()
    val nombreColores: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>(secuenciaColor)
    val puntuacion: MutableLiveData<Int> = MutableLiveData(0)
    val botonActivo: MutableLiveData<Int> = MutableLiveData(-1)
    val ronda = MutableLiveData(0)

    var _record: Record = controllerSQLite.getRecord(getApplication()) // Variable para guardar el record actual mediante el controlador
    var _recordFecha: LocalDateTime = _record.recordFeha // Variable para guardar la fecha del record actual mediante el controlador
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") //Formato de texto en el que se guarda la fecha

    val record = MutableStateFlow<Int>(_record.recordPun) // Variable para guardar la puntuación del record
    val recordData = MutableStateFlow<String>(_recordFecha.format(formatter)) // Variable para guardar la fecha del record


    private val secuencia = mutableListOf<Int>()
    private var indiceJugador : MutableLiveData<Int> = MutableLiveData(0)
    private val colores: List<String> = listOf("rojo","verde","azul","amarillo")

    init {
        record.value = _record.recordPun
        recordData.value = _recordFecha.format(formatter)
    }

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

    fun mostrarSecuencia() {
        CoroutineScope(Dispatchers.Main).launch {
            for (color in secuencia) {
                botonActivo.value = color
                delay(200)
                botonActivo.value = -1
                delay(200)
            }
            delay(200)
            secuenciaColor.clear()
            for (color in secuencia) {
                secuenciaColor.add(colores[color])
            }
            Log.d(TAG_LOG, "Generada secuencia Secuencia:${nombreColores.value}")
            estadoLiveData.value = Estado.SIGUIENDO
        }
    }

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

    fun reiniciarJuego() {

        _record = controllerSQLite.getRecord(application)
        secuencia.clear()
        indiceJugador.value = 0
        puntuacion.value = 0
        ronda.value = 1
        estadoLiveData.value = Estado.INICIO
        Log.d(TAG_LOG, "Juego reiniciado. Estado :${estadoLiveData.value}")
    }

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
            actualizarRecord()
            reiniciarJuego()
        }
    }

    /**
     * Función que actualiza los datos del record de la viewModel y luego los manda actualizar en la clase Datos.
     */
    fun actualizarRecord(){
        record.value = puntuacion.value
        controllerSQLite.setRecord(record.value, LocalDateTime.now(),getApplication())
        _record = controllerSQLite.getRecord(getApplication())
        _recordFecha = _record.recordFeha
        recordData.value = _recordFecha.format(formatter)
    }
}