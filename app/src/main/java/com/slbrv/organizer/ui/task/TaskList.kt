package com.slbrv.organizer.ui.task

import android.util.Log
import com.slbrv.organizer.data.room.task.TaskEntity
import java.util.*
import kotlin.collections.ArrayList

class TaskList {

    private val tasks = ArrayList<TaskEntity>()

    fun set(src: List<TaskEntity>) {
        tasks.clear()
        tasks.addAll(src)
        tasks.sortBy { it.position }
    }

    fun replace(index: Int, task: TaskEntity) {
        tasks[index] = task
    }

    fun add(task: TaskEntity) {
        task.position = tasks.size
        tasks.add(task)
    }

    fun get(index: Int) = tasks[index]

    fun move(from: Int, to: Int) {
        val task = tasks.removeAt(from)
        tasks.add(to, task)
        for ((index, item) in tasks.withIndex()) {
            item.position = index
            Log.i("APP", "Id: ${item.id} task: ${item.task}, position: ${item.position}")
        }

    }

    fun toList() = tasks
}