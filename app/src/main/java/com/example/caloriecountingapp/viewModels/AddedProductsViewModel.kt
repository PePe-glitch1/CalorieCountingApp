package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AddedProducts
import com.example.domain.repository.AddedProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddedProductsViewModel (
    private val addedProductsRepository: AddedProductsRepository,
    private val userId: Long
): ViewModel() {

    private val _addedProducts = MutableStateFlow<List<AddedProducts>>(emptyList())
    val addedProducts: StateFlow<List<AddedProducts>> = _addedProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadAddedProducts()
    }

    private fun loadAddedProducts() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val products = addedProductsRepository.getAllAddedProductByUserId(userId)
                _addedProducts.value = products ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllAddedProductByUserIdAndDate(date: LocalDateTime): List<AddedProducts> {
        var products: List<AddedProducts> = emptyList()
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                products = addedProductsRepository.getAllAddedProductByUserIdAndDate(userId, date) ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        return products
    }

    fun getAddedProductById(id: Long): AddedProducts? {
        var product: AddedProducts? = null
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                product = addedProductsRepository.getAddedProductById(id)
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        return product
    }

    fun saveAddedProduct(
        name: String,
        mass: Double,
        calories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                val params = AddedProducts(
                    name = name,
                    mass = mass,
                    calories = calories,
                    proteins = proteins,
                    fats = fats,
                    carbohydrates = carbohydrates
                )
                addedProductsRepository.saveAddedProduct(params)
                loadAddedProducts()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            }
        }
    }

    fun deleteAddedProductById(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                addedProductsRepository.deleteAddedProductById(id)
                loadAddedProducts()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class AddedProductsViewModelFactory(
    private val addedProductsRepository: AddedProductsRepository,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddedProductsViewModel::class.java)) {
            return AddedProductsViewModel(addedProductsRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}