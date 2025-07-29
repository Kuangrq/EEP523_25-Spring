package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // Paint object for styling our drawing
    private val drawPaint = Paint()

    // Path object to track the finger movement
    private val drawPath = Path()

    // Bitmap to hold drawing content
    private lateinit var canvasBitmap: Bitmap
    private lateinit var drawCanvas: Canvas

    // Default color
    private var paintColor = Color.BLACK

    init {
        setupPaint()
    }

    private fun setupPaint() {
        drawPaint.color = paintColor
        drawPaint.isAntiAlias = true
        drawPaint.strokeWidth = 20f
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Create Bitmap and Canvas when size is determined
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap)

        // Fill with white background
        drawCanvas.drawColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the saved bitmap content
        canvas.drawBitmap(canvasBitmap, 0f, 0f, null)

        // Draw the current path that's being created
        canvas.drawPath(drawPath, drawPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Start a new line when finger touches screen
                drawPath.moveTo(touchX, touchY)
            }
            MotionEvent.ACTION_MOVE -> {
                // Continue the line as finger moves
                drawPath.lineTo(touchX, touchY)
            }
            MotionEvent.ACTION_UP -> {
                // End the line and commit it to the canvas
                drawCanvas.drawPath(drawPath, drawPaint)
                drawPath.reset()
            }
            else -> return false
        }

        // Trigger a redraw
        invalidate()
        return true
    }

    fun setColor(newColor: Int) {
        // Update paint color for future drawing
        paintColor = newColor
        drawPaint.color = paintColor
    }

    fun clearCanvas() {
        // Clear by filling with white
        drawCanvas.drawColor(Color.WHITE)
        invalidate()
    }
}