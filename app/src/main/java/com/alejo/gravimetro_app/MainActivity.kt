package com.alejo.gravimetro_app // Paquete de la aplicación

import android.os.Bundle // Paquete de Android
import androidx.activity.enableEdgeToEdge // Actividad de compatibilidad
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Importaciones de hardware
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity

// Importación de widgets
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager // Administrador de sensores
    private lateinit var gravitySensor: Sensor // Sensor de gravedad
    private lateinit var tvX: TextView // Vista de texto para el eje X
    private lateinit var tvY: TextView // Vista de texto para el eje Y
    private lateinit var tvZ: TextView // Vista de texto para el eje Z

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                // Actualizar los valores mostrados en pantalla
                tvX.text = getString(R.string.axis_x, it.values[0])
                tvY.text = getString(R.string.axis_y, it.values[1])
                tvZ.text = getString(R.string.axis_z, it.values[2])
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // No se necesita implementar para este caso
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        tvX = findViewById(R.id.tvX)
        tvY = findViewById(R.id.tvY)
        tvZ = findViewById(R.id.tvZ)

        // Inicializar sensor de gravedad
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
            ?: throw IllegalStateException("El sensor de gravedad no está disponible en este dispositivo.")

        // Configurar ajuste de márgenes para insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener,
            gravitySensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }
}
