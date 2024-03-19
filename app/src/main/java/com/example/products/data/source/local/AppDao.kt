package com.example.findpix.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.products.data.source.local.entity.ProductEntity

@Dao
interface AppDao {

    @Transaction
    fun insertAndClear(products: List<ProductEntity>) {
        // Clear previous records
        //clearProducts()
        // Insert new list of products
        insertProducts(products)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    fun clearProducts()

    @Query("SELECT * FROM products")
    fun getLastProducts(): List<ProductEntity>?

}