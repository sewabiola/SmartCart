package com.smartcart.data.dao

import androidx.room.*
import com.smartcart.data.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    
    @Query("SELECT * FROM shopping_items WHERE listId = :listId ORDER BY sortOrder ASC, createdAt ASC")
    fun getItemsForList(listId: Int): Flow<List<ShoppingItemEntity>>
    
    @Query("SELECT * FROM shopping_items WHERE id = :id")
    suspend fun getItemById(id: Int): ShoppingItemEntity?
    
    @Insert
    suspend fun insertItem(item: ShoppingItemEntity): Long
    
    @Update
    suspend fun updateItem(item: ShoppingItemEntity)
    
    @Delete
    suspend fun deleteItem(item: ShoppingItemEntity)
    
    @Query("DELETE FROM shopping_items WHERE id = :id")
    suspend fun deleteItemById(id: Int)
    
    @Query("SELECT * FROM shopping_items WHERE listId = :listId AND isCompleted = :isCompleted ORDER BY sortOrder ASC, createdAt ASC")
    fun getItemsByStatus(listId: Int, isCompleted: Boolean): Flow<List<ShoppingItemEntity>>
    
    @Query("UPDATE shopping_items SET sortOrder = :sortOrder WHERE id = :id")
    suspend fun updateItemSortOrder(id: Int, sortOrder: Int)
    
    @Query("SELECT COUNT(*) FROM shopping_items WHERE listId = :listId")
    suspend fun getItemCountForList(listId: Int): Int
    
    @Query("SELECT COUNT(*) FROM shopping_items WHERE listId = :listId AND isCompleted = 1")
    suspend fun getCompletedItemCountForList(listId: Int): Int
    
    @Query("SELECT * FROM shopping_items ORDER BY listId, sortOrder ASC, createdAt ASC")
    fun getAllItems(): Flow<List<ShoppingItemEntity>>
}