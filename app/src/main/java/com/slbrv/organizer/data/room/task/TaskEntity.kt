package com.slbrv.organizer.data.room.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "task") var task: String = "",
    @ColumnInfo(name = "list_position") var position: Int = 0,
    @ColumnInfo(name = "creation_date") var creationDate: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "target_date") var targetDate: Date = Calendar.getInstance().time,
    @ColumnInfo(name = "project") var project: String = "",
    @ColumnInfo(name = "checked") var checked: Boolean = false
)