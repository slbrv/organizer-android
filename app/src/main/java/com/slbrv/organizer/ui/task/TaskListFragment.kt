package com.slbrv.organizer.ui.task

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.task.TaskEntity
import java.util.*
import kotlin.collections.ArrayList

class TaskListFragment : Fragment() {

    data class TaskEditData (
        val task: String,
        val project: String,
        val date: Date
    )

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
        val adapter = TaskRecyclerViewAdapter(requireContext(), tasks)

        taskRecyclerView = view.findViewById(R.id.task_recycler_view)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

        val addActionButton: FloatingActionButton = view.findViewById(R.id.task_add_action_button)

        addActionButton.setOnClickListener {
            val taskData = MutableLiveData<TaskEditData>()
            val taskEditFragment = TaskEditDialogFragment.getInstance(taskData)
            taskEditFragment.show(parentFragmentManager, taskEditFragment.tag)
            taskData.observe(viewLifecycleOwner, {
                val time = Calendar.getInstance().time
                tasks.add(TaskEntity(null, it.task, time, it.date, it.project, false))
                adapter.notifyDataSetChanged()
            })
        }

        return view
    }

}