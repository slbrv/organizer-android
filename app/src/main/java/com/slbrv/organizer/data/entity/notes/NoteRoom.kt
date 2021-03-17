package com.slbrv.organizer.data.entity.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class NoteRoom(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "creation_date") val creationDate: Date?,
    @ColumnInfo(name = "project") val project: String?,
    @ColumnInfo(name = "synced") val synced: Boolean?
)