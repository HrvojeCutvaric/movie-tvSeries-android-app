package com.example.movieandroidapp.presentation.core.movieDetails

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.movieandroidapp.data.remote.TMDBApi
import com.example.movieandroidapp.domain.models.movie.Genre
import com.example.movieandroidapp.domain.utils.RatingBar
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextPrimary
import com.example.movieandroidapp.presentation.theme.TextSecondary
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailsScreen(navHostController: NavHostController) {
    val movieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>()
    val movieDetailsState = movieDetailsViewModel.movieDetailsState.collectAsState().value

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(TMDBApi.IMAGE_BASE_URL + movieDetailsState.movieDetails?.backdrop_path).size(
                Size.ORIGINAL
            ).build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(TMDBApi.IMAGE_BASE_URL + movieDetailsState.movieDetails?.poster_path).size(
                Size.ORIGINAL
            ).build()
    ).state

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {

            if (backDropImageState is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(70.dp),
                        imageVector = Icons.Rounded.ImageNotSupported,
                        contentDescription = movieDetailsState.movieDetails?.title
                    )
                }
            }

            if (backDropImageState is AsyncImagePainter.State.Success) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = backDropImageState.painter,
                    contentDescription = movieDetailsState.movieDetails?.title,
                    contentScale = ContentScale.Crop
                )
            }

            FloatingActionButton(
                modifier = Modifier.padding(12.dp),
                onClick = { navHostController.popBackStack() },
                contentColor = Primary,
                containerColor = Color.Transparent,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back Arrow")
            }
        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            ) {
                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = movieDetailsState.movieDetails?.title
                        )
                    }
                }

                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = posterImageState.painter,
                        contentDescription = movieDetailsState.movieDetails?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = movieDetailsState.movieDetails?.title ?: "",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = displayGenres(movieDetailsState.movieDetails?.genres),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TextSecondary
                )

                val date = LocalDate.parse(movieDetailsState.movieDetails?.release_date ?: "1970-01-01")
                val displayDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(date)

                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = displayDateFormat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TextSecondary
                )
            }

        }

        Row(
            Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingBar(
                modifier = Modifier.padding(start = 0.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                starsModifier = Modifier.size(18.dp),
                rating = (movieDetailsState.movieDetails?.vote_average ?: 1.0) / 2
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                text = (movieDetailsState.movieDetails?.vote_average ?: 1.000).toString().take(3),
                color = Color.Yellow,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )



            Text(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                text = displayReviewsCount(movieDetailsState.movieDetails?.vote_count ?: 0),
                color = TextSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = "Overview",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = TextPrimary
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = movieDetailsState.movieDetails?.overview ?: "",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = TextSecondary
        )

    }
}

fun displayGenres(genres: List<Genre>?): String {
    var result = ""
    val size = genres?.size ?: 0

    genres?.forEachIndexed {index, genre ->
        result += genre.name + if(index < size-1) ", " else ""
    }

    return if(result != "") result else "No Genres"
}

fun displayReviewsCount(voteCount: Int): String {
    var result = "("

    result += when (voteCount) {
        in 1000..999999 -> {
            (voteCount / 1000).toString() + "k"
        }

        in 1000000..999999999 -> {
            (voteCount / 1000000).toString() + "m"
        }

        else -> {
            voteCount.toString()
        }
    }

    result += " review${if(voteCount > 1) "s" else ""})"
    return result
}
