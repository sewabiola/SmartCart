package com.smartcart.data.dao

import androidx.room.*
import com.smartcart.data.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    
    @Query("SELECT * FROM shopping_lists ORDER BY updatedAt DESC")
    fun getAllLists(): Flow<List<ShoppingListEntity>>
    
    @Query("SELECT * FROM shopping_lists WHERE id = :id")
    suspend fun getListById(id: Int): ShoppingListEntity?
    
    @Insert
    suspend fun insertList(list: ShoppingListEntity): Long
    
    @Update
    suspend fun updateList(list: ShoppingListEntity)
    
    @Delete
    suspend fun deleteList(list: ShoppingListEntity)
    
    @Query("DELETE FROM shopping_lists WHERE id = :id")
    suspend fun deleteListById(id: Int)
    
    @Query("SELECT * FROM shopping_lists WHERE isCompleted = :isCompleted ORDER BY updatedAt DESC")
    fun getListsByCompletionStatus(isCompleted: Boolean): Flow<List<ShoppingListEntity>>
}