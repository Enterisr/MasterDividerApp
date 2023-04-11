package com.drunktolstoy.masterdivider3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.games.Player

class UserViewModel(appContext: Application) : AndroidViewModel(appContext) {
    var user = MutableLiveData<Player>()

    fun setUser(newUser: Player) {
        user.value = newUser
    }
}