package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AllProducts
import com.example.domain.repository.AllProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AllProductsViewModel @Inject constructor(
    private val allProductsRepository: AllProductsRepository,
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val allProducts: StateFlow<List<AllProducts>> = _searchQuery
        .flatMapLatest { query ->
            allProductsRepository.searchProducts(query)
                .onStart { _errorMessage.value = null }
                .catch { e ->
                    _errorMessage.value = "Error ${e.message}"
                    emit(emptyList())
                }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
