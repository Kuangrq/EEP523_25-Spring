package com.example.listapp

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var taskText: EditText
    private lateinit var pickTimeButton: Button
    private lateinit var timeText: TextView
    private var isTaskCompleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        taskText = findViewById(R.id.taskText)
        pickTimeButton = findViewById(R.id.pickTimeButton)
        timeText = findViewById(R.id.timeText)

        // Set initial task text
        taskText.setText("Task: Buy groceries")

        // Set task click listener
        taskText.setOnClickListener {
            toggleTaskCompletion()
        }

        // Set time picker button click listener
        pickTimeButton.setOnClickListener {
            showTimePicker()
        }
    }

    private fun toggleTaskCompletion() {
        isTaskCompleted = !isTaskCompleted
        val text = taskText.text.toString()
        
        if (isTaskCompleted) {
            val spannableString = SpannableString(text)
            spannableString.setSpan(StrikethroughSpan(), 0, text.length, 0)
            taskText.setText(spannableString)
        } else {
            taskText.setText(text)
        }
    }

    private fun showTimePicker() {
        val calendar = java.util.Calendar.getInstance()
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)

        TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeText.text = "Selected time: $time"
            },
            hour,
            minute,
            true
        ).show()
    }
}