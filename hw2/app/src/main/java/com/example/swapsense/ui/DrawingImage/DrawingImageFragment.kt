package com.example.swapsense.ui.DrawingImage

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.swapsense.R
import android.graphics.Color


class DrawingImageFragment : Fragment() {
    private lateinit var drawingImageView: DrawingImageView
    private var originalBitmap: Bitmap? = null
    private var currentImageUri: Uri? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            currentImageUri = it

            try {
                // TODO: Load bitmap from URI, handle EXIF orientation, rotate if necessary, and set to drawingImageView
                // Hint: Use ExifInterface to get orientation and Matrix to rotate

            } catch (e: Exception) {
                //TODO: Handle Exception
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_draw, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        drawingImageView = view.findViewById(R.id.drawingImageView)

            // TODO: Launch image picker

            // TODO: Save drawn image as new image in gallery

            // TODO: Reset the drawing to originalBitmap

            // TODO: Set brush color to black

            // TODO: Set brush color to red

    }

    private fun saveImageAsNew() {
        // TODO: Get bitmap with drawing from view
        // TODO: Create content values and insert into MediaStore
        // TODO: Call saveImageToUri with returned URI
    }

    private fun saveImageToUri(uri: Uri) {
        // TODO: Compress and write bitmap to output stream using JPEG format
        // TODO: Handle success and error with Toast messages

    }
}