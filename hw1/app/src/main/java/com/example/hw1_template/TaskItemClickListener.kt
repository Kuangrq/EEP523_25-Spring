package com.example.hw1_template

// TaskItemClickListener: An interface for handling clicks on items in your list.
// This interface allows the app to respond to different user interactions with tasks.
interface TaskItemClickListener {
    // Called when a task item is clicked
    fun onItemClick(taskItem: TaskItem)

    // Called when the complete button is clicked
    fun onCompleteClick(taskItem: TaskItem)

    // Called when the delete button is clicked
    fun onDeleteClick(taskItem: TaskItem)

    // Called when the time button is clicked
    fun onTimeClick(taskItem: TaskItem)
}