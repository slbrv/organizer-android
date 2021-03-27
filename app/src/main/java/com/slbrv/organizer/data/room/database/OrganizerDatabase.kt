package com.slbrv.organizer.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slbrv.organizer.data.converter.DatabaseConverters
import com.slbrv.organizer.data.room.dao.NoteDao
import com.slbrv.organizer.data.room.entity.note.Note

@Database(entities = arrayOf(Note::class), version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class OrganizerDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}