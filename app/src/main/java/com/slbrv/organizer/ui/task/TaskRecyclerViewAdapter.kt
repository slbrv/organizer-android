package com.slbrv.organizer.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.task.TaskEntity
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class TaskRecyclerViewAdapter(
    private val tasks: List<TaskEntity>
) : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.task_recycler_view_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskTextView.text = tasks[position].task
        holder.taskTargetDateTextView.text =
            SimpleDateFormat("yy-MM-dd HH:mm", Locale.US)
                .format(tasks[position].targetDate)
        holder.taskProjectTextView.text = tasks[position].project
        holder.taskCheckBox.isChecked = tasks[position].checked
        holder.layout.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var taskTextView: TextView = view.findViewById(R.id.task_text_view)
        var taskTargetDateTextView: TextView = view.findViewById(R.id.task_target_date_text_view)
        var taskProjectTextView: TextView = view.findViewById(R.id.task_project_text_view)
        var taskCheckBox: CheckBox = view.findViewById(R.id.task_check_box)
        var layout: LinearLayout = view.findViewById(R.id.task_recycler_view_layout)
    }
}