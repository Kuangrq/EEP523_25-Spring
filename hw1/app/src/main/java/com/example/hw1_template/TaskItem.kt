package com.example.hw1_template

import java.time.LocalTime
import java.time.format.DateTimeFormatter

// TaskItem: Represents the data structure for a task in your to-do list.
// This class holds all the data related to a single task.
data class TaskItem(
    val id: Int,
    var title: String,
    var description: String = "",
    var isCompleted: Boolean = false,
    var hasDeadline: Boolean = false,
    var deadlineTime: LocalTime? = null
) {
    // Format the deadline time to a readable string
    fun deadlineTimeFormatted(): String {
        if (!hasDeadline || deadlineTime == null) {
            return ""
        }

        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return deadlineTime!!.format(formatter)
    }
}