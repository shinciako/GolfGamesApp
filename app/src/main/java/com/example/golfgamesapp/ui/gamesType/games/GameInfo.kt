package com.example.golfgamesapp.ui.gamesType.games

import android.os.Parcelable
import com.example.golfgamesapp.ui.gamesType.Type
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameInfo(val name: String, val gameType: Type, val description: String) : Parcelable