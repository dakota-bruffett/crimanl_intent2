package com.example.crimanlintent

import java.util.Date
import java.util.UUID

class TypeConverters {
    @TypeConverter
    fun toDate(date: Date?): Long?{
        return date?.time
    }
    @TypeConverter
    fun  toDate(millisSinceEpoh: Long?):Date?{
        return millisSinceEpoh?.let {
            Date(it)
        }
    }
    @TypeConverter
    fun toUUID(uuid: String?):UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?):String?{
        return uuid?.toString()
    }

}