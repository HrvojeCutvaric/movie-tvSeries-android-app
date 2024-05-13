package com.example.movieandroidapp.presentation.core.mainScreen

import com.example.movieandroidapp.domain.utils.MainFragment

data class MainState(
    val currentFragment: MainFragment = MainFragment.Home
)