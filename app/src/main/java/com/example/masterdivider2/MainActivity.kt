package com.example.masterdivider2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.masterdivider2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this).get(GameManagerViewModel::class.java);
        viewModel.initGame();

        binding.input.addTextChangedListener {
            val ans = binding.input.text.toString();
            if (ans.isNotBlank()) {
                val ansInInt = ans.toInt();
                val nextQuestion = viewModel.onAnswer(ansInInt)
                binding.question.text = nextQuestion.toString()
            }
        }

        viewModel.timerTime.observe(this) { value ->
            binding.timerLabel.text = value.toString().substring(0, 2);
        }

        viewModel.points.observe(this) { value ->
            binding.scoreLabel.text = value.toString()
        }
    }
}