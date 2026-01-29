package com.example.caloriecountingapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CaloriesInDay
import com.example.domain.models.VoterInDay
import com.example.domain.repository.VoterInDayRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate


class VoterInDayViewModel (
    private val voterInDayRepository: VoterInDayRepository,
    private val userId: Long
): ViewModel() {

    private val _voterInDay = MutableStateFlow<VoterInDay?>(null)
    val voterInDay: StateFlow<VoterInDay?> = _voterInDay.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadVoterInDay()
    }

    private fun loadVoterInDay() {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                val voterInDay = voterInDayRepository.getVoterInDayByUserIdAndDate(userId)
                val today = LocalDate.now()

                if (voterInDay == null || voterInDay.date.isBefore(today)) {
                    createNewDayRecord()
                } else {
                    _voterInDay.value = voterInDay
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun createNewDayRecord() {
        val newVoterInDay = VoterInDay(
            userId = userId,
            date = LocalDate.now(),
            votersMl = 0
        )
        voterInDayRepository.addVoterInDay(newVoterInDay)
        _voterInDay.value = newVoterInDay
    }

    fun addVotersMl(votersToAdd: Int) {
        val currentVoterInDay = _voterInDay.value
        if (currentVoterInDay != null) {
            val updatedVotersMl = currentVoterInDay.votersMl + votersToAdd
            val updatedVoterInDay = currentVoterInDay.copy(votersMl = updatedVotersMl)

            viewModelScope.launch {
                try {
                    _errorMessage.value = null
                    _isLoading.value = true
                    voterInDayRepository.updateVoterInDay(updatedVoterInDay)
                    _voterInDay.value = updatedVoterInDay
                } catch (e: Exception) {
                    _errorMessage.value = "Error ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun deleteVoterInDay(id: Long) {
        viewModelScope.launch {
            try {
                _errorMessage.value = null
                _isLoading.value = true
                voterInDayRepository.deleteVoterInDayById(id)
                _voterInDay.value = null
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

    override fun onCleared() {
        super.onCleared()
    }
}

class VoterInDayViewModelFactory(
    private val voterInDayRepository: VoterInDayRepository,
    private val userId: Long
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VoterInDayViewModel::class.java)) {
            return VoterInDayViewModel(voterInDayRepository, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
