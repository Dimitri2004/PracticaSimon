package gz.dam.trabajosimondize.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge







class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mviewModel = MyViewModel(application)
        setContent {
            Interfaz(miViewModel = mviewModel)
        }
    }
}