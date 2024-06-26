package com.example.movieandroidapp.presentation.core.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieandroidapp.R
import com.example.movieandroidapp.domain.utils.MainFragment
import com.example.movieandroidapp.presentation.movies.MoviesScreen
import com.example.movieandroidapp.presentation.profile.ProfileScreen
import com.example.movieandroidapp.presentation.theme.IconColor
import com.example.movieandroidapp.presentation.theme.Primary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navHostController: NavHostController) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val mainState = mainViewModel.mainState.collectAsState().value
    val navController = rememberNavController()

    Scaffold(
        topBar = {

            var icon: ImageVector = Icons.Filled.Home
            var iconDesc = stringResource(R.string.home_icon)
            var title = "Title"

            when (mainState.currentFragment) {
                MainFragment.Home -> {
                    icon = Icons.Filled.Home
                    iconDesc = stringResource(R.string.home_icon)
                    title = stringResource(R.string.home)
                }

                MainFragment.Movies -> {
                    icon = Icons.Filled.Movie
                    iconDesc = stringResource(R.string.movie_icon)
                    title = stringResource(R.string.movies)
                }

                MainFragment.TvSeries -> {
                    icon = Icons.Filled.LiveTv
                    iconDesc = stringResource(R.string.tv_series_icon)
                    title = stringResource(R.string.tv_series)
                }

                MainFragment.Profile -> {
                    icon = Icons.Filled.AccountCircle
                    iconDesc = stringResource(R.string.profile_icon)
                    title = stringResource(R.string.profile)
                }
            }

            TopBar(icon = icon, iconDesc = iconDesc, title = title)
        },
        bottomBar = {
            BottomNavigationBar(
                navHostController = navController,
                navigateOnOtherScreen = mainViewModel::navigateToOtherFragment
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = MainFragment.Home.rout
            ) {
                composable(
                    route = MainFragment.Home.rout
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = MainFragment.Home.rout)
                    }
                }

                composable(
                    route = MainFragment.Movies.rout
                ) {
                    MoviesScreen(navHostController)
                }

                composable(
                    route = MainFragment.TvSeries.rout
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = MainFragment.TvSeries.rout)
                    }
                }

                composable(
                    route = MainFragment.Profile.rout
                ) {
                    ProfileScreen(navHostController)
                }
            }
        }
    }
}

@Composable
fun TopBar(icon: ImageVector, iconDesc: String, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp),
            imageVector = icon,
            contentDescription = iconDesc,
            tint = Primary
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )
        Icon(
            modifier = Modifier
                .size(35.dp)
                .clickable { },
            imageVector = Icons.AutoMirrored.Filled.ManageSearch,
            contentDescription = stringResource(R.string.search_icon),
            tint = IconColor
        )

    }
}

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    navigateOnOtherScreen: (mainFragment: MainFragment) -> Unit
) {

    val items = listOf(
        BottomItem(
            description = stringResource(id = R.string.home),
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomItem(
            description = stringResource(id = R.string.movies),
            icon = Icons.Outlined.Movie,
            selectedIcon = Icons.Filled.Movie
        ),
        BottomItem(
            description = stringResource(id = R.string.tv_series),
            icon = Icons.Outlined.LiveTv,
            selectedIcon = Icons.Filled.LiveTv
        ),
        BottomItem(
            description = stringResource(id = R.string.profile),
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
                                navigateOnOtherScreen(MainFragment.Home)
                                navHostController.popBackStack()
                                navHostController.navigate(MainFragment.Home.rout)
                            }

                            1 -> {
                                navigateOnOtherScreen(MainFragment.Movies)
                                navHostController.popBackStack()
                                navHostController.navigate(MainFragment.Movies.rout)
                            }

                            2 -> {
                                navigateOnOtherScreen(MainFragment.TvSeries)
                                navHostController.popBackStack()
                                navHostController.navigate(MainFragment.TvSeries.rout)
                            }

                            3 -> {
                                navigateOnOtherScreen(MainFragment.Profile)
                                navHostController.popBackStack()
                                navHostController.navigate(MainFragment.Profile.rout)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected.intValue == index) bottomItem.selectedIcon else bottomItem.icon,
                            contentDescription = bottomItem.description,
                            tint = if (selected.intValue == index) Primary else IconColor
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            LocalAbsoluteTonalElevation.current
                        ),

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
