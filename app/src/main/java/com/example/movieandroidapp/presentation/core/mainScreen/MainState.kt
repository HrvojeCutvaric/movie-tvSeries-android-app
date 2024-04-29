package com.example.movieandroidapp.presentation.core.mainScreen

import com.example.movieandroidapp.domain.utils.MainFragmentScreen
import com.example.movieandroidapp.domain.utils.Screen

data class MainState(
    val currentScreen: MainFragmentScreen = MainFragmentScreen.Home
)