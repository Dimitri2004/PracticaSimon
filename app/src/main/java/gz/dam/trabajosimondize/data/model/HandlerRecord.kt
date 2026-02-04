package gz.dam.trabajosimondize.data.model

import android.content.Context
import gz.dam.trabajosimondize.data.Utility.Record
import java.time.LocalDateTime


interface HandlerRecord {
    /**
     * Guarda los datos del record en donde sean persistentes
     * @param valorRecord puntuación del record
     * @param fechaRecord fecha en la que fue hecho el record
     * @return devuelve 1 si fue bien, cualquier otro número mal
     */
    fun setRecord(valorRecord: Int, fechaRecord: LocalDateTime, nombreRecord: String, context: Context) : Int

    /**
     * Función que devuelve el record desde donde se haya guardado
     * @return objeto Record
     */
    fun getRecord(context: Context) : Record
}