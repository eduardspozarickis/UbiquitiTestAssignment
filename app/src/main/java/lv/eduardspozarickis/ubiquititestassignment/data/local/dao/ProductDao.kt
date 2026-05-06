package lv.eduardspozarickis.ubiquititestassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import lv.eduardspozarickis.ubiquititestassignment.data.local.entity.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM product")
    suspend fun getAll(): List<Product>

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}