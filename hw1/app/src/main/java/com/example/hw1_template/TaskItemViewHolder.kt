package com.example.hw1_template

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// TaskItemViewHolder: Holds the view for each task item in the list.
// This class binds individual views in the RecyclerView to your data.
class TaskItemViewHolder(
    itemView: View,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(itemView) {

    // UI elements from the task_item_cell layout
    private val taskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
    private val taskAction: ImageView = itemView.findViewById(R.id.ivTaskAction)
    private val taskComplete: ImageView = itemView.findViewById(R.id.ivTaskComplete)

    // Additional TextView for displaying the deadline time
    private val taskDeadline: TextView? = itemView.findViewById(R.id.tvTaskDeadline)

    // Bind the TaskItem data to the views
    fun bindTaskItem(taskItem: TaskItem) {
        // Set the task title
        taskTitle.text = taskItem.title

        // Update UI based on completion status
        if (taskItem.isCompleted) {
            taskTitle.paintFlags = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            taskComplete.setImageResource(R.drawable.ic_check_circle)
        } else {
            taskTitle.paintFlags = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            taskComplete.setImageResource(R.drawable.ic_circle)
        }

        // Show deadline if task has one
        if (taskItem.hasDeadline && taskDeadline != null) {
            taskDeadline.visibility = View.VISIBLE
            taskDeadline.text = "Due: ${taskItem.deadlineTimeFormatted()}"
        } else if (taskDeadline != null) {
            taskDeadline.visibility = View.GONE
        }

        // Set up click listener for the task action button
        taskAction.setOnClickListener {
            clickListener.onDeleteClick(taskItem)
        }

        // Set up click listener for the task item itself
        itemView.setOnClickListener {
            clickListener.onItemClick(taskItem)
        }

        // Set up click listener for the complete button
        taskComplete.setOnClickListener {
            clickListener.onCompleteClick(taskItem)
        }
    }
}