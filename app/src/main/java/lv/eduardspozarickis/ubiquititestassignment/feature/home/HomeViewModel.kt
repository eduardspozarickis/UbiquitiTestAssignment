package lv.eduardspozarickis.ubiquititestassignment.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lv.eduardspozarickis.ubiquititestassignment.data.remote.ApiResult
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.Product
import lv.eduardspozarickis.ubiquititestassignment.domain.entity.ProductCategory
import lv.eduardspozarickis.ubiquititestassignment.domain.usecase.FetchProductsListUseCase

class HomeViewModel(
    private val fetchProductsListUseCase: FetchProductsListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = withContext(Dispatchers.IO) {
                fetchProductsListUseCase.launch()
            }

            when (result) {
                is ApiResult.Success -> {
                    val products = result.data ?: emptyList()

                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            products = products,
                            displayProducts = filterProducts(
                                products,
                                currentState.selectedCategory
                            ),
                            error = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result) }
                }
            }
        }
    }

    fun onCategoryClicked(category: ProductCategory) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    selectedCategory = category,
                    displayProducts = ArrayList(filterProducts(currentState.products, category))
                )
            }
        }
    }

    private fun filterProducts(list: List<Product>, category: ProductCategory): List<Product> {
        return when (category) {
            ProductCategory.ALL -> list
            ProductCategory.CAMERA -> {
                list.filter {
                    it.deviceType.equals(ProductCategory.CAMERA.name, ignoreCase = true)
                }
            }
            else -> {
                list.filter {
                    !it.deviceType.equals(ProductCategory.CAMERA.name, ignoreCase = true)
                }
            }
        }
    }

    data class HomeUiState(
        val isLoading: Boolean = false,
        val selectedCategory: ProductCategory = ProductCategory.ALL,
        val products: List<Product> = emptyList(),
        val displayProducts: List<Product> = emptyList(),
        val error: ApiResult.Error? = null
    )
}