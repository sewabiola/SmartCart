package com.smartcart.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smartcart.data.database.SmartCartDatabase
import com.smartcart.data.entity.CategoryEntity
import com.smartcart.data.entity.ShoppingItemEntity
import com.smartcart.data.entity.ShoppingListEntity
import com.smartcart.data.repository.SmartCartRepository
import com.smartcart.data.repository.ShoppingListWithItemCount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class SmartCartViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: SmartCartRepository
    
    init {
        val database = SmartCartDatabase.getDatabase(application)
        repository = SmartCartRepository(
            database.shoppingListDao(),
            database.shoppingItemDao(),
            database.categoryDao()
        )
    }
    
    // Shopping Lists
    val allLists: Flow<List<ShoppingListWithItemCount>> = repository.getAllLists()
    
    fun insertList(name: String) {
        viewModelScope.launch {
            val list = ShoppingListEntity(
                name = name,
                createdAt = Date(),
                updatedAt = Date()
            )
            repository.insertList(list)
        }
    }
    
    fun updateList(list: ShoppingListWithItemCount, newName: String) {
        viewModelScope.launch {
            val entity = ShoppingListEntity(
                id = list.id,
                name = newName,
                isCompleted = list.isCompleted,
                createdAt = list.createdAt,
                updatedAt = Date()
            )
            repository.updateList(entity)
        }
    }
    
    fun deleteList(listId: Int) {
        viewModelScope.launch {
            repository.deleteListById(listId)
        }
    }
    
    fun toggleListCompleted(list: ShoppingListWithItemCount) {
        viewModelScope.launch {
            val entity = ShoppingListEntity(
                id = list.id,
                name = list.name,
                isCompleted = !list.isCompleted,
                createdAt = list.createdAt,
                updatedAt = Date()
            )
            repository.updateList(entity)
        }
    }
    
    // Shopping Items
    fun getItemsForList(listId: Int): Flow<List<ShoppingItemEntity>> {
        return repository.getItemsForList(listId)
    }
    
    fun insertItem(listId: Int, name: String, category: String, quantity: String) {
        viewModelScope.launch {
            val item = ShoppingItemEntity(
                listId = listId,
                name = name,
                category = category,
                quantity = quantity,
                createdAt = Date(),
                updatedAt = Date()
            )
            repository.insertItem(item)
        }
    }
    
    fun updateItem(item: ShoppingItemEntity) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
    
    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            repository.deleteItemById(itemId)
        }
    }
    
    fun toggleItemCompleted(itemId: Int) {
        viewModelScope.launch {
            repository.toggleItemCompleted(itemId)
        }
    }
    
    // Categories
    val allCategories: Flow<List<CategoryEntity>> = repository.getAllCategories()
    
    fun insertCategory(name: String, color: String = "#6200EE") {
        viewModelScope.launch {
            val category = CategoryEntity(
                name = name,
                color = color,
                createdAt = Date(),
                updatedAt = Date()
            )
            repository.insertCategory(category)
        }
    }
    
    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.updateCategory(category)
        }
    }
    
    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            repository.deleteCategoryById(categoryId)
        }
    }
    
    // Item Reordering
    fun moveItemUp(listId: Int, itemId: Int) {
        viewModelScope.launch {
            repository.moveItemUp(listId, itemId)
        }
    }
    
    fun moveItemDown(listId: Int, itemId: Int) {
        viewModelScope.launch {
            repository.moveItemDown(listId, itemId)
        }
    }
}