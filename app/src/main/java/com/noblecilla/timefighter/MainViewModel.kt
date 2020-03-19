package com.noblecilla.timefighter

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private lateinit var countDownTimer: CountDownTimer

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long> = _time

    private val _isGameOver = MutableLiveData<Boolean>()
    val isGameOver: LiveData<Boolean> = _isGameOver

    private val _isGameStart = MutableLiveData<Boolean>().apply { value = false }

    fun play() {
        _isGameStart.value?.let {
            if (!it) resetGame() else restoreGame()
        }
    }

    fun score() {
        _isGameStart.value?.let {
            if (!it) startGame()
        }

        _score.value = _score.value?.plus(1)
    }

    fun pause() {
        countDownTimer.cancel()
    }

    private fun startGame() {
        _isGameStart.value = true
        countDownTimer.start()
    }

    private fun resetGame() {
        _isGameOver.value = false

        _score.value = 0
        _time.value = 60000

        _time.value?.let {
            countDownTimer = object : CountDownTimer(it, 1000) {
                override fun onFinish() {
                    endGame()
                }

                override fun onTick(millisUntilFinished: Long) {
                    _time.value = millisUntilFinished
                }
            }
        }

        _isGameStart.value = false
    }

    private fun restoreGame() {
        _time.value?.let {
            countDownTimer = object : CountDownTimer(it, 1000) {
                override fun onFinish() {
                    endGame()
                }

                override fun onTick(millisUntilFinished: Long) {
                    _time.value = millisUntilFinished
                }

            }
        }

        startGame()
    }

    private fun endGame() {
        _isGameOver.value = true
        resetGame()
    }
}
