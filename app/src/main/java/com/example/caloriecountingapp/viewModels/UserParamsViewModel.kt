package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.calculationLogic.LifeActivityLevel
import com.example.domain.calculationLogic.MetriсHeight
import com.example.domain.calculationLogic.MetriсMass
import com.example.domain.calculationLogic.TargetInWeight
import com.example.domain.models.UserParams
import com.example.domain.repository.UserParamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserParamsViewModel @Inject constructor(
    private val userParamsRepository: UserParamsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val userId: Long = savedStateHandle["user_id"] ?: 1L

    private val _userParams = MutableStateFlow<UserParams?>(null)
    val userParams: StateFlow<UserParams?> = _userParams.asStateFlow()

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
                _isLoading.value = true
                _errorMessage.value = null
                _userParams.value = userParamsRepository.getUserParamsByUserId(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveUserParams(
        isMale: Boolean,
        metricMass: String,
        mass: Double,
        metricHeight: String,
        height: Double,
        activityLevel: String,
        target: String
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val params = UserParams(
                    userId = userId,
                    isMale = isMale,
                    metricMass = when (metricMass) {
                        "kg" -> MetriсMass.MassInKg
                        "lb" -> MetriсMass.MassInLd
                        else -> throw IllegalArgumentException("Invalid mass metric")
                    },
                    mass = mass,
                    metricHeight = when (metricHeight) {
                        "cm" -> MetriсHeight.HeightInCm
                        "in" -> MetriсHeight.HeightInInch
                        else -> throw IllegalArgumentException("Invalid height metric")
                    },
                    height = height,
                    activityLevel = when (activityLevel) {
                        "Low" -> LifeActivityLevel.Minimum
                        "Light" -> LifeActivityLevel.Light
                        "Middle" -> LifeActivityLevel.Middle
                        "Active" -> LifeActivityLevel.Active
                        "Very Active" -> LifeActivityLevel.VeryActive
                        else -> throw IllegalArgumentException("Invalid activity level")
                    },
                    target = when (target) {
                        "Lose Weight" -> TargetInWeight.LoseWeight
                        "Maintain Weight" -> TargetInWeight.MaintinWeight
                        "Gain Weight" -> TargetInWeight.GainWeight
                        else -> throw IllegalArgumentException("Invalid target")
                    }
                )

                if (_userParams.value == null) {
                    userParamsRepository.saveUserParams(params)
                } else {
                    userParamsRepository.updateUserParams(params)
                }
                loadUserParams()
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}