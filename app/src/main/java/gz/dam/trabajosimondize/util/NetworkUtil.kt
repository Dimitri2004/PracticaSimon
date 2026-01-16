package gz.dam.trabajosimondize.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

/**
 * Utilidad para verificar el estado de la conexión a internet.
 * Proporciona métodos para detectar si el dispositivo tiene conexión de red activa.
 */
object NetworkUtil {
    private val TAG = "NetworkUtil"

    /**
     * Verifica si el dispositivo tiene conexión a internet activa
     * @param context El contexto de la aplicación
     * @return true si hay conexión a internet, false en caso contrario
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Para Android 6.0 (API 23) y superiores
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            val isConnected = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

            Log.d(TAG, "Conexión a internet disponible: $isConnected")
            isConnected && hasInternet
        } else {
            // Para Android anterior a 6.0
            @Suppress("DEPRECATION")
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            val connected = activeNetworkInfo != null && activeNetworkInfo.isConnected

            Log.d(TAG, "Conexión a internet disponible: $connected")
            connected
        }
    }

    /**
     * Verifica si hay conexión WiFi disponible
     * @param context El contexto de la aplicación
     * @return true si hay conexión WiFi, false en caso contrario
     */
    fun isWifiAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
        }
    }

    /**
     * Verifica si hay conexión de datos móviles disponible
     * @param context El contexto de la aplicación
     * @return true si hay conexión móvil, false en caso contrario
     */
    fun isMobileDataAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.type == ConnectivityManager.TYPE_MOBILE
        }
    }

    /**
     * Obtiene el tipo de conexión activa
     * @param context El contexto de la aplicación
     * @return String describiendo el tipo de conexión
     */
    fun getConnectionType(context: Context): String {
        return when {
            isWifiAvailable(context) -> "WiFi"
            isMobileDataAvailable(context) -> "Datos Móviles"
            else -> "Sin conexión"
        }
    }
}

