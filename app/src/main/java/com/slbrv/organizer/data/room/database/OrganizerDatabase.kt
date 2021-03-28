package com.slbrv.organizer.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.slbrv.organizer.data.converter.DatabaseConverters
import com.slbrv.organizer.data.room.note.NoteDao
import com.slbrv.organizer.data.room.note.NoteEntity
import com.slbrv.organizer.data.room.task.TaskDao
import com.slbrv.organizer.data.room.task.TaskEntity

@Database(entities = arrayOf(NoteEntity::class, TaskEntity::class), version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class OrganizerDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
}