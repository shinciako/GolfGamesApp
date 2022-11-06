package com.example.golfgamesapp.ui.gamesType.games

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(val name: String, val id: Int, val gameType: Int) : Parcelable