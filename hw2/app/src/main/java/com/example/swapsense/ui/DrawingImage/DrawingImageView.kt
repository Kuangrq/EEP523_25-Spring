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
        color = Color.RED
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var drawCanvas: Canvas? = null
    private var bitmap: Bitmap? = null
    private var bitmapScale = 1f
    private var offsetX = 0f
    private var offsetY = 0f

    init {
        setOnTouchListener { _, event ->
            val x = (event.x - offsetX) / bitmapScale
            val y = (event.y - offsetY) / bitmapScale

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.reset()
                    path.moveTo(x, y)
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    drawCanvas?.drawPath(path, paint)
                    path.reset()
                }
            }
            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let { bmp ->
            val viewWidth = width.toFloat()
            val viewHeight = height.toFloat()
            val bitmapWidth = bmp.width.toFloat()
            val bitmapHeight = bmp.height.toFloat()

            bitmapScale = min(viewWidth / bitmapWidth, viewHeight / bitmapHeight)
            offsetX = (viewWidth - bitmapWidth * bitmapScale) / 2
            offsetY = (viewHeight - bitmapHeight * bitmapScale) / 2

            canvas.save()
            canvas.translate(offsetX, offsetY)
            canvas.scale(bitmapScale, bitmapScale)

            canvas.drawBitmap(bmp, 0f, 0f, null)
            canvas.drawPath(path, paint)

            canvas.restore()
        }
    }

    override fun setImageBitmap(bmp: Bitmap?) {
        super.setImageBitmap(bmp)
        bmp?.let {
            bitmap = it.copy(Bitmap.Config.ARGB_8888, true)
            drawCanvas = Canvas(bitmap!!)
            invalidate()
        }
    }

    fun getBitmapWithDrawing(): Bitmap? {
        return bitmap
    }

    fun setBrushColor(color: Int) {
        paint.color = color
        invalidate()
    }
}
