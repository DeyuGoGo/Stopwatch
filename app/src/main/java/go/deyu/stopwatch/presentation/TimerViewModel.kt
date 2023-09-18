package go.deyu.stopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import go.deyu.stopwatch.domain.PlaySoundUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val playSoundUseCase: PlaySoundUseCase
) : ViewModel() {
    private fun playSystemSound() {
        playSoundUseCase.playNotificationSound()
    }

    private val _timeLeft = MutableStateFlow("00:00")
    val timeLeft: StateFlow<String> get() = _timeLeft

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> get() = _isRunning

    private var timerJob: Job? = null

    fun setTimer(timeInSeconds: Int) {
        timerJob?.cancel()
        _timeLeft.value = String.format("%02d:%02d", timeInSeconds / 60, timeInSeconds % 60)
    }

    private fun startTimer(initialTimeInSeconds: Int) {
        timerJob?.cancel() // If there is already a timer running, cancel it
        _isRunning.value = true

        var remainingSeconds = initialTimeInSeconds

        timerJob = viewModelScope.launch {
            while (remainingSeconds > 0) {
                delay(1000) // wait for 1 second
                remainingSeconds -= 1
                _timeLeft.value =
                    String.format("%02d:%02d", remainingSeconds / 60, remainingSeconds % 60)
            }
            playSystemSound()
            _isRunning.value = false
        }
    }

    fun toggleTimer() {
        if (_isRunning.value) {
            timerJob?.cancel()
            _isRunning.value = false
        } else {
            val currentTime = _timeLeft.value.split(":").let { it[0].toInt() * 60 + it[1].toInt() }
            startTimer(currentTime)
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _isRunning.value = false
        _timeLeft.value = "00:00"
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel() // Ensure the timer is stopped when ViewModel is cleared
    }
}
