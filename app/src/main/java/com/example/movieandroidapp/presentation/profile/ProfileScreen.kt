package com.example.movieandroidapp.presentation.profile

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieandroidapp.R
import com.example.movieandroidapp.domain.utils.ProfileFragment
import com.example.movieandroidapp.domain.utils.Screen
import com.example.movieandroidapp.presentation.core.auth.AuthViewModel
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.CardBackground
import com.example.movieandroidapp.presentation.theme.IconColor
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextPrimary
import com.example.movieandroidapp.presentation.theme.TextSecondary
import com.google.firebase.auth.FirebaseUser

@Composable
fun ProfileScreen(mainNavController: NavHostController) {
    val authViewModel = hiltViewModel<AuthViewModel>()
    val navController = rememberNavController()
    val user = authViewModel.currentUser

    NavHost(navController = navController, startDestination = getCurrentFragment(user)) {
        composable(
            route = ProfileFragment.UnauthorizedUser.rout
        ) {
            UnauthorizedUserFragment(mainNavController = mainNavController)
        }

        composable(
            route = ProfileFragment.AuthorizedUser.rout
        ) {
            AuthorizedUserFragment(authViewModel = authViewModel, user = user!!, navController = navController)
        }
    }
}



@Composable
fun UnauthorizedUserFragment(mainNavController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_an_account),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            color = TextPrimary
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.hey_there_create_an_account_and_begin_your_journey_with_our_app_find_out_what_movies_are_now_playing_and_more),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary
        )

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            ),
            onClick = {
                mainNavController.navigate(Screen.SignUp.rout)
            }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Background
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = stringResource(R.string.arrow_forward_icon),
                    tint = Background
                )

            }
        }

        Text(
            modifier = Modifier
                .padding(top = 50.dp)
                .clickable {
                    mainNavController.navigate(Screen.SignIn.rout)
                },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append(stringResource(id = R.string.already_have_an_account)) }
                withStyle(style = SpanStyle(Primary)) {
                    append(stringResource(id = R.string.sign_in))
                }
            },
            color = TextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AuthorizedUserFragment(
    authViewModel: AuthViewModel,
    user: FirebaseUser,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(TextSecondary),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = "Person Icon",
                tint = Color.Black
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if (user.displayName == "") stringResource(R.string.no_display_name) else user.displayName
                ?: stringResource(R.string.no_display_name),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user.email ?: "",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val items = listOf(
            ProfileOptionsItem(
                icon = Icons.Filled.Edit,
                name = stringResource(R.string.edit_profile),
                color = TextSecondary,
                showNextIcon = true,
                onClick = {}
            ),
            ProfileOptionsItem(
                icon = Icons.Filled.Lock,
                name = stringResource(R.string.change_password),
                color = TextSecondary,
                showNextIcon = true,
                onClick = {}
            ),
            ProfileOptionsItem(
                icon = Icons.Filled.Bookmark,
                name = stringResource(R.string.saved),
                color = TextSecondary,
                showNextIcon = true,
                onClick = {}
            ),
            ProfileOptionsItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                name = stringResource(R.string.logout),
                color = Color.Red,
                showNextIcon = false,
                onClick = {
                    authViewModel.logout()
                    navController.navigate(ProfileFragment.UnauthorizedUser.rout) {
                        popUpTo(ProfileFragment.AuthorizedUser.rout) { inclusive = true }
                    }
                }
            ),
        )

        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {

                        val borderSize = 0.5.dp.toPx()

                        drawLine(
                            CardBackground,
                            Offset(0f, 0f),
                            Offset(size.width, 0f),
                            borderSize
                        )
                    }
                    .clickable {
                        item.onClick()
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                    imageVector = item.icon,
                    contentDescription = item.name,
                    tint = item.color
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = item.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = item.color
                )

                if (item.showNextIcon) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 16.dp, top = 8.dp, bottom = 8.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = stringResource(R.string.next_icon),
                        tint = IconColor
                    )
                }
            }
        }

    }
}

private fun getCurrentFragment(user: FirebaseUser?): String {
    if (user == null) return ProfileFragment.UnauthorizedUser.rout

    return ProfileFragment.AuthorizedUser.rout
}

data class ProfileOptionsItem(
    val icon: ImageVector,
    val name: String,
    val color: Color,
    val showNextIcon: Boolean,
    val onClick: () -> Unit
)