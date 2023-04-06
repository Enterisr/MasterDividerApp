package com.example.masterdivider2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.masterdivider2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private fun updateNextQuestion(nextQuestion: Number) {
        binding.question.text = nextQuestion.toString()
        binding.input.text.clear();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[GameManagerViewModel::class.java];
        binding.input.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val ans = binding.input.text.toString();
                if (ans.isNotBlank()) {
                    val ansInInt = ans.toInt();
                    viewModel.onAnswer(ansInInt)
                    return@OnKeyListener true
                }
            }
            false
        });
        viewModel.resultObserver.observe(this) { status ->
            var scoreBarBackgroundColors = arrayOf(
                ColorDrawable(resources.getColor(R.color.RED)),
                ColorDrawable(resources.getColor(R.color.scoreBar))
            )
            if (status == STATUS.CORRECT) {
                scoreBarBackgroundColors = arrayOf(
                    ColorDrawable(resources.getColor(R.color.scoreBarHighlight)),
                    ColorDrawable(resources.getColor(R.color.scoreBar))
                )
            }
            val scoreBarTransition = TransitionDrawable(scoreBarBackgroundColors)
            binding.scoreBar.background = scoreBarTransition
            scoreBarTransition.startTransition(1000)

            Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show();
        }
        viewModel.numberInQuestion.observe(this) { numberInQuestion ->
            updateNextQuestion(numberInQuestion)
        }
        viewModel.timerTime.observe(this) { value ->
            binding.timerLabel.text = value.toString();
        }

        viewModel.points.observe(this) { value ->
            binding.scoreLabel.text = value.toString()
        }
    }
}