package gz.dam.trabajosimondize.data.repository

import android.content.Context
import androidx.core.content.edit
import gz.dam.trabajosimondize.data.model.Record
import java.util.Date

//Es un singleton,solo una unica instancia del gestor ,ya que no necesita a varios gestores haciendo lo mismo
object ControladorPreference : InterfazRecord {
    // definimos el nombre del fichero de preferencias
    private const val PREFS_NAME = "preferencias_app_Nuevas"
    // definimos la clave del record (guardamos key:value)
    private const val KEY_RECORD = "record"

    private const val KEY_DATA = "data"

    /**
     * Obtiene el record desde las preferencias compartidas.
     * @param context Contexto de la aplicación.
     * @return El objeto Record con los datos guardados.
     */
    override fun obtenerRecord(context: Context): Record {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val rec = sharedPreferences.getInt(KEY_RECORD,0)
        val dat = sharedPreferences.getLong(KEY_DATA, Date().time) // Leer como Long
        Record.valorRecord = rec
        Record.fechaSuperacion = Date(dat) // Crear Date desde Long
        return Record
    }


    /**
     * Permite actualizar el record si se supera.
     * @param context Contexto de la aplicación.
     * @param nuevoRecord Nuevo record a guardar.
     * @param dataActual La fecha actual de la superación del record.
     * @return 10 si la operación fue exitosa.
     */
    override fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int {
        val sharedPreferences  = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putInt(KEY_RECORD,nuevoRecord)
            putLong(KEY_DATA, dataActual.time) // Guardar como Long
            apply() // Es mejor usar apply() para escrituras asíncronas
        }
        return 10
    }
}
