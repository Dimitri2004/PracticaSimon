package gz.dam.trabajosimondize.data.repository

import android.content.Context
import gz.dam.trabajosimondize.data.model.Record
import java.util.Date

interface InterfazRecord {

    // Permite recoger el record actual
    fun obtenerRecord(context: Context): Record
    fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int
}