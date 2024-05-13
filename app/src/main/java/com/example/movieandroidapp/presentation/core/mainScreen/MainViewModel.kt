package com.example.movieandroidapp.presentation.core.mainScreen

import androidx.lifecycle.ViewModel
import com.example.movieandroidapp.domain.utils.MainFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private var _mainState = MutableStateFlow(MainState())
    var mainState = _mainState.asStateFlow()

    fun navigateToOtherFragment(mainFragment: MainFragment) {
        _mainState.update {
            it.copy(
                currentFragment = mainFragment
            )
        }
    }

}