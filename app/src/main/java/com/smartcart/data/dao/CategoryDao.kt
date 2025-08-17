package com.smartcart.data.dao

import androidx.room.*
import com.smartcart.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    
    @Query("SELECT * FROM categories ORDER BY isDefault DESC, name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?
    
    @Insert
    suspend fun insertCategory(category: CategoryEntity): Long
    
    @Insert
    suspend fun insertCategories(categories: List<CategoryEntity>)
    
    @Update
    suspend fun updateCategory(category: CategoryEntity)
    
    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
    
    @Query("DELETE FROM categories WHERE id = :id AND isDefault = 0")
    suspend fun deleteCategoryById(id: Int)
    
    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoryCount(): Int
}