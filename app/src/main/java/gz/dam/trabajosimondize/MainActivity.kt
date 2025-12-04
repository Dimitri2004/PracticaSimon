package gz.dam.trabajosimondize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel= MyViewModel(application)

        setContent {
            Interfaz(miViewModel = viewModel)
        }
    }

}