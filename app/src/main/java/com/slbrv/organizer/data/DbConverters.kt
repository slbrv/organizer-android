package com.slbrv.organizer.data

import androidx.room.TypeConverter
import java.util.*

class DbConverters {

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}