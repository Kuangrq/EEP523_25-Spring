package com.example.hellobuttonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pressMeButton = findViewById<Button>(R.id.pressMeButton)
        val helloText = findViewById<TextView>(R.id.helloText)

        pressMeButton.setOnClickListener {
            helloText.text = "You pressed it!"
            pressMeButton.text = "Nice!"
        }
    }
}