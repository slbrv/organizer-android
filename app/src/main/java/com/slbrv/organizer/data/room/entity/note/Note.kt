package com.slbrv.organizer.data.room.entity.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "creation_date") var creationDate: Date,
    @ColumnInfo(name = "edit_date") var editDate: Date,
    @ColumnInfo(name = "project") var project: String,
    @ColumnInfo(name = "vanish") var vanish: Boolean
)