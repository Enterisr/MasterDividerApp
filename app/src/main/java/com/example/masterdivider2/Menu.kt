package com.example.masterdivider2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.example.masterdivider2.databinding.ActivityMainBinding
import com.example.masterdivider2.databinding.ActivityMenuBinding

class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { _ ->
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        };
    }
}