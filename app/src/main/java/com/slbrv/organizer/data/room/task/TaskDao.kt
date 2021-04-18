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

    @Query("UPDATE tasks SET list_position = :position WHERE id = :id")
    fun updatePosition(id: Long, position: Int)

    @Delete
    fun delete(noteEntity: TaskEntity)

    @Query("DELETE FROM tasks")
    fun deleteAll()
}