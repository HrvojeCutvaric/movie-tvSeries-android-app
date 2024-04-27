package com.example.movieandroidapp.presentation.movies

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.movieandroidapp.R
import com.example.movieandroidapp.data.remote.TMDBApi
import com.example.movieandroidapp.domain.models.Movie
import com.example.movieandroidapp.domain.utils.Category
import com.example.movieandroidapp.domain.utils.MoviesFragments
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.CardBackground
import com.example.movieandroidapp.presentation.theme.IconColor
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextSecondary
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoviesScreen() {
    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val moviesState = moviesViewModel.moviesState.collectAsState().value
    val navController = rememberNavController()



    Column(modifier = Modifier.fillMaxSize()) {
        CategoryNavigationBar(navController)
        NavHost(navController = navController, startDestination = MoviesFragments.Popular.rout) {
            composable(Category.POPULAR) {
                DisplayMovieList(movieList = moviesState.popularMovieList, isLoading = moviesState.isLoading) {
                    moviesViewModel.onEvent(MoviesUiEvent.Paginate(Category.POPULAR))
                }

            }
            composable(Category.UPCOMING) {
                DisplayMovieList(movieList = moviesState.upcomingMovieList, isLoading = moviesState.isLoading) {
                    moviesViewModel.onEvent(MoviesUiEvent.Paginate(Category.UPCOMING))
                }
            }
            composable(Category.PLAYING) {
                DisplayMovieList(movieList = moviesState.nowPlayingMovieList, isLoading = moviesState.isLoading) {
                    moviesViewModel.onEvent(MoviesUiEvent.Paginate(Category.PLAYING))
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayMovieList(movieList: List<Movie>, isLoading: Boolean, onEvent: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        if (movieList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp),
            ) {
                items(movieList.size) { index ->
                    MovieRow(movie = movieList[index])

                    if (index >= movieList.size - 1 && !isLoading) {
                        onEvent()
                    }
                }
            }
        }

    }
}

@Composable
fun CategoryNavigationBar(navController: NavHostController) {
    val items = listOf(
        CategoryNavItem(stringResource(R.string.popular), MoviesFragments.Popular.rout),
        CategoryNavItem(stringResource(R.string.upcoming), MoviesFragments.Upcoming.rout),
        CategoryNavItem(stringResource(R.string.playing), MoviesFragments.Playing.rout)
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, categoryNavItem ->
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (selected.intValue == index) Primary else Color.Transparent)
                    .padding(horizontal = 10.dp)
                    .clickable {
                        selected.intValue = index
                        navController.navigate(categoryNavItem.rout)
                    },
                text = categoryNavItem.category,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = if (selected.intValue == index) Background else IconColor
            )
        }
    }
}

data class CategoryNavItem(
    val category: String,
    val rout: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieRow(movie: Movie) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(TMDBApi.IMAGE_BASE_URL + movie.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {}
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(CardBackground)
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = movie.title
                )
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(20.dp)),
                painter = imageState.painter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = movie.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Start",
                    tint = Color.Yellow
                )
                Text(
                    text = movie.vote_average.toString().take(3),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Yellow
                )
            }

            val date = LocalDate.parse(movie.release_date)
            val displayDateFormat = DateTimeFormatter.ofPattern("MMM yyyy").format(date)

            Text(
                text = displayDateFormat,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )
        }
    }
}