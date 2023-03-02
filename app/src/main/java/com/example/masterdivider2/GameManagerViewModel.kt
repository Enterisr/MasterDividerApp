package com.example.masterdivider2;

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

val numberToDivideBy = intArrayOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)

class GameManagerViewModel : ViewModel() {
    var points = MutableLiveData(0);
    private var time = MutableLiveData<Long>();
    val timerTime: LiveData<Long> get() = time;
    private var timer = object : CountDownTimer(20000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            time.value = millisUntilFinished
        }

        override fun onFinish() {
            onFail();
        }
    }
    private var numberInQuestion: Int = 0

    fun initGame() {
        timer.start()
        initRound()
    }

    private fun initRound(): Int {
        val rndIndex = (numberToDivideBy.indices).random();
        val rndMultiplier = (0..2000).random();
        val rndToChooseFromArray = (0..10).random()
        if (rndToChooseFromArray > 7) {
            numberInQuestion = (0..1000).random()
        }
        numberInQuestion = numberToDivideBy[rndIndex] * rndMultiplier;
        return numberInQuestion;
    }

    fun onAnswer(ans: Int): Int {
        if (numberInQuestion % ans == 0) {
            points.value = points.value!! + 1;
        } else {
            onFail();
        }
        return initRound();
    }

    fun onFail() {
        println("fail");
    }
}
