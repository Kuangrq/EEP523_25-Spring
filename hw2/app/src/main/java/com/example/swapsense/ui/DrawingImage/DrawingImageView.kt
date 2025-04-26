package com.example.swapsense.ui.DrawingImage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.math.min

@SuppressLint("ClickableViewAccessibility")
class DrawingImageView(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private val path = Path()
    private val paint = Paint().apply {
        // TODO: Initialize paint with red color, 10f strokeWidth, STROKE style, anti-alias enabled
    }

    // TODO: Initialize with appropriate values
    private var drawCanvas: Canvas? = null
    private var bitmap: Bitmap? = null
    private var bitmapScale = 1f
    private var offsetX = 0f
    private var offsetY = 0f

    init {
        setOnTouchListener { _, event ->
            // Convert screen touch to bitmap coordinates
            // TODO: Convert event.x and event.y to bitmap coordinates using offset and scale
            // TODO: Start new path at (x, y)
            // TODO: Extend path to (x, y) and draw on canvas
            // TODO: Invalidate the view to trigger redraw

            // TODO: Finalize drawing, draw path and reset path

            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let { bmp ->
            // TODO: Compute bitmapScale and offsets to center image in view

            canvas.save()
            // TODO: Translate and scale canvas based on computed values

            // TODO: Draw the base bitmap

            // TODO: Draw the modified bitmap with paths

            canvas.restore()

        }
    }

    override fun setImageBitmap(bmp: Bitmap?) {
        super.setImageBitmap(bmp)
        bmp?.let {
            // TODO: Make a mutable copy of the bitmap and create a canvas for drawing
        }
        // TODO: Handle invalidating
    }

    fun getBitmapWithDrawing(): Bitmap? {
        // TODO: Return bitmap with drawing
    }

    fun setBrushColor(color: Int) {
        // TODO: Set paint color and refresh view

    }
}
