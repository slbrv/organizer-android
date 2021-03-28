package com.slbrv.organizer.data.room.task

import androidx.room.*
import com.slbrv.organizer.data.room.task.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id=:id")
    fun get(id: Long): TaskEntity

    @Insert
    fun insert(noteEntity: TaskEntity): Long

    @Update
    fun update(noteEntity: TaskEntity)

    @Delete
    fun delete(noteEntity: TaskEntity)
}