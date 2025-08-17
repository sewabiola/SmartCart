package com.smartcart.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "shopping_items",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listId: Int,
    val name: String,
    val category: String = "General",
    val quantity: String = "1",
    val isCompleted: Boolean = false,
    val notes: String = "",
    val sortOrder: Int = 0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)