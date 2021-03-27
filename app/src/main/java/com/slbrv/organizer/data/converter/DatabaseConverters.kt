package com.slbrv.organizer.data.converter

import androidx.room.TypeConverter
import java.util.*

class DatabaseConverters {

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}