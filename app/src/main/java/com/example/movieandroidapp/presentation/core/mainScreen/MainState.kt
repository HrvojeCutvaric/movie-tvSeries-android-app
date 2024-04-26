package com.example.movieandroidapp.presentation.core.mainScreen

import com.example.movieandroidapp.domain.utils.Screen

data class MainState(
    val currentScreen: Screen = Screen.Home
)