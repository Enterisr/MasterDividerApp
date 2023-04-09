package com.example.masterdivider3;

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameManagerViewModel : ViewModel() {
    var livesLeft = MutableLiveData(Consts.LIVES_NUMBER)
    var points = MutableLiveData(0)
    private var time = MutableLiveData<Long>()
    private var _resultObserver = MutableLiveData<STATUS>()
    var numberInQuestion = MutableLiveData(0)
    val resultObserver get() = _resultObserver
    val timerTime: LiveData<Long> get() = time
    private var timer = object : CountDownTimer(6000, 10) {
        override fun onTick(millisUntilFinished: Long) {
            time.value = millisUntilFinished / 1000
        }

        override fun onFinish() {
            setStatus(STATUS.TIMES_UP);
            time.value = 0;
        }
    }

    fun initGame() {
        timer.start()
        initRound()
    }

    private fun initRound() {
        timer.cancel();
        timer.start();
        val rndIndex = (Consts.NUMBERS_TO_DIVIDE_BY.indices).random();
        val rndMultiplier = (0..2000).random();
        val rndToChooseFromArray = (0..10).random()
        if (rndToChooseFromArray > 7) {
            numberInQuestion.value = (0..1000).random()
        }
        numberInQuestion.value = Consts.NUMBERS_TO_DIVIDE_BY[rndIndex] * rndMultiplier;

    }

    fun onAnswer(ans: Int) {
        val validationResult = validateAnswer(ans, numberInQuestion);
        setStatus(validationResult)
    }

    private fun setStatus(status: STATUS) {
        resultObserver.value = status
        if (status == STATUS.CORRECT) {
            points.value = points.value!! + 1
        } else if(livesLeft.value==1) {
            resultObserver.value = STATUS.GAME_END
            return
        }
        else{
            livesLeft.value = livesLeft.value!! - 1
            points.value = points.value?.minus(1)
        }
        initRound()
    }

}
