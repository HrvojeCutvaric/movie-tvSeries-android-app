package com.example.movieandroidapp.presentation.core

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieandroidapp.domain.utils.Screen
import com.example.movieandroidapp.presentation.core.mainScreen.MainScreen
import com.example.movieandroidapp.presentation.core.movieDetails.MovieDetailsScreen
import com.example.movieandroidapp.presentation.core.signUpScreen.SignUpScreen
import com.example.movieandroidapp.presentation.theme.MovieAndroidAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAndroidAppTheme {
                
                SetBarColor(color = MaterialTheme.colorScheme.background)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.Main.rout) {
                        composable(Screen.Main.rout) {
                            MainScreen(navController)
                        }

                        composable(Screen.MovieDetails.rout + "/{movieId}", arguments = listOf(
                            navArgument("movieId") { type = NavType.IntType }
                        )) {
                            MovieDetailsScreen(navController)
                        }

                        composable(Screen.SignUp.rout) {
                            SignUpScreen()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SetBarColor(color: Color) {
        val view = LocalView.current

        if (!view.isInEditMode) {
            LaunchedEffect(key1 = true) {
                val window = (view.context as Activity).window
                window.statusBarColor = color.toArgb()
            }
        }

    }
}

