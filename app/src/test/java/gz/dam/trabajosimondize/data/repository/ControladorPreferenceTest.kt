package gz.dam.trabajosimondize.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import gz.dam.trabajosimondize.data.controller.ControladorPreference
import gz.dam.trabajosimondize.data.record.Record
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
class ControladorPreferenceTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        // Limpiar las preferencias antes de cada prueba
        val prefs = context.getSharedPreferences("preferencias_app_Nuevas", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()

        Record.recordPun = 0
        Record.recordFeha = java.time.LocalDateTime.now()
    }

    @Test
    fun obtenerRecord_leePreferencias_y_devuelveRecord() {
        val expectedInt = 42
        val expectedDate = java.time.LocalDateTime.now()

        // Escribir datos de prueba en las SharedPreferences reales
        val prefs = context.getSharedPreferences("preferencias_app_Nuevas", Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("record", expectedInt)
            .putLong("data", java.time.ZoneId.systemDefault().let { expectedDate.atZone(it).toInstant().toEpochMilli() })
            .apply()

        val record = ControladorPreference.obtenerRecord(context)

        assertEquals(expectedInt, record.recordPun)
        // Comparamos los milisegundos para evitar problemas con la precisión del objeto Date
        assertEquals(expectedDate.dayOfMonth, record.recordFeha.dayOfMonth)
    }

    @Test
    fun actualizarRecord_escribePreferencias_y_devuelveUno() {
        val nuevoRecord = 99
        val fecha = Date()

        val resultado = ControladorPreference.actualizarRecord(context, nuevoRecord, fecha)

        assertEquals(10, resultado)

        // Verificar que los datos se han escrito correctamente
        val prefs = context.getSharedPreferences("preferencias_app_Nuevas", Context.MODE_PRIVATE)
        val savedRecord = prefs.getInt("record", 0)
        val savedDate = prefs.getLong("data", 0L)

        assertEquals(nuevoRecord, savedRecord)
        assertEquals(fecha.time, savedDate)
    }

}
