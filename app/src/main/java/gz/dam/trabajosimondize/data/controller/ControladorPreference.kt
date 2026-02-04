package gz.dam.trabajosimondize.data.controller

import android.content.Context
import androidx.core.content.edit
import gz.dam.trabajosimondize.data.record.InterfazRecord
import gz.dam.trabajosimondize.data.record.Record
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

//Es un singleton,solo una unica instancia del gestor ,ya que no necesita a varios gestores haciendo lo mismo
object ControladorPreference : InterfazRecord {
    // definimos el nombre del fichero de preferencias
    private const val PREFS_NAME = "preferencias_app_Nuevas"
    // definimos la clave del record (guardamos key:value)
    private const val KEY_RECORD = "record"

    private const val KEY_DATA = "data"

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss") //Formato de texto en el que se guarda la fecha
    /**
     * Obtiene el record desde las preferencias compartidas.
     * @param context Contexto de la aplicación.
     * @return El objeto Record con los datos guardados.
     */
    override fun obtenerRecord(context: Context): Record {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val rec = sharedPreferences.getInt(KEY_RECORD,0)
        val defaultDateMillis = java.time.LocalDateTime.parse("11/11/2011 11:11:11", formatter)
            .atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        val fecMillis = sharedPreferences.getLong(KEY_DATA, defaultDateMillis) // Read as Long

        // Convert epoch milliseconds to LocalDateTime
        val instant = java.time.Instant.ofEpochMilli(fecMillis)
        val localDateTime = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())

        Record.recordPun = rec
        Record.recordFeha = localDateTime // Assign LocalDateTime
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
