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

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // TextViews to display sensor data
    private val binding get() = _binding!!

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

        // TODO
        // Initialize TextViews from the layout

        // TODO
        // Get the SensorManager instance
        // Get list of all Sensors
        // LOG it

        // TODO
        // Get sensor instances

        // TODO
        // Check if Sensors available
        // Toast Message if Sensor not available

        // TODO
        // Define SensorEventListeners

        // TODO
        // Register listeners

    }

    // TODO
    // checkPermission for the SENSORS


    // TODO
    // Callback for the result from requesting permissions


    // TODO
    // Declare Request codes for permissions


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
