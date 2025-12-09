package gz.dam.trabajosimondize.data.repository

import android.content.Context
import gz.dam.trabajosimondize.data.model.Record
import java.util.Date

/**
 * Define el contrato que debe seguir cualquier clase que gestione el almacenamiento
 * y la recuperación de los récords del juego.
 *
 * Esta interfaz permite desacoplar la lógica de la aplicación de la implementación
 * específica de guardado de datos (p. ej., SharedPreferences, base de datos, etc.).
 */
interface InterfazRecord {

    /**
     * Obtiene el récord actual guardado.
     *
     * @param context El contexto de la aplicación, necesario para acceder al almacenamiento.
     * @return Un objeto [Record] que contiene la puntuación y la fecha del récord.
     */
    fun obtenerRecord(context: Context): Record

    /**
     * Actualiza el récord si se ha superado la puntuación máxima.
     *
     * @param context El contexto de la aplicación.
     * @param nuevoRecord La nueva puntuación obtenida en la partida.
     * @param dataActual La fecha en la que se consiguió el nuevo récord.
     * @return Un entero que indica el resultado de la operación (p. ej., 1 si fue exitosa).
     */
    fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int
}
