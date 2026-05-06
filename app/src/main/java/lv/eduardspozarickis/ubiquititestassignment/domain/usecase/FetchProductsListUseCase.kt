package lv.eduardspozarickis.ubiquititestassignment.domain.usecase

import lv.eduardspozarickis.ubiquititestassignment.data.remote.ApiResult
import lv.eduardspozarickis.ubiquititestassignment.domain.ProductsRepository
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.Product

class FetchProductsListUseCase(private val repository: ProductsRepository) {

    suspend fun launch(): ApiResult<List<Product>?> {
        return repository.getProductsList()
    }
}