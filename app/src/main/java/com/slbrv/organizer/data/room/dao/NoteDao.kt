package com.slbrv.organizer.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.slbrv.organizer.data.room.entity.note.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE :id")
    fun get(id: Int): NoteEntity

    @Delete
    fun delete(note: NoteEntity)
} 