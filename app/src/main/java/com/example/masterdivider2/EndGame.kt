package com.example.masterdivider2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.masterdivider2.databinding.ActivityEndGameBinding
import com.example.masterdivider2.databinding.ActivityMainBinding
import com.example.masterdivider2.databinding.ActivityMenuBinding

class EndGame : AppCompatActivity() {
    private lateinit var binding: ActivityEndGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)
        binding = ActivityEndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startOverButton.setOnClickListener { _ ->
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        };
    }
}