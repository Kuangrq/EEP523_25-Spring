package com.example.hw1_template

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// TaskItemAdapter: Manages how task items are displayed and interacted with in a RecyclerView.
// This adapter links your TaskItem data to the RecyclerView in the UI.
class TaskItemAdapter(
    private val taskItems: List<TaskItem>,
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>() {

    // Create new ViewHolder instances when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        // Inflate the task_item_cell layout for each item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item_cell, parent, false)

        // Return a new ViewHolder with the inflated view
        return TaskItemViewHolder(itemView, clickListener)
    }

    // Bind data to a ViewHolder at the specified position
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        // Get the TaskItem at the specified position
        val taskItem = taskItems[position]

        // Bind the TaskItem data to the ViewHolder
        holder.bindTaskItem(taskItem)
    }

    // Return the total number of items in the data set
    override fun getItemCount(): Int {
        return taskItems.size
    }
}