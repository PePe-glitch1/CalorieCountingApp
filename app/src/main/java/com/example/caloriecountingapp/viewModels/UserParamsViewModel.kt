package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight
import com.example.domain.models.UserParams
import com.example.domain.repository.UserParamsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserParamsViewModel (
    private val userParamsRepository: UserParamsRepository,
    private val userId: Long
): ViewModel() {

    private val _userParams = MutableStateFlow<UserParams?>(null)
    val userParams:StateFlow<UserParams?> = _userParams.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUserParams()
    }

    private fun loadUserParams() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val params = userParamsRepository.getUserParamsByUserId(userId)
                _userParams.value = params
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }

    }

    fun saveUserParams(
        isMale: Boolean,
        metricMass: MetriсMass,
        mass: Double,
        metricHeight: MetriсHeight,
        height: Double,
        activityLevel: LifeActivityLevel,
        target: TargetInWeight
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val params = UserParams (
                    isMale = isMale,
                    metricMass = metricMass,
                    mass = mass,
                    metricHeight = metricHeight,
                    height = height,
                    activityLevel = activityLevel,
                    target = target,
                )
                clearError()
                userParamsRepository.saveUserParams(params)
                loadUserParams()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            }
        }
    }

    fun updateUserParams(
        isMale: Boolean? = null,
        metricMass: MetriсMass? = null,
        mass: Double? = null,
        metricHeight: MetriсHeight? = null,
        height: Double? = null,
        activityLevel: LifeActivityLevel? = null,
        target: TargetInWeight? = null
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val params = _userParams.value ?: return@launch
                val updatedParams = params.copy(
                    isMale = isMale ?: params.isMale,
                    metricMass = metricMass ?: params.metricMass,
                    mass = mass ?: params.mass,
                    metricHeight = metricHeight ?: params.metricHeight,
                    height = height ?: params.height,
                    activityLevel = activityLevel ?: params.activityLevel,
                    target = target ?: params.target
                )
                userParamsRepository.updateUserParams(updatedParams)
                loadUserParams()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            }
        }
    }

    fun deleteUserParams(userParamsId: Long) {
        viewModelScope.launch {
            try {
                clearError()
                userParamsRepository.deleteUserParamsById(userParamsId)
                loadUserParams()
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

class UserParamsViewModelFactory(
    private val userParamsRepository: UserParamsRepository,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserParamsViewModel::class.java)) {
            return UserParamsViewModel(userParamsRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}