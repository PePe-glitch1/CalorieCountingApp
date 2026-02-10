package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AddedProducts
import com.example.domain.repository.AddedProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddedProductsViewModel @Inject constructor(
    private val addedProductsRepository: AddedProductsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId: Long = savedStateHandle["user_id"] ?: 1L

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

    fun addProduct(
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
                _isLoading.value = true
                val product = AddedProducts(
                    userId = userId,
                    date = LocalDateTime.now(),
                    name = name,
                    mass = mass,
                    calories = calories,
                    proteins = proteins,
                    fats = fats,
                    carbohydrates = carbohydrates
                )
                addedProductsRepository.saveAddedProduct(product)
                loadAddedProducts()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                addedProductsRepository.deleteAddedProductById(id)
                loadAddedProducts()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductsByDate(date: LocalDateTime) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val products = addedProductsRepository.getAllAddedProductByUserIdAndDate(userId, date)
                _addedProducts.value = products ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
