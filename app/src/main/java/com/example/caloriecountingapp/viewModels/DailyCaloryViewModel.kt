package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.calculationLogic.RecalculateCalorie
import com.example.domain.models.DailyCalory
import com.example.domain.repository.DailyCaloryRepository
import com.example.domain.repository.UserParamsRepository
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DailyCaloryViewModel(
    private val dailyCaloryRepository: DailyCaloryRepository,
    private val userRepository: UserRepository,
    private val userParamsRepository: UserParamsRepository,
    private val userId: Long
) : ViewModel() {

    private val _dailyCalory = MutableStateFlow<DailyCalory?>(null)
    val dailyCalory: StateFlow<DailyCalory?> = _dailyCalory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val recalculateCalorie = RecalculateCalorie()

    init {
        loadDailyCalory()
    }

    private fun loadDailyCalory() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val dailyCalory = dailyCaloryRepository.gitDailyCaloryByParentId(userId)

                if (dailyCalory != null) {
                    _dailyCalory.value = dailyCalory
                } else {
                    generateDailyCalory()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun generateDailyCalory() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true

                val user = userRepository.getUserById(userId)
                val userParams = userParamsRepository.getUserParamsByUserId(userId)

                if (user == null || userParams == null) {
                    _errorMessage.value = "User or UserParams not found"
                    return@launch
                }

                val calculatedDailyCalory = recalculateCalorie.recalculate(userParams, user)

                val existingDailyCalory = dailyCaloryRepository.gitDailyCaloryByParentId(userId)

                if (existingDailyCalory != null) {
                    val updatedDailyCalory = calculatedDailyCalory.copy(
                        id = existingDailyCalory.id,
                        parentId = userId
                    )
                    dailyCaloryRepository.updateDailyCalory(updatedDailyCalory)
                    _dailyCalory.value = updatedDailyCalory
                } else {
                    val newDailyCalory = calculatedDailyCalory.copy(parentId = userId)
                    val id = dailyCaloryRepository.saveDailyCalory(newDailyCalory)
                    _dailyCalory.value = newDailyCalory.copy(id = id)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun recalculate() {
        generateDailyCalory()
    }

    fun deleteDailyCalory(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                dailyCaloryRepository.deleteDailyCaloryById(id)
                _dailyCalory.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadDailyCalory()
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

class DailyCaloryViewModelFactory(
    private val dailyCaloryRepository: DailyCaloryRepository,
    private val userRepository: UserRepository,
    private val userParamsRepository: UserParamsRepository,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyCaloryViewModel::class.java)) {
            return DailyCaloryViewModel(
                dailyCaloryRepository,
                userRepository,
                userParamsRepository,
                userId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}