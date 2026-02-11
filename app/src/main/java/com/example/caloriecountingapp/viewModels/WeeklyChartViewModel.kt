package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriecountingapp.screenElements.DayNutrition
import com.example.domain.repository.AddedProductsRepository
import com.example.domain.repository.AddedVoterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class WeeklyChartViewModel @Inject constructor(
    private val addedProductsRepository: AddedProductsRepository,
    private val addedVoterRepository: AddedVoterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: Long = savedStateHandle["user_id"] ?: 1L

    private val _weekData = MutableStateFlow<List<DayNutrition>>(emptyList())
    val weekData: StateFlow<List<DayNutrition>> = _weekData.asStateFlow()

    private val _selectedDayIndex = MutableStateFlow(-1)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadWeekData()
    }

    fun loadWeekData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val today = LocalDate.now()
                // Неділя як початок тижня
                val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

                val from = startOfWeek.atStartOfDay()
                val to = startOfWeek.plusDays(7).atStartOfDay()

                val products = addedProductsRepository.getProductsInDateRange(userId, from, to)
                val voters = addedVoterRepository.getVotersInDateRange(userId, from, to)

                // Групуємо по дню
                val productsByDay = products.groupBy { it.date.toLocalDate() }
                val votersByDay = voters.groupBy { it.date.toLocalDate() }

                val dayLabels = listOf("S", "M", "T", "W", "T", "F", "S")

                val weekList = (0..6).map { offset ->
                    val date = startOfWeek.plusDays(offset.toLong())
                    val dayProducts = productsByDay[date] ?: emptyList()
                    val dayVoters = votersByDay[date] ?: emptyList()

                    DayNutrition(
                        dayLabel = dayLabels[offset],
                        calories = dayProducts.sumOf { it.calories }.toFloat(),
                        fat = dayProducts.sumOf { it.fats }.toFloat(),
                        carbs = dayProducts.sumOf { it.carbohydrates }.toFloat(),
                        protein = dayProducts.sumOf { it.proteins }.toFloat(),
                        water = dayVoters.sumOf { it.addVoterMl }.toFloat()
                    )
                }

                _weekData.value = weekList

                // Виділити сьогоднішній день
                val todayOffset = today.toEpochDay() - startOfWeek.toEpochDay()
                if (todayOffset in 0..6) {
                    _selectedDayIndex.value = todayOffset.toInt()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectDay(index: Int) {
        _selectedDayIndex.value = index
    }

    fun clearError() {
        _errorMessage.value = null
    }
}