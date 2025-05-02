package com.example.swapsense.ui.dashboard

import android.Manifest
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.swapsense.databinding.FragmentDashboardBinding
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.widget.Toast
import android.os.Build

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var magneticSensor: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null

    private val magneticListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            Log.d("DashboardFragment", "magneticListener onSensorChanged: ${event.values.joinToString()}")
            binding.magneticX.text = String.format("Magnetic X: %.2f", event.values[0])
            binding.magneticY.text = String.format("Magnetic Y: %.2f", event.values[1])
            binding.magneticZ.text = String.format("Magnetic Z: %.2f", event.values[2])
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val proximityListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            Log.d("DashboardFragment", "proximityListener onSensorChanged: ${event.values.joinToString()}")
            binding.proximity.text = String.format("Proximity: %.2f", event.values[0])
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val gyroscopeListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            Log.d("DashboardFragment", "gyroscopeListener onSensorChanged: ${event.values.joinToString()}")
            binding.gyroX.text = String.format("Gyro X: %.2f", event.values[0])
            binding.gyroY.text = String.format("Gyro Y: %.2f", event.values[1])
            binding.gyroZ.text = String.format("Gyro Z: %.2f", event.values[2])
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    private val SENSOR_PERMISSION_CODE = 1002

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.d("DashboardFragment", "Available sensors: ${sensorList.joinToString { it.name }}")

        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        Log.d("DashboardFragment", "magneticSensor: $magneticSensor")
        Log.d("DashboardFragment", "proximitySensor: $proximitySensor")
        Log.d("DashboardFragment", "gyroscopeSensor: $gyroscopeSensor")

        if (magneticSensor == null) {
            Toast.makeText(context, "Magnetic sensor not available", Toast.LENGTH_SHORT).show()
        }
        if (proximitySensor == null) {
            Toast.makeText(context, "Proximity sensor not available", Toast.LENGTH_SHORT).show()
        }
        if (gyroscopeSensor == null) {
            Toast.makeText(context, "Gyroscope sensor not available", Toast.LENGTH_SHORT).show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.BODY_SENSORS), SENSOR_PERMISSION_CODE)
            } else {
                registerSensors()
            }
        } else {
            registerSensors()
        }
    }

    private fun registerSensors() {
        magneticSensor?.let {
            sensorManager.registerListener(
                magneticListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        proximitySensor?.let {
            sensorManager.registerListener(
                proximityListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        gyroscopeSensor?.let {
            sensorManager.registerListener(
                gyroscopeListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(magneticListener)
        sensorManager.unregisterListener(proximityListener)
        sensorManager.unregisterListener(gyroscopeListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SENSOR_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerSensors()
            } else {
                Toast.makeText(context, "需要传感器权限才能正常使用！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}
