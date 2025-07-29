package com.example.hw1_template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// TaskViewModel: Handles the business logic of your task data, separating concerns from the UI.
// This ViewModel is the sole source of truth for task data in the app.
class TaskViewModel : ViewModel() {

    // LiveData to hold the current list of tasks
    val taskItems = MutableLiveData<MutableList<TaskItem>>()

    // Track the next ID to assign to a new task
    private var nextId = 0

    init {
        // Initialize with an empty list
        taskItems.value = mutableListOf()
    }

    // Add a new task to the list
    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value
        list?.add(newTask)
        taskItems.postValue(list)
        nextId++
    }

    // Create a new task with the given title and description
    fun createTask(taskTitle: String, description: String = ""): TaskItem {
        return TaskItem(
            id = nextId,
            title = taskTitle,
            description = description
        )
    }

    // Update a task in the list
    fun updateTask(taskId: Int, newTitle: String, newDescription: String) {
        val list = taskItems.value
        val task = list?.find { it.id == taskId }
        if (task != null) {
            task.title = newTitle
            task.description = newDescription
            taskItems.postValue(list)
        }
    }

    // Set the completion status of a task
    fun setCompleted(taskId: Int, isCompleted: Boolean) {
        val list = taskItems.value
        val task = list?.find { it.id == taskId }
        if (task != null) {
            task.isCompleted = isCompleted
            taskItems.postValue(list)
        }
    }

    // Remove a task from the list
    fun removeTask(taskId: Int) {
        val list = taskItems.value
        list?.removeIf { it.id == taskId }
        taskItems.postValue(list)
    }

    // Set a deadline for a task
    fun setDeadline(taskId: Int, time: java.time.LocalTime) {
        val list = taskItems.value
        val task = list?.find { it.id == taskId }
        if (task != null) {
            task.hasDeadline = true
            task.deadlineTime = time
            taskItems.postValue(list)
        }
    }
}