package com.smartcart.data.database

import com.smartcart.data.entity.CategoryEntity
import com.smartcart.data.entity.ShoppingItemEntity
import com.smartcart.data.entity.ShoppingListEntity
import com.smartcart.data.repository.SmartCartRepository
import java.util.Date

class DatabaseSeeder(private val repository: SmartCartRepository) {
    
    suspend fun seedDatabase() {
        // Check if database is already seeded
        val categoryCount = repository.getCategoryCount()
        
        // Seed default categories if not already present
        if (categoryCount == 0) {
            val defaultCategories = listOf(
                CategoryEntity(name = "General", isDefault = true),
                CategoryEntity(name = "Fruits & Vegetables", color = "#4CAF50", isDefault = true),
                CategoryEntity(name = "Dairy", color = "#2196F3", isDefault = true),
                CategoryEntity(name = "Meat", color = "#F44336", isDefault = true),
                CategoryEntity(name = "Bakery", color = "#FF9800", isDefault = true),
                CategoryEntity(name = "Pantry", color = "#795548", isDefault = true),
                CategoryEntity(name = "Frozen", color = "#00BCD4", isDefault = true),
                CategoryEntity(name = "Snacks", color = "#E91E63", isDefault = true),
                CategoryEntity(name = "Beverages", color = "#9C27B0", isDefault = true),
                CategoryEntity(name = "Household", color = "#607D8B", isDefault = true)
            )
            repository.insertCategories(defaultCategories)
        }
        
        val lists = repository.getAllLists()
        
        // Create sample shopping lists
        val groceriesId = repository.insertList(
            ShoppingListEntity(
                name = "Groceries",
                createdAt = Date(),
                updatedAt = Date()
            )
        ).toInt()
        
        val electronicsId = repository.insertList(
            ShoppingListEntity(
                name = "Electronics",
                createdAt = Date(),
                updatedAt = Date()
            )
        ).toInt()
        
        val homeSuppliesId = repository.insertList(
            ShoppingListEntity(
                name = "Home Supplies",
                isCompleted = true,
                createdAt = Date(),
                updatedAt = Date()
            )
        ).toInt()
        
        // Create sample items for Groceries
        val groceryItems = listOf(
            ShoppingItemEntity(listId = groceriesId, name = "Apples", category = "Fruits & Vegetables", quantity = "2 lbs"),
            ShoppingItemEntity(listId = groceriesId, name = "Milk", category = "Dairy", quantity = "1 gallon"),
            ShoppingItemEntity(listId = groceriesId, name = "Bread", category = "Bakery", quantity = "1 loaf", isCompleted = true),
            ShoppingItemEntity(listId = groceriesId, name = "Chicken Breast", category = "Meat", quantity = "2 lbs"),
            ShoppingItemEntity(listId = groceriesId, name = "Eggs", category = "Dairy", quantity = "1 dozen")
        )
        
        groceryItems.forEach { item ->
            repository.insertItem(item)
        }
        
        // Create sample items for Electronics
        val electronicsItems = listOf(
            ShoppingItemEntity(listId = electronicsId, name = "USB Cable", category = "Accessories", quantity = "1"),
            ShoppingItemEntity(listId = electronicsId, name = "Phone Charger", category = "Accessories", quantity = "1")
        )
        
        electronicsItems.forEach { item ->
            repository.insertItem(item)
        }
        
        // Create sample items for Home Supplies
        val homeItems = listOf(
            ShoppingItemEntity(listId = homeSuppliesId, name = "Paper Towels", category = "Household", quantity = "1 pack", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Dish Soap", category = "Household", quantity = "1 bottle", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Laundry Detergent", category = "Household", quantity = "1 bottle", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Light Bulbs", category = "Hardware", quantity = "4 pack", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Batteries", category = "Hardware", quantity = "1 pack", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Air Freshener", category = "Household", quantity = "2", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Trash Bags", category = "Household", quantity = "1 box", isCompleted = true),
            ShoppingItemEntity(listId = homeSuppliesId, name = "Cleaning Spray", category = "Household", quantity = "1 bottle", isCompleted = true)
        )
        
        homeItems.forEach { item ->
            repository.insertItem(item)
        }
    }
}