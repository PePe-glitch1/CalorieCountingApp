package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser(userId: Long = 1L) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val users = userRepository.getUserById(userId)
                _user.value = users
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun sateUser(user: User) {
        viewModelScope.launch {
            try {
                userRepository.saveUser(user)
                loadUser()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                userRepository.updateUser(user)
                loadUser()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            }
        }
    }

    fun deleteUser(userId: Long) {
        viewModelScope.launch {
            try {
                userRepository.deleteUserById(userId)
                _user.value = null
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