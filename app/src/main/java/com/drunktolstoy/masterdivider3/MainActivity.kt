package com.drunktolstoy.masterdivider3

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.drunktolstoy.masterdivider3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private fun updateNextQuestion(nextQuestion: Number) {
        binding.question.text = nextQuestion.toString()
        binding.input.text.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[GameManagerViewModel::class.java]
        viewModel.initGame()

        binding.input.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val ans = binding.input.text.toString()
                if (ans.isNotBlank()) {
                    val ansInInt = ans.toInt()
                    viewModel.onAnswer(ansInInt)
                    return@OnKeyListener true
                }
            }
            false
        })

        viewModel.resultObserver.observe(this) { status ->
            if (status == STATUS.GAME_END) {
                intent = Intent(this, EndGame::class.java)
                intent.putExtra("score", viewModel.points.value);
                startActivity(intent)
            }
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
            scoreBarTransition.startTransition(1500)
            Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.numberInQuestion.observe(this) { numberInQuestion ->
            updateNextQuestion(numberInQuestion)
        }
        viewModel.timerTime.observe(this) { value ->
            binding.timerLabel.text = value.toString()
        }

        viewModel.points.observe(this) { value ->
            binding.scoreLabel.text = value.toString()
        }
        viewModel.livesLeft.observe(this) { livesNumber ->
            binding.heartBox.removeAllViews()
            for (i in 1..livesNumber) {
                val imageView = ImageView(this)
                imageView.layoutParams = ViewGroup.LayoutParams(120,130)
                imageView.id =  i+1;
                imageView.setImageResource(R.drawable.heart)
                binding.heartBox.addView(imageView)
            }
        }
    }
}