package com.slbrv.organizer.ui.task

import android.os.Bundle
import android.util.Log
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

    private val tasks = TaskList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        val selectedTask = MutableLiveData<Int>()
        val checkedTask = MutableLiveData<Int>()
        val adapter =
            TaskRecyclerViewAdapter(requireContext(), tasks.toList(), selectedTask, checkedTask)

        taskViewModel.getAll().observe(viewLifecycleOwner, { list ->
            tasks.set(list)
            adapter.update(tasks.toList())
        })

        taskRecyclerView = view.findViewById(R.id.task_recycler_view)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter

        val addActionButton: FloatingActionButton = view.findViewById(R.id.task_add_action_button)

        addActionButton.setOnClickListener {
            onEditTask(adapter, -1)
        }

        selectedTask.observe(viewLifecycleOwner, { index ->
            onEditTask(adapter, index)
        })

        checkedTask.observe(viewLifecycleOwner, { index ->
            onCheckTask(adapter, index)
        })

        return view
    }

    override fun onPause() {
        super.onPause()
        taskViewModel.updatePositions(tasks.toList())
    }

    private fun onEditTask(
        adapter: TaskRecyclerViewAdapter,
        index: Int
    ) {
        val taskData = MutableLiveData<TaskEntity>()
        val task = if (index >= 0) tasks.get(index) else null
        val taskEditFragment = TaskEditDialogFragment.getInstance(taskData, task)
        taskEditFragment.show(parentFragmentManager, taskEditFragment.tag)
        taskData.observe(viewLifecycleOwner, {
            if (index >= 0) {
                tasks.replace(index, it)
                taskViewModel.update(it)
            } else {
                tasks.add(it)
                taskViewModel.insert(it)
            }
            adapter.notifyDataSetChanged()
        })
    }

    private fun onCheckTask(
        adapter: TaskRecyclerViewAdapter,
        index: Int
    ) {
        val task = tasks.get(index)
        tasks.move(index, 0)
        taskViewModel.update(task)
        adapter.notifyItemMoved(index, 0)
    }

}