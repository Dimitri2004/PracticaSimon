package gz.dam.trabajosimondize

import android.content.Context
import java.util.Date

interface InterfazRecord {

    // Permite recoger el record actual
    fun obtenerRecord(context: Context): Record
    fun actualizarRecord(context: Context, nuevoRecord: Int, dataActual: Date): Int
}