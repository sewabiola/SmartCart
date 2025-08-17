package com.smartcart.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.smartcart.data.dao.CategoryDao
import com.smartcart.data.dao.ShoppingItemDao
import com.smartcart.data.dao.ShoppingListDao
import com.smartcart.data.entity.CategoryEntity
import com.smartcart.data.entity.ShoppingItemEntity
import com.smartcart.data.entity.ShoppingListEntity

@Database(
    entities = [ShoppingListEntity::class, ShoppingItemEntity::class, CategoryEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SmartCartDatabase : RoomDatabase() {
    
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun shoppingItemDao(): ShoppingItemDao
    abstract fun categoryDao(): CategoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: SmartCartDatabase? = null
        
        fun getDatabase(context: Context): SmartCartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartCartDatabase::class.java,
                    "smartcart_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}