package com.example.golfgamesapp.ui.gamesType

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameType(val name: String, val type: Type ) : Parcelable


enum class Type {
    Short,
    Mid,
    Long
}