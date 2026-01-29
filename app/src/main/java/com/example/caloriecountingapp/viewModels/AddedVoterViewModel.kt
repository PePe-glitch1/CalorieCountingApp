package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AddedVoter
import com.example.domain.repository.AddedVoterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddedVoterViewModel (
    private val addedVoterRepository: AddedVoterRepository,
    private val userId: Long
): ViewModel() {

    private val _addedVoter = MutableStateFlow<List<AddedVoter>>(emptyList())
    val addedVoter: StateFlow<List<AddedVoter>> = _addedVoter.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadAddedVoter()
    }

    private fun loadAddedVoter() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val voters = addedVoterRepository.getAllAddedVoterEntityByUserId(userId)
                _addedVoter.value = voters ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllAddedVoterByUserIdAndDate(date: LocalDateTime): List<AddedVoter> {
        var voters: List<AddedVoter> = emptyList()
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                voters = addedVoterRepository.getAllAddedVoterByUserIdAndDate(userId, date) ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        return voters
    }

    fun getAddedVoterEntityById(id: Long): AddedVoter? {
        var voter: AddedVoter? = null
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                voter = addedVoterRepository.getAddedVoterEntityById(id)
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        return voter
    }

    fun saveAddedVoter(
        date: LocalDateTime,
        addVoterMl: Int
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val voter = AddedVoter(
                    date = date,
                    addVoterMl = addVoterMl
                )
                addedVoterRepository.saveAddedVoter(voter)
                loadAddedVoter()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteAddedVoter(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                addedVoterRepository.deleteAddedVoterById(id)
                loadAddedVoter()
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

    override fun onCleared() {
        super.onCleared()
    }
}

class AddedVoterViewModelFactory(
    private val addedVoterRepository: AddedVoterRepository,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddedVoterViewModel::class.java)) {
            return AddedVoterViewModel(addedVoterRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}