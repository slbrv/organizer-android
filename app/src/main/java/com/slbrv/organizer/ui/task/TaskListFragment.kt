package com.slbrv.organizer.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.slbrv.organizer.R
import com.slbrv.organizer.data.room.task.TaskEntity

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

        val selectedTask = MutableLiveData<Int>()
        val checkedTask = MutableLiveData<Int>()
        val adapter = TaskRecyclerViewAdapter(requireContext(), tasks, selectedTask, checkedTask)

        taskViewModel.getAll().observe(viewLifecycleOwner, {
            tasks.clear()
            tasks.addAll(it)
            adapter.update(tasks)
        })

        taskRecyclerView = view.findViewById(R.id.task_recycler_view)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

        val addActionButton: FloatingActionButton = view.findViewById(R.id.task_add_action_button)

        addActionButton.setOnClickListener {
            onEditTask(adapter, tasks, -1)
        }

        selectedTask.observe(viewLifecycleOwner, {
            onEditTask(adapter, tasks, it)
        })

        checkedTask.observe(viewLifecycleOwner, {
            onCheckTask(tasks, it)
        })

        return view
    }

    private fun onEditTask(
        adapter: TaskRecyclerViewAdapter,
        tasks: ArrayList<TaskEntity>,
        id: Int
    ) {
        val taskData = MutableLiveData<TaskEntity>()
        val task = if (id >= 0) tasks[id] else null
        val taskEditFragment = TaskEditDialogFragment.getInstance(taskData, task)
        taskEditFragment.show(parentFragmentManager, taskEditFragment.tag)
        taskData.observe(viewLifecycleOwner, {
            if (id >= 0) {
                tasks[id] = it
                taskViewModel.update(it)
            } else {
                tasks.add(it)
                taskViewModel.insert(it)
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun onCheckTask(
        tasks: List<TaskEntity>,
        position: Int
    ) {
        taskViewModel.update(tasks[position])
    }

}