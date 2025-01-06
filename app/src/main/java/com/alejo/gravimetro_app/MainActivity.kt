package com.alejo.gravimetro_app // Paquete de la aplicación

import android.os.Bundle // Paquete de Android
import androidx.activity.enableEdgeToEdge // Actividad de compatibilidad
// Importaciones de Android (Vista de compatibilidad)
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Imporaciones de hardware
// Sensores de hardware
import android.hardware.Sensor // Sensor de hardware
import android.hardware.SensorEvent // Evento de sensor
import android.hardware.SensorEventListener // Escucha de eventos de sensor
import android.hardware.SensorManager // Administrador de sensores
import androidx.appcompat.app.AppCompatActivity // Actividad de compatibilidad

// Importacion de widgets
import android.widget.TextView // Vista de texto

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}