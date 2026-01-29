package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

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
                _errorMessage.value = null
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

    fun saveUser(
        username: String,
        email: String,
        bornData: LocalDate
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val user = User(
                    username = username,
                    email = email,
                    bornData = bornData,
                    age = 0,
                )
                userRepository.saveUser(user)
                loadUser()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
            _isLoading.value = false
        }
        }
    }

    fun updateUser(
        username: String? = null,
        email: String? = null,
        bornData: LocalDate? = null,
        age: Int? = null,
        notificationsEnabled: Boolean? = null
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val currentUser = _user.value ?: return@launch
                val updatedUser = currentUser.copy(
                    username = username ?: currentUser.username,
                    email = email ?: currentUser.email,
                    bornData = bornData ?: currentUser.bornData,
                    age = age ?: currentUser.age,
                    notificationsEnabled = notificationsEnabled ?: currentUser.notificationsEnabled
                )
                userRepository.updateUser(updatedUser)
                loadUser()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteUser(userId: Long = 1L) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
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

class UserViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}