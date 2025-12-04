package gz.dam.trabajosimondize

import android.content.Context
import androidx.core.content.edit
import java.util.Date

object ControladorPreference : InterfazRecord{
    // definimos el nombre del fichero de preferencias
    private const val PREFS_NAME = "preferencias_app_Nuevas"
    // definimos la clave del record (guardamos key:value)
    private const val KEY_RECORD = "record"

    private const val KEY_DATA = "data"

    /**
     * Actualiza el record en las preferencias compartidas.
     * @param context Contexto de la aplicación.
     * @param nuevoRecord Nuevo record a guardar.
     */
    override fun obtenerRecord(context: Context): Record {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val rec = sharedPreferences.getInt(KEY_RECORD,0)
        val dat = sharedPreferences.getString(KEY_DATA,"Wed Dec 03 13:27:11 GMT+01:00 2025")
        Record.valorRecord = rec
        Record.fechaSuperacion = Date(dat)
        return Record
    }


    /**
     * Permite actualizar el record si se supera
     */
    override fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int {
        val sharedPreferences  = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val dataString = dataActual.toString()
        sharedPreferences.edit{
            putInt(KEY_RECORD,nuevoRecord)
            putString(KEY_DATA,dataString)
        }
        return 1
    }
}