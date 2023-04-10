package com.drunktolstoy.masterdivider3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drunktolstoy.masterdivider3.databinding.ActivityMenuBinding
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import com.google.firebase.auth.FirebaseAuth


class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        PlayGamesSdk.initialize(this);
        val gamesSignInClient = PlayGames.getGamesSignInClient(this)
        gamesSignInClient.signIn()
            .addOnCompleteListener { _ ->
                gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthedTask1 ->
                    PlayGames.getPlayersClient(this).currentPlayer.addOnCompleteListener { mTask ->
                        print(
                            mTask.result.displayName
                        )
                    }
                }
            }


        binding.button.setOnClickListener { _ ->
            intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        };
    }
}