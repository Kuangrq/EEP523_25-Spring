package com.example.drawingapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var drawingView: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the DrawingView
        drawingView = findViewById(R.id.drawingView)

        // Set up color buttons
        findViewById<android.widget.Button>(R.id.blackButton).setOnClickListener {
            drawingView.setColor(Color.BLACK)
        }

        findViewById<android.widget.Button>(R.id.redButton).setOnClickListener {
            drawingView.setColor(Color.RED)
        }

        findViewById<android.widget.Button>(R.id.blueButton).setOnClickListener {
            drawingView.setColor(Color.BLUE)
        }

        // Set up clear button
        findViewById<android.widget.Button>(R.id.clearButton).setOnClickListener {
            drawingView.clearCanvas()
        }
    }
}