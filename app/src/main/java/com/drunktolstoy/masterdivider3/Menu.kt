package com.drunktolstoy.masterdivider3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.drunktolstoy.masterdivider3.databinding.ActivityMenuBinding
import com.google.android.gms.games.AuthenticationResult
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import com.google.android.gms.games.Player
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var auth: FirebaseAuth
    private var user: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        PlayGamesSdk.initialize(this);
        val gamesSignInClient = PlayGames.getGamesSignInClient(this)
        gamesSignInClient.signIn().addOnCompleteListener(onSigIn)


        binding.button.setOnClickListener { _ ->
            if (user != null) {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.button.setOnClickListener { _ ->
                    PlayGames.getGamesSignInClient(this).signIn().addOnCompleteListener(onSigIn)
                }
            }
        };
    }
    private val onSigIn =
        OnCompleteListener<AuthenticationResult> { _->
        val gamesSignInClient = PlayGames.getGamesSignInClient(this)
        gamesSignInClient.isAuthenticated.addOnCompleteListener { isAuthed ->
            if (isAuthed.isSuccessful && isAuthed.result.isAuthenticated) {
                PlayGames.getPlayersClient(this).currentPlayer.addOnCompleteListener { mTask ->
                    user = mTask.result
                }
            } else {
                Toast.makeText(
                    this,
                    "Opps, auth failed, please try to sign in manually",
                    Toast.LENGTH_SHORT
                ).show()
                binding.button.text = getString(R.string.SignInButon)
            }
        }
    }

}