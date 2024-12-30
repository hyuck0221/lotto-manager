package com.hshim.lottomanager.database.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = false)
class IntConverter : AttributeConverter<List<Int>, String> {
    override fun convertToDatabaseColumn(attribute: List<Int>?): String? {
        return attribute?.joinToString(separator = ",")
    }

    override fun convertToEntityAttribute(dbData: String?): List<Int>? {
        return dbData?.split(",")?.map { it.toInt() }
    }
}