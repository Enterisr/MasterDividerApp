package com.example.masterdivider2

import androidx.lifecycle.MutableLiveData

enum class STATUS(val message:String) {
    ONE_IS_NOT_ALLOWED("Simple number is not allowed"),
    NOT_DIVIDED_BY_INPUT("this number is not divided by your answer"),
    TIMES_UP("times up!"),
    CORRECT("nice!")
}

fun validateAnswer(userAns: Int, numberInQuestion: MutableLiveData<Int>): STATUS {
    if (userAns == 1 || userAns == 0) {
        return STATUS.ONE_IS_NOT_ALLOWED;
    } else if (numberInQuestion.value!! % userAns == 0) {
        return STATUS.CORRECT;
    }
    return STATUS.NOT_DIVIDED_BY_INPUT;
}