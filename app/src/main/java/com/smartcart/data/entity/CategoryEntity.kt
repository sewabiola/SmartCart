package com.smartcart.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: String = "#6200EE", // Default Material color
    val isDefault: Boolean = false, // True for built-in categories
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)