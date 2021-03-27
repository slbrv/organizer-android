package com.slbrv.organizer.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.slbrv.organizer.data.room.entity.note.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id=:id")
    fun get(id: Long): Note

    @Insert
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
} 