package com.example.hw1_template

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalTime

// NewTaskSheet: This Activity is responsible for adding new tasks.
// It handles user input for creating new tasks and adding them to the TaskViewModel.
class NewTaskSheet : AppCompatActivity() {
    private var selectedTime: LocalTime? = null
    private var isTimeSelected = false
    private var isEditing = false
    private var taskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_new_task_sheet)

        // 初始化UI组件
        val etTaskTitle = findViewById<TextInputEditText>(R.id.etTaskTitle)
        val etTaskDescription = findViewById<TextInputEditText>(R.id.etTaskDescription)
        val btnSetDeadline = findViewById<Button>(R.id.btnSetDeadline)
        val btnSaveTask = findViewById<Button>(R.id.btnSaveTask)

        // 检查是否是编辑模式
        isEditing = intent.getBooleanExtra("IS_EDITING", false)
        if (isEditing) {
            taskId = intent.getIntExtra("TASK_ID", -1)
            etTaskTitle.setText(intent.getStringExtra("TASK_TITLE"))
            etTaskDescription.setText(intent.getStringExtra("TASK_DESCRIPTION"))
            
            if (intent.getBooleanExtra("HAS_DEADLINE", false)) {
                val hour = intent.getIntExtra("DEADLINE_HOUR", 0)
                val minute = intent.getIntExtra("DEADLINE_MINUTE", 0)
                selectedTime = LocalTime.of(hour, minute)
                isTimeSelected = true
                updateTimeButtonText(btnSetDeadline, hour, minute)
            }
        }

        btnSetDeadline.setOnClickListener {
            showTimePickerDialog()
        }

        btnSaveTask.setOnClickListener {
            val taskTitle = etTaskTitle.text?.toString()?.trim() ?: ""
            val taskDescription = etTaskDescription.text?.toString()?.trim() ?: ""

            if (taskTitle.isEmpty()) {
                Toast.makeText(this, "Task name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 创建返回数据的Intent
            val resultIntent = Intent().apply {
                putExtra("TASK_TITLE", taskTitle)
                putExtra("TASK_DESCRIPTION", taskDescription)
                
                if (isEditing) {
                    putExtra("TASK_ID", taskId)
                }
                
                if (isTimeSelected && selectedTime != null) {
                    putExtra("HAS_DEADLINE", true)
                    putExtra("DEADLINE_HOUR", selectedTime!!.hour)
                    putExtra("DEADLINE_MINUTE", selectedTime!!.minute)
                } else {
                    putExtra("HAS_DEADLINE", false)
                }
            }

            // 设置结果并关闭活动
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun showTimePickerDialog() {
        val currentTime = selectedTime ?: LocalTime.now()

        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime = LocalTime.of(hourOfDay, minute)
                isTimeSelected = true

                // 更新按钮文本显示选择的时间
                updateTimeButtonText(findViewById(R.id.btnSetDeadline), hourOfDay, minute)

                Toast.makeText(
                    this,
                    "Time set to: ${String.format("%02d:%02d", hourOfDay, minute)}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            currentTime.hour,
            currentTime.minute,
            true // 使用24小时制
        ).show()
    }

    private fun updateTimeButtonText(button: Button, hour: Int, minute: Int) {
        val timeStr = String.format("%02d:%02d", hour, minute)
        button.text = "Time: $timeStr"
    }
}
