package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.calculationLogic.RecalculateCalorie
import com.example.domain.models.DailyCalory
import com.example.domain.models.User
import com.example.domain.models.UserParams
import com.example.domain.repository.DailyCaloryRepository
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyCaloryViewModel @Inject constructor(
    private val dailyCaloryRepository: DailyCaloryRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId: Long = savedStateHandle["user_id"] ?: 1L

    private val _user = MutableStateFlow<User?>(null)

    private val _dailyCalory = MutableStateFlow<DailyCalory?>(null)
    val dailyCalory: StateFlow<DailyCalory?> = _dailyCalory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUser()
        loadDailyCalory()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _user.value = userRepository.getUserById(userId)
            } catch (_: Exception) { }
        }
    }

    private fun loadDailyCalory() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                _dailyCalory.value = dailyCaloryRepository.gitDailyCaloryByParentId(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveDailyCalory(userParams: UserParams) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                val currentUser = _user.value ?: return@launch
                val dailyCalory = RecalculateCalorie().recalculate(userParams, currentUser)
                if (dailyCalory.id == 0L) {
                    dailyCaloryRepository.saveDailyCalory(dailyCalory)
                } else {
                    dailyCaloryRepository.updateDailyCalory(dailyCalory)
                }
                _dailyCalory.value = dailyCalory
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}