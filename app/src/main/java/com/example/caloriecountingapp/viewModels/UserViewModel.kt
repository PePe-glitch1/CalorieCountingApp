package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId: Long = savedStateHandle["user_id"] ?: 1L

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                _user.value = userRepository.getUserById(userId)
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
                    age = Period.between(bornData, LocalDate.now()).years,
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
        notificationsEnabled: Boolean? = null
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val currentUser = _user.value ?: return@launch
                val newBornData = bornData ?: currentUser.bornData
                val updatedUser = currentUser.copy(
                    username = username ?: currentUser.username,
                    email = email ?: currentUser.email,
                    bornData = newBornData,
                    age = Period.between(newBornData, LocalDate.now()).years,
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

    fun deleteUser() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                userRepository.deleteUserById(userId)
                _user.value = null
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
