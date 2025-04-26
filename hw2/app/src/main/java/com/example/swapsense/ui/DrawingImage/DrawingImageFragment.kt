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
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.IOException
import java.io.OutputStream
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.activity.result.ActivityResultLauncher
import android.Manifest

class DrawingImageFragment : Fragment() {
    private lateinit var drawingImageView: DrawingImageView
    private var originalBitmap: Bitmap? = null
    private var currentImageUri: Uri? = null
    private lateinit var storagePermissionLauncher: ActivityResultLauncher<String>

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            currentImageUri = it
            try {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                if (inputStream == null) {
                    Toast.makeText(context, "无法打开图片文件", Toast.LENGTH_SHORT).show()
                    return@let
                }

                try {
                    val exif = ExifInterface(inputStream)
                    val orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL
                    )

                    val matrix = Matrix()
                    when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                        ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                        ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                    }

                    // 重新打开输入流用于解码位图
                    inputStream.close()
                    val bitmapInputStream = requireContext().contentResolver.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(bitmapInputStream)
                    
                    if (bitmap == null) {
                        Toast.makeText(context, "无法解码图片文件", Toast.LENGTH_SHORT).show()
                        return@let
                    }

                    originalBitmap = Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        matrix,
                        true
                    )
                    drawingImageView.setImageBitmap(originalBitmap)
                    bitmapInputStream?.close()
                } catch (e: IOException) {
                    Toast.makeText(context, "读取图片出错: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                } finally {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "加载图片失败: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storagePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                selectImageLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "请授予存储权限以导入图片", Toast.LENGTH_SHORT).show()
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
        val selectButton: Button = view.findViewById(R.id.selectButton)
        val saveButton: Button = view.findViewById(R.id.saveButton)
        val resetButton: Button = view.findViewById(R.id.resetButton)
        val blackButton: Button = view.findViewById(R.id.blackButton)
        val redButton: Button = view.findViewById(R.id.redButton)

        selectButton.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectImageLauncher.launch("image/*")
                } else {
                    storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                selectImageLauncher.launch("image/*")
            }
        }

        saveButton.setOnClickListener {
            saveImageAsNew()
        }

        resetButton.setOnClickListener {
            originalBitmap?.let { bitmap ->
                drawingImageView.setImageBitmap(bitmap)
            }
        }

        blackButton.setOnClickListener {
            drawingImageView.setBrushColor(Color.BLACK)
        }

        redButton.setOnClickListener {
            drawingImageView.setBrushColor(Color.RED)
        }
    }

    private fun saveImageAsNew() {
        val bitmap = drawingImageView.getBitmapWithDrawing() ?: return

        val name = "SwapSense_${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/SwapSense")
            }
        }

        val uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        uri?.let { saveImageToUri(it) }
    }

    private fun saveImageToUri(uri: Uri) {
        try {
            val outputStream: OutputStream? = requireContext().contentResolver.openOutputStream(uri)
            if (outputStream == null) {
                Toast.makeText(context, "无法获取输出流", Toast.LENGTH_SHORT).show()
                return
            }
            drawingImageView.getBitmapWithDrawing()?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            Toast.makeText(context, "Image saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}