package gz.dam.trabajosimondize.data.BaseDatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

/**
 * Objeto que define el "Contrato" de la base de datos. 
 * Un contrato es una buena práctica que centraliza en un solo lugar todas las constantes 
 * que definen nombres de tablas, columnas, etc. 
 * Esto ayuda a evitar errores al escribir los nombres de las columnas en diferentes partes del código.
 */
object ContratoSQLite {
    const val TAG = "SQLite"

    /**
     * Objeto interno que define el contenido de la tabla 'Record'.
     * Al heredar de BaseColumns, se obtiene automáticamente una columna _ID, 
     * que es una práctica recomendada para las claves primarias en Android.
     */
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "Record"
        const val COLUMN_NAME_PUNTUACION = "Puntuación"
        const val COLUMN_NAME_FECHA = "Fecha"

        const val COLUMN_NAME_NOMBRE ="Nombre"
    }

    /**
     * Sentencia SQL en formato String para crear la tabla de récords.
     * Define una clave primaria autoincremental (_ID), una columna para la puntuación (INTEGER)
     * y una columna para la fecha (TEXT).
     */
    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_NOMBRE} TEXT,"+
                "${FeedEntry.COLUMN_NAME_PUNTUACION} INTEGER," +
                "${FeedEntry.COLUMN_NAME_FECHA} TEXT)"

    /**
     * Sentencia SQL para eliminar la tabla. 
     * Es útil para actualizar la estructura de la base de datos.
     */
    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"


    /**
     * Clase auxiliar que gestiona la creación y actualización de la base de datos.
     * Hereda de SQLiteOpenHelper, que es la clase base de Android para trabajar con bases de datos SQLite.
     */
    class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        /**
         * Se ejecuta solo una vez, cuando la base de datos se crea por primera vez.
         * Aquí es donde se debe ejecutar el SQL para crear las tablas.
         */
        override fun onCreate(db: SQLiteDatabase) {
            Log.d(TAG,"Creando base")
            db.execSQL(SQL_CREATE_ENTRIES)
            Log.d(TAG,"Creada base")
        }

        /**
         * Se ejecuta cuando el número de DATABASE_VERSION cambia (aumenta).
         * Permite actualizar el esquema de la base de datos. La política aquí es simple:
         * eliminar la tabla antigua y crearla de nuevo.
         */
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.d(TAG,"Actualizando base")
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        /**
         * Se ejecuta si se intenta abrir la base de datos con una versión inferior a la actual.
         */
        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.d(TAG,"desactualizando base")
            onUpgrade(db, oldVersion, newVersion)
        }

        /**
         * Companion object para almacenar las constantes de la base de datos.
         */
        companion object {
            // Si cambias el esquema de la base de datos, debes incrementar la versión.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "FeedReader.db"
        }
    }
}