package com.example.articlehub.until

import androidx.room.TypeConverter
import com.example.articlehub.model.Tag
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromTagList(value: List<Tag>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toTagList(value: String): List<Tag> {
        return Json.decodeFromString(value)
    }
}