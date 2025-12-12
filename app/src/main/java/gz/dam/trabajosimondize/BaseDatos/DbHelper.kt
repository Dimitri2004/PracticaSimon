package gz.dam.trabajosimondize.BaseDatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Clase auxiliar que gestiona la creación y actualización de la base de datos.
 *
 * Hereda de [SQLiteOpenHelper], que proporciona la funcionalidad base para
 * trabajar con una base de datos SQLite en Android.
 */
class DbHelper(context: Context) : SQLiteOpenHelper(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION) {
    private val TAG = "SQLite"

    /**
     * Se llama cuando la base de datos se crea por primera vez.
     * Aquí es donde se deben ejecutar las sentencias SQL para crear las tablas.
     */
    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "Creando la base de datos...")
        db.execSQL(Contract.SQL_CREATE_ENTRIES)
        Log.d(TAG, "Base de datos creada.")
    }

    /**
     * Se llama cuando la versión de la base de datos necesita ser actualizada.
     * La política aquí es simple: descartar la tabla antigua y crearla de nuevo.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "Actualizando la base de datos...")
        db.execSQL(Contract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    /**
     * Se llama cuando la versión de la base de datos necesita ser revertida.
     * En este caso, simplemente delegamos a la lógica de onUpgrade.
     */
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "Desactualizando la base de datos...")
        onUpgrade(db, oldVersion, newVersion)
    }
}
