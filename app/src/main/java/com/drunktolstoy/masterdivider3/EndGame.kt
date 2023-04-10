package com.drunktolstoy.masterdivider3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.drunktolstoy.masterdivider3.databinding.ActivityEndGameBinding


class EndGame : AppCompatActivity() {
    private lateinit var binding: ActivityEndGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)
        binding = ActivityEndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val score = intent.getIntExtra("score", 0)
        binding.scoreGameOverLabel.text =
            String.format(getString(R.string.score_label_endgame), score)
        binding.startOverButton.setOnClickListener { _ ->
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        };
    }
}