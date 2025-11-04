package gz.dam.trabajosimondize

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

}