package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.AddedVoter
import com.example.domain.repository.AddedVoterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddedVoterViewModel @Inject constructor(
    private val addedVoterRepository: AddedVoterRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId: Long = savedStateHandle["user_id"] ?: 1L

    private val _addedVoters = MutableStateFlow<List<AddedVoter>>(emptyList())
    val addedVoters: StateFlow<List<AddedVoter>> = _addedVoters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadAddedVoters()
    }

    private fun loadAddedVoters() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val voters = addedVoterRepository.getAllAddedVoterEntityByUserId(userId)
                _addedVoters.value = voters ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addVoter(addVoterMl: Int) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val voter = AddedVoter(
                    userId = userId,
                    date = LocalDateTime.now(),
                    addVoterMl = addVoterMl
                )
                addedVoterRepository.saveAddedVoter(voter)
                loadAddedVoters()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteVoter(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                addedVoterRepository.deleteAddedVoterById(id)
                loadAddedVoters()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadVotersByDate(date: LocalDateTime) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val voters = addedVoterRepository.getAllAddedVoterByUserIdAndDate(userId, date)
                _addedVoters.value = voters ?: emptyList()
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