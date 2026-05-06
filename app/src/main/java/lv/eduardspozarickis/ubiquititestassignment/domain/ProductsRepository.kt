package lv.eduardspozarickis.ubiquititestassignment.domain

import lv.eduardspozarickis.ubiquititestassignment.data.local.LocalDataStore
import lv.eduardspozarickis.ubiquititestassignment.data.mapper.toData
import lv.eduardspozarickis.ubiquititestassignment.data.mapper.toDomain
import lv.eduardspozarickis.ubiquititestassignment.data.remote.Api
import lv.eduardspozarickis.ubiquititestassignment.data.remote.ApiHandler.handleApi
import lv.eduardspozarickis.ubiquititestassignment.data.remote.ApiResult
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.Product

class ProductsRepository(
    private val api: Api,
    private val localStore: LocalDataStore
) {

    companion object {
        private const val CACHE_EXPIRATION_TIME = 24 * 60 * 60 * 1000L
    }

    suspend fun getProductsList(): ApiResult<List<Product>?> {
        val productsStoredTime = localStore.getProductsStoredTime()
        val isCacheExpired =
            (System.currentTimeMillis() - productsStoredTime) > CACHE_EXPIRATION_TIME

        val localResult = localStore.getAllProducts()

        if (!isCacheExpired && localResult.isNotEmpty()) {
            return ApiResult.Success(localResult.map { it.toDomain() })
        }

        val result = handleApi { api.getProducts() }

        when (result) {
            is ApiResult.Success -> {
                val productsToStore = result.data.devices
                    .map { it.toData() }
                localStore.storeProducts(productsToStore)

                val productsList = result.data.devices
                    .map { it.toDomain() }
                return ApiResult.Success(productsList)
            }

            is ApiResult.Error -> {
                return result
            }
        }
    }
}