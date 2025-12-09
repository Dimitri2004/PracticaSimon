package gz.dam.trabajosimondize

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * Composable principal que construye la interfaz de usuario del juego Simon.
 *
 * Esta función observa el estado del [MyViewModel] y recompone la UI en respuesta a los cambios.
 * Utiliza `LaunchedEffect` para manejar efectos secundarios controlados por el ciclo de vida, como
 * la lógica del juego y la reproducción de sonidos.
 *
 * @param miViewModel La instancia del [MyViewModel] que contiene el estado y la lógica del juego.
 */
@Composable
fun Interfaz(miViewModel: MyViewModel) {

    val estado by miViewModel.estadoLiveData.observeAsState(Estado.INICIO)
    val record by miViewModel.record.collectAsState()
    val botonActivo by miViewModel.botonActivo.observeAsState(-1)
    val error by miViewModel.errorLiveData.observeAsState(false)

    // Efecto que se lanza cuando el estado del juego cambia a SECUENCIA_ACERTADA.
    // Se encarga de esperar un segundo y generar la siguiente ronda.
    LaunchedEffect(estado) {
        if (estado == Estado.SECUENCIA_ACERTADA) { // <- REACCIONA AL NUEVO ESTADO
            delay(1000) // Pausa antes de la siguiente ronda
            miViewModel.generarSiguienteRonda()
        }
    }

    // Efecto que se lanza cuando un botón se activa en el ViewModel para reproducir un sonido.
    LaunchedEffect(botonActivo) {
        when (botonActivo) {
            0 -> reproducirTono(261.63, 150) // DO
            1 -> reproducirTono(293.66, 150) // RE
            2 -> reproducirTono(329.63, 150) // MI
            3 -> reproducirTono(349.23, 150) // FA
        }
        delay(50)
    }

    // Efecto que reproduce una secuencia de sonidos de error cuando el jugador se equivoca.
    LaunchedEffect(error) {
        if (error) {
            delay(150) // pequeño espacio antes de iniciar la secuencia

            try {
                val notas = listOf(293.66, 329.63, 349.23, 261.63)
                for (nota in notas) {
                    reproducirTono(nota, 200)
                    delay(300)
                }
            } finally {
                miViewModel.errorLiveData.value = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB9F6CA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Ronda(miViewModel)
        Spacer(Modifier.size(8.dp))
        Puntuacion(miViewModel)
        Spacer(Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column {
                Text(
                    text = "Record: $record"
                )
                Row {
                    BotonSimondize(miViewModel, Colores.CLASE_ROJO)
                    Spacer(Modifier.size(5.dp))
                    BotonSimondize(miViewModel, Colores.CLASE_VERDE)
                }
                Spacer(Modifier.size(5.dp))
                Row {
                    BotonSimondize(miViewModel, Colores.CLASE_AZUL)
                    Spacer(Modifier.size(5.dp))
                    BotonSimondize(miViewModel, Colores.CLASE_AMARILLO)
                }
            }
        }
        Box {
            Row {
                BotonStart( miViewModel)
            }
        }
    }
}

/**
 * Composable que representa cada uno de los botones de colores del Simon.
 *
 * @param miViewModel La instancia del ViewModel para comunicar la acción del usuario.
 * @param enum_color El objeto [Colores] que define las propiedades de este botón (color, etc.).
 */
@Composable
fun BotonSimondize(miViewModel: MyViewModel, enum_color: Colores) {
    val activo by miViewModel.botonActivo.observeAsState(-1)
    val isActive = activo == enum_color.ordinal
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                // Lanza una corrutina para reproducir el sonido sin bloquear el hilo principal.
                scope.launch {
                    when (enum_color.ordinal) {
                        0 -> reproducirTono(261.63, 150) // DO
                        1 -> reproducirTono(293.66, 150) // RE
                        2 -> reproducirTono(329.63, 150) // MI
                        3 -> reproducirTono(349.23, 150) // FA
                    }
                }
                // Notifica al ViewModel que el botón ha sido pulsado.
                miViewModel.comprobar(enum_color.ordinal)},
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isActive)
                    enum_color.color.copy(alpha = 0.4f) // Color más claro si está activo
                else enum_color.color
            ),
            modifier = Modifier
                .size(150.dp, 250.dp)
                .border(6.dp, Color.Black, RoundedCornerShape(6.dp))
                .padding(3.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = enum_color.txt, fontSize = 0.sp)
        }
    }
}

/**
 * Composable para el botón "Start" que inicia una nueva partida.
 *
 * @param miViewModel La instancia del ViewModel para iniciar el juego.
 */
@Composable
fun BotonStart(miViewModel: MyViewModel) {

    Button(
        onClick = {miViewModel.crearRandom()},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF14B8CC)
        ),
        modifier = Modifier
            .size(160.dp, 70.dp)
            .border(6.dp, Color.Black, RoundedCornerShape(6.dp))
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Start", fontSize = 20.sp)
    }
}

/**
 * Composable que muestra la puntuación actual de la partida.
 *
 * @param model La instancia del ViewModel de la que obtiene la puntuación.
 * @param modifier El modificador para aplicar a este Composable.
 */
@Composable
fun Puntuacion(model: MyViewModel, modifier: Modifier = Modifier) {
    val puntos by model.puntuacion.observeAsState(0)

    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .size(70.dp)
            .border(10.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Puntuación: $puntos",
            fontSize = 30.sp,
            color = Color.Black
        )

    }
}

/**
 * Genera y reproduce un tono de una frecuencia y duración específicas.
 *
 * Es una `suspend fun` que utiliza `delay` en lugar de `Thread.sleep` para evitar
 * bloquear el hilo principal, haciéndola segura para ser llamada desde corrutinas.
 *
 * @param frecuencia La frecuencia del tono a generar, en Hercios (Hz).
 * @param duracionMs La duración del tono, en milisegundos.
 * @param pausaMs Una pausa adicional al final para evitar la mezcla de sonidos.
 */
suspend fun reproducirTono(frecuencia: Double, duracionMs: Int, pausaMs: Long = 100) {
    val sampleRate = 44100
    val numSamples = (duracionMs / 1000.0 * sampleRate).toInt()
    val buffer = ShortArray(numSamples)

    // Generar la onda senoidal
    // La onda senoidal es una forma de onda que se repite periódicamente y la necesitamos para el tono.
    for (i in buffer.indices) {
        val angle = 2.0 * PI * i * frecuencia / sampleRate
        buffer[i] = (sin(angle) * Short.MAX_VALUE).toInt().toShort()
    }

    // Crear y configurar AudioTrack
    val audioTrack = AudioTrack(
        AudioManager.STREAM_MUSIC,
        sampleRate,
        AudioFormat.CHANNEL_OUT_MONO,
        AudioFormat.ENCODING_PCM_16BIT,
        buffer.size * 2,
        AudioTrack.MODE_STATIC
    )

    audioTrack.write(buffer, 0, buffer.size)
    audioTrack.play()

    // Pausa no bloqueante mientras suena el tono
    delay(duracionMs.toLong())

    audioTrack.stop()
    audioTrack.release()

    // Pausa adicional no bloqueante
    delay(pausaMs)
}

/**
 * Composable que muestra el número de la ronda actual.
 *
 * @param miViewModel La instancia del ViewModel de la que obtiene el número de ronda.
 */
@Composable
fun Ronda(miViewModel: MyViewModel) {
    val ronda by miViewModel.ronda.observeAsState(0)
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .size(70.dp)
            .border(10.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ronda: $ronda",
            fontSize = 30.sp,
            color = Color.Black
        )

    }
}

