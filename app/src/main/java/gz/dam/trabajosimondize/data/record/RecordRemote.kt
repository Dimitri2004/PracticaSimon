package gz.dam.trabajosimondize.data.record

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

/**
 * Modelo de datos para representar un récord almacenado en MongoDB.
 * Esta clase se serializa/deserializa con Gson para comunicarse con la API de MongoDB.
 *
 * @param id Identificador único del récord en MongoDB (generado por MongoDB)
 * @param puntuacion La puntuación obtenida en la partida
 * @param fecha La fecha y hora en la que se consiguió el récord
 * @param sincronizado Indicador de si el récord ha sido sincronizado con MongoDB
 * @param fechaCreacion Timestamp de creación en el cliente
 * @param fechaModificacion Timestamp de última modificación
 */
data class RecordRemote(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("puntuacion")
    val puntuacion: Int = 0,

    @SerializedName("fecha")
    val fecha: String = "", // Guardado como String en formato "dd/MM/yyyy HH:mm:ss"

    @SerializedName("sincronizado")
    val sincronizado: Boolean = false,

    @SerializedName("fechaCreacion")
    val fechaCreacion: Long = System.currentTimeMillis(),

    @SerializedName("fechaModificacion")
    val fechaModificacion: Long = System.currentTimeMillis()
) {
    /**
     * Constructor alternativo para facilitar la creación desde otros modelos
     */
    constructor(
        puntuacion: Int,
        fecha: String
    ) : this(
        id = null,
        puntuacion = puntuacion,
        fecha = fecha,
        sincronizado = false,
        fechaCreacion = System.currentTimeMillis(),
        fechaModificacion = System.currentTimeMillis()
    )
}

