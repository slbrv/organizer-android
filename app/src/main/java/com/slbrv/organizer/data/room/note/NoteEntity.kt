package com.slbrv.organizer.data.room.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "list_position") var position: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "content") var content: String = "",
    @ColumnInfo(name = "creation_date") var creationDate: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "edit_date") var editDate: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "project") var project: String = "",
    @ColumnInfo(name = "vanish") var vanish: Boolean = false
)