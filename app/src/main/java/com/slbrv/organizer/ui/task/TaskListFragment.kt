package com.slbrv.organizer.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.task.TaskEntity
import java.util.*
import kotlin.collections.ArrayList

class TaskListFragment : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        val tasks = ArrayList<TaskEntity>()
        val time = Calendar.getInstance().time
        tasks.add(TaskEntity(null, "Task", time, time, "Project", false))
        tasks.add(TaskEntity(null, "Task", time, time, "Project", false))
        tasks.add(TaskEntity(null, "Task", time, time, "Project", false))
        tasks.add(TaskEntity(null, "Task", time, time, "Project", false))

        taskRecyclerView = view.findViewById(R.id.task_recycler_view)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = TaskRecyclerViewAdapter(tasks)

        return view
    }

}