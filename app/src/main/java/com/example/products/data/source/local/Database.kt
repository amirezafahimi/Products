package com.example.products.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.findpix.data.source.local.AppDao
import com.example.products.data.source.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun appDao(): AppDao
}