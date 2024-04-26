package com.example.movieandroidapp.presentation.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieandroidapp.domain.utils.Screen
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.Primary

@Composable
fun MainScreen(navHostController: NavHostController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navHostController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(navController = navController, startDestination = Screen.Home.rout) {
                composable(
                    route = Screen.Home.rout
                ) {
                    Text(text = Screen.Home.rout)
                }

                composable(
                    route = Screen.Movies.rout
                ) {
                    Text(text = Screen.Movies.rout)
                }

                composable(
                    route = Screen.TvSeries.rout
                ) {
                    Text(text = Screen.TvSeries.rout)
                }

                composable(
                    route = Screen.Profile.rout
                ) {
                    Text(text = Screen.Profile.rout)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {

    val items = listOf(
        BottomItem(
            description = "Home",
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomItem(
            description = "Movies",
            icon = Icons.Outlined.Movie,
            selectedIcon = Icons.Filled.Movie
        ),
        BottomItem(
            description = "Tv Series",
            icon = Icons.Outlined.LiveTv,
            selectedIcon = Icons.Filled.LiveTv
        ),
        BottomItem(
            description = "Profile",
            icon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .drawBehind {

                    val borderSize = 1.dp.toPx()

                    drawLine(
                        Color.LightGray,
                        Offset(0f, 0f),
                        Offset(size.width, 0f),
                        borderSize
                    )
                }
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Home.rout)
                            }

                            1 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Movies.rout)
                            }

                            2 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.TvSeries.rout)
                            }

                            3 -> {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Profile.rout)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected.intValue == index) bottomItem.selectedIcon else bottomItem.icon,
                            contentDescription = bottomItem.description,
                            tint = if (selected.intValue == index) Primary else com.example.movieandroidapp.presentation.theme.IconColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current),

                    )
                )
            }
        }
    }
}

data class BottomItem(
    val description: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)
