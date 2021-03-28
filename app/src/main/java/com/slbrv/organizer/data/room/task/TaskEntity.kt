package com.slbrv.organizer.data.room.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "task") var task: String,
    @ColumnInfo(name = "creation_date") var creationDate: Date,
    @ColumnInfo(name = "target_date") var targetDate: Date,
    @ColumnInfo(name = "project") var project: String,
    @ColumnInfo(name = "checked") var checked: Boolean
)