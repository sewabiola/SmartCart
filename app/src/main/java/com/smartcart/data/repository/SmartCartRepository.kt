package com.smartcart.data.repository

import com.smartcart.data.dao.CategoryDao
import com.smartcart.data.dao.ShoppingItemDao
import com.smartcart.data.dao.ShoppingListDao
import com.smartcart.data.entity.CategoryEntity
import com.smartcart.data.entity.ShoppingItemEntity
import com.smartcart.data.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Date

class SmartCartRepository(
    private val listDao: ShoppingListDao,
    private val itemDao: ShoppingItemDao,
    private val categoryDao: CategoryDao
) {
    
    // Shopping Lists
    fun getAllLists(): Flow<List<ShoppingListWithItemCount>> {
        return combine(
            listDao.getAllLists(),
            itemDao.getAllItems()
        ) { lists, allItems ->
            lists.map { list ->
                val listItems = allItems.filter { it.listId == list.id }
                val totalItems = listItems.size
                val completedItems = listItems.count { it.isCompleted }
                
                ShoppingListWithItemCount(
                    id = list.id,
                    name = list.name,
                    isCompleted = list.isCompleted,
                    totalItems = totalItems,
                    completedItems = completedItems,
                    createdAt = list.createdAt,
                    updatedAt = list.updatedAt
                )
            }
        }
    }
    
    suspend fun getListById(id: Int): ShoppingListEntity? {
        return listDao.getListById(id)
    }
    
    suspend fun insertList(list: ShoppingListEntity): Long {
        return listDao.insertList(list)
    }
    
    suspend fun updateList(list: ShoppingListEntity) {
        listDao.updateList(list.copy(updatedAt = Date()))
    }
    
    suspend fun deleteList(list: ShoppingListEntity) {
        listDao.deleteList(list)
    }
    
    suspend fun deleteListById(id: Int) {
        listDao.deleteListById(id)
    }
    
    // Shopping Items
    fun getItemsForList(listId: Int): Flow<List<ShoppingItemEntity>> {
        return itemDao.getItemsForList(listId)
    }
    
    suspend fun getItemById(id: Int): ShoppingItemEntity? {
        return itemDao.getItemById(id)
    }
    
    suspend fun insertItem(item: ShoppingItemEntity): Long {
        return itemDao.insertItem(item)
    }
    
    suspend fun updateItem(item: ShoppingItemEntity) {
        itemDao.updateItem(item.copy(updatedAt = Date()))
    }
    
    suspend fun deleteItem(item: ShoppingItemEntity) {
        itemDao.deleteItem(item)
    }
    
    suspend fun deleteItemById(id: Int) {
        itemDao.deleteItemById(id)
    }
    
    suspend fun toggleItemCompleted(id: Int) {
        val item = itemDao.getItemById(id)
        item?.let {
            itemDao.updateItem(it.copy(
                isCompleted = !it.isCompleted,
                updatedAt = Date()
            ))
        }
    }
    
    // Categories
    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }
    
    suspend fun getCategoryById(id: Int): CategoryEntity? {
        return categoryDao.getCategoryById(id)
    }
    
    suspend fun insertCategory(category: CategoryEntity): Long {
        return categoryDao.insertCategory(category)
    }
    
    suspend fun insertCategories(categories: List<CategoryEntity>) {
        categoryDao.insertCategories(categories)
    }
    
    suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.updateCategory(category.copy(updatedAt = Date()))
    }
    
    suspend fun deleteCategory(category: CategoryEntity) {
        categoryDao.deleteCategory(category)
    }
    
    suspend fun deleteCategoryById(id: Int) {
        categoryDao.deleteCategoryById(id)
    }
    
    suspend fun getCategoryCount(): Int {
        return categoryDao.getCategoryCount()
    }
}

// Data class for UI
data class ShoppingListWithItemCount(
    val id: Int,
    val name: String,
    val isCompleted: Boolean,
    val totalItems: Int,
    val completedItems: Int,
    val createdAt: Date,
    val updatedAt: Date
)