package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AllProducts
import com.example.domain.repository.AllProductsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllProductsViewModel(
    private val allProductsRepository: AllProductsRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _allProducts = MutableStateFlow<List<AllProducts>>(emptyList())
    val allProducts: StateFlow<List<AllProducts>> = _allProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var searchJob: Job? = null

    init {
        searchProducts("")
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                allProductsRepository.searchProducts(query).collect { products ->
                    _allProducts.value = products
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

class AllProductsViewModelFactory(
    private val allProductsRepository: AllProductsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllProductsViewModel::class.java)) {
            return AllProductsViewModel(allProductsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}