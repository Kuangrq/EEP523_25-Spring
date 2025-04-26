package com.example.swapsense.ui.Camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.Manifest
import com.example.swapsense.R
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {

    // TODO: Declare variables and handle assignment of values using null cecks
    private lateinit var previewView: PreviewView
    private var imageCapture: //TODO
    private var lensFacing = //TODO

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // TODO: Declare requiredPermissions variable with android version handling
    private val requiredPermissions: Array<String>
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // TODO
            } else {
                // TODO
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher =  // TODO: Register permissionLauncher with ActivityResultContracts.RequestMultiplePermissions

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // TODO: Initialize previewView from layout

        // TODO: Set click listener to capture button to trigger takePhoto()

        // TODO: Set click listener to switchCamera button to toggle between front and back

        // TODO: Launch permission request if not all permissions granted, else startCamera()

    }

    private fun allPermissionsGranted(): Boolean {
        return         // TODO: Implement permission check logic

    }

    // TODO: Handle permission result fallback (including treating WRITE_EXTERNAL_STORAGE as "granted" on newer Androids)

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            // TODO: Get CameraProvider, build preview and imageCapture use cases
            // TODO: Add cameraSelector
            // TODO: Bind use cases to lifecycle
        )
    }

    private fun takePhoto() {
        // TODO: Create ImageCapture, OutputFileOptions, ContentValues(example: displayname,mimetype) variables
        // TODO: Call imageCapture.takePicture with appropriate callback
        // TODO: Error Handling
    }
}
