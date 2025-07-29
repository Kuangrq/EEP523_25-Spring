package com.example.hw1_template

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.app.TimePickerDialog
import java.time.LocalTime

// MainActivity: The heart of your application's UI.
// This class coordinates the main user interactions and screen transitions.
class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTaskCount: TextView

    // Define request code
    private val NEW_TASK_REQUEST_CODE = 1
    private val EDIT_TASK_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        // Initialize UI components
        recyclerView = findViewById(R.id.rvTasks)
        tvTaskCount = findViewById(R.id.tvTaskCount)

        // Set up RecyclerView with a LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up FloatingActionButton to add new tasks
        val fabNewTask = findViewById<FloatingActionButton>(R.id.fabNewTask)
        fabNewTask.setOnClickListener {
            // Launch NewTaskSheet activity for result
            val intent = Intent(this, NewTaskSheet::class.java)
            startActivityForResult(intent, NEW_TASK_REQUEST_CODE)
        }

        // Observe task list changes and update UI accordingly
        taskViewModel.taskItems.observe(this) { taskItems ->
            // Update the task count display
            updateTaskCount(taskItems.size)

            // Update the RecyclerView adapter with the new data
            recyclerView.adapter = TaskItemAdapter(taskItems, this)
        }
    }

    // Handle activity results
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val taskTitle = data.getStringExtra("TASK_TITLE") ?: return
            val taskDescription = data.getStringExtra("TASK_DESCRIPTION") ?: ""

            when (requestCode) {
                NEW_TASK_REQUEST_CODE -> {
                    val newTask = taskViewModel.createTask(taskTitle, taskDescription)
                    if (data.getBooleanExtra("HAS_DEADLINE", false)) {
                        val hour = data.getIntExtra("DEADLINE_HOUR", 0)
                        val minute = data.getIntExtra("DEADLINE_MINUTE", 0)
                        val deadlineTime = LocalTime.of(hour, minute)
                        newTask.hasDeadline = true
                        newTask.deadlineTime = deadlineTime
                    }
                    taskViewModel.addTaskItem(newTask)
                }
                EDIT_TASK_REQUEST_CODE -> {
                    val taskId = data.getIntExtra("TASK_ID", -1)
                    if (taskId != -1) {
                        taskViewModel.updateTask(taskId, taskTitle, taskDescription)
                        if (data.getBooleanExtra("HAS_DEADLINE", false)) {
                            val hour = data.getIntExtra("DEADLINE_HOUR", 0)
                            val minute = data.getIntExtra("DEADLINE_MINUTE", 0)
                            val deadlineTime = LocalTime.of(hour, minute)
                            taskViewModel.setDeadline(taskId, deadlineTime)
                        }
                    }
                }
            }
        }
    }

    // Update the task count TextView
    private fun updateTaskCount(count: Int) {
        tvTaskCount.text = "Tasks: $count"
    }

    // Handle clicks on task items
    override fun onItemClick(taskItem: TaskItem) {
        // 打开编辑界面
        val intent = Intent(this, NewTaskSheet::class.java).apply {
            putExtra("TASK_ID", taskItem.id)
            putExtra("TASK_TITLE", taskItem.title)
            putExtra("TASK_DESCRIPTION", taskItem.description)
            putExtra("IS_EDITING", true)
            if (taskItem.hasDeadline && taskItem.deadlineTime != null) {
                putExtra("HAS_DEADLINE", true)
                putExtra("DEADLINE_HOUR", taskItem.deadlineTime!!.hour)
                putExtra("DEADLINE_MINUTE", taskItem.deadlineTime!!.minute)
            }
        }
        startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
    }

    override fun onCompleteClick(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem.id, !taskItem.isCompleted)
    }

    override fun onDeleteClick(taskItem: TaskItem) {
        taskViewModel.removeTask(taskItem.id)
    }

    override fun onTimeClick(taskItem: TaskItem) {
        showTimePickerDialog(taskItem)
    }

    // Display a time picker dialog for selecting a deadline
    private fun showTimePickerDialog(taskItem: TaskItem) {
        val currentTime = taskItem.deadlineTime ?: LocalTime.now()

        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedTime = LocalTime.of(hourOfDay, minute)
                taskViewModel.setDeadline(taskItem.id, selectedTime)
            },
            currentTime.hour,
            currentTime.minute,
            true // 使用24小时制
        ).show()
    }
}