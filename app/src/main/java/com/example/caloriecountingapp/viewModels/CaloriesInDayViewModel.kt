package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CaloriesInDay
import com.example.domain.repository.CaloriesInDayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CaloriesInDayViewModel(
    private val caloriesInDayRepository: CaloriesInDayRepository,
    private val userId: Long
) : ViewModel() {

    private val _caloriesInDay = MutableStateFlow<CaloriesInDay?>(null)
    val caloriesInDay: StateFlow<CaloriesInDay?> = _caloriesInDay.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadCaloriesInDay()
    }

    private fun loadCaloriesInDay() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val caloriesInDay = caloriesInDayRepository.getCaloriesInDayByUserIdAndDate(userId)
                val today = LocalDate.now()

                if (caloriesInDay == null || caloriesInDay.date.isBefore(today)) {
                    createNewDayRecord()
                } else {
                    _caloriesInDay.value = caloriesInDay
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun createNewDayRecord() {
        val newCaloriesInDay = CaloriesInDay(
            userId = userId,
            date = LocalDate.now(),
            addTotalCalories = 0.0,
            addTotalProteins = 0.0,
            addTotalFats = 0.0,
            addTotalCarbohydrates = 0.0
        )
        val id = caloriesInDayRepository.saveCaloriesInDay(newCaloriesInDay)
        _caloriesInDay.value = newCaloriesInDay.copy(id = id)
    }

    fun addCalories(
        calories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double
    ) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val current = _caloriesInDay.value ?: return@launch

                val updatedCaloriesInDay = current.copy(
                    addTotalCalories = current.addTotalCalories + calories,
                    addTotalProteins = current.addTotalProteins + proteins,
                    addTotalFats = current.addTotalFats + fats,
                    addTotalCarbohydrates = current.addTotalCarbohydrates + carbohydrates
                )
                caloriesInDayRepository.updateCaloriesInDay(updatedCaloriesInDay)
                _caloriesInDay.value = updatedCaloriesInDay
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetDayCalories() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val current = _caloriesInDay.value ?: return@launch

                val resetCaloriesInDay = current.copy(
                    addTotalCalories = 0.0,
                    addTotalProteins = 0.0,
                    addTotalFats = 0.0,
                    addTotalCarbohydrates = 0.0
                )
                caloriesInDayRepository.updateCaloriesInDay(resetCaloriesInDay)
                _caloriesInDay.value = resetCaloriesInDay
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCaloriesInDay(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                caloriesInDayRepository.deleteCaloriesInDayById(id)
                _caloriesInDay.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        loadCaloriesInDay()
    }

    fun clearError() {
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
    }
}



class CaloriesInDayViewModelFactory(
    private val caloriesInDayRepository: CaloriesInDayRepository,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CaloriesInDayViewModel::class.java)) {
            return CaloriesInDayViewModel(caloriesInDayRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}