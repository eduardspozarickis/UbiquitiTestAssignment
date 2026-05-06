package lv.eduardspozarickis.ubiquititestassignment.data.local

import android.content.SharedPreferences
import lv.eduardspozarickis.ubiquititestassignment.data.local.dao.ProductDao
import lv.eduardspozarickis.ubiquititestassignment.data.local.entity.Product
import androidx.core.content.edit

class LocalDataStore(
    private val productDao: ProductDao,
    private val sharedPrefs: SharedPreferences
) {

    companion object {
        private const val KEY_PRODUCTS_STORED_TIME = "products_stored_time"
    }

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAll()
    }

    suspend fun storeProducts(products: List<Product>) {
        clearProducts()
        productDao.insertAll(products)
        saveProductsStoredTime(System.currentTimeMillis())
    }

    suspend fun clearProducts() {
        productDao.deleteAll()
    }

    fun getProductsStoredTime(): Long {
        return sharedPrefs.getLong(KEY_PRODUCTS_STORED_TIME, 0L)
    }

    private fun saveProductsStoredTime(timestamp: Long) {
        sharedPrefs.edit { putLong(KEY_PRODUCTS_STORED_TIME, timestamp) }
    }
}