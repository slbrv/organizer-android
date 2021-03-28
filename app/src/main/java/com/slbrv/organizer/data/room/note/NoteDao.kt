package com.slbrv.organizer.data.room.note

import androidx.room.*
import com.slbrv.organizer.data.room.note.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id=:id")
    fun get(id: Long): NoteEntity

    @Insert
    fun insert(noteEntity: NoteEntity): Long

    @Update
    fun update(noteEntity: NoteEntity)

    @Delete
    fun delete(noteEntity: NoteEntity)
} 