package com.alejo.gravimetro_app // Paquete de la aplicación

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    // Declaración de propiedades
    private lateinit var sensorManager: SensorManager
    private var gravitySensor: Sensor? = null
    private lateinit var tvX: TextView
    private lateinit var tvY: TextView
    private lateinit var tvZ: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilitar diseño edge-to-edge
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        tvX = findViewById(R.id.tv_x)
        tvY = findViewById(R.id.tv_y)
        tvZ = findViewById(R.id.tv_z)

        // Inicializar el administrador de sensores y el sensor de gravedad
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        // Validar si el sensor está disponible
        if (gravitySensor == null) {
            tvX.text = "Sensor de gravedad no disponible"
        }

        // Ajustar márgenes para insets del sistema
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener del sensor si está disponible
        gravitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar el listener del sensor para ahorrar energía
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Verificar si el evento corresponde al sensor de gravedad
        if (event?.sensor?.type == Sensor.TYPE_GRAVITY) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Actualizar los valores mostrados en pantalla
            tvX.text = "X: $x"
            tvY.text = "Y: $y"
            tvZ.text = "Z: $z"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Método requerido pero no utilizado en este caso
    }
}
