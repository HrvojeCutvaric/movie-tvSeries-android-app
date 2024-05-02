package com.example.movieandroidapp.presentation.core.signInScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieandroidapp.R
import com.example.movieandroidapp.data.remote.firebase.AuthResource
import com.example.movieandroidapp.domain.utils.Screen
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextPrimary
import com.example.movieandroidapp.presentation.theme.TextSecondary

@Composable
fun SignInScreen(mainNavController: NavHostController) {

    val signInViewModel = hiltViewModel<SignInViewModel>()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var showPassword by remember {
        mutableStateOf(false)
    }

    val singInFLow = signInViewModel.signInFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.welcome_back_enter_your_credentials_to_sing_in),
            color = TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = stringResource(R.string.email),
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            value = signInViewModel.formState.email,
            onValueChange = { email -> signInViewModel.onEvent(SignInEvent.EmailChanged(email)) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = stringResource(R.string.email))
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Primary,
                unfocusedLeadingIconColor = TextSecondary,
                focusedBorderColor = Primary,
                cursorColor = TextPrimary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_your_email),
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            supportingText = {
                val isError = signInViewModel.formState.emailError != null
                Text(
                    text = if (isError) signInViewModel.formState.emailError!!.asString(context) else "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            isError = signInViewModel.formState.emailError != null
        )

        Text(
            text = stringResource(R.string.password),
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signInViewModel.formState.password,
            onValueChange = { password ->
                signInViewModel.onEvent(
                    SignInEvent.PasswordChange(
                        password
                    )
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = stringResource(R.string.password))
            },
            trailingIcon = {

                val icon = if (showPassword) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                val description = if (showPassword) {
                    stringResource(R.string.visible_off_icon)
                } else {
                    stringResource(R.string.visible_icon)
                }

                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = icon, contentDescription = description)
                }

            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Primary,
                unfocusedLeadingIconColor = TextSecondary,
                focusedBorderColor = Primary,
                cursorColor = TextPrimary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_your_password),
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            supportingText = {
                val isError = signInViewModel.formState.passwordError != null
                Text(
                    text = if (isError) signInViewModel.formState.passwordError!!.asString(context) else "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            isError = signInViewModel.formState.passwordError != null
        )



        Spacer(modifier = Modifier.height(140.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            ),
            onClick = {
                signInViewModel.onEvent(SignInEvent.Submit)
            }
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                color = Background,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    mainNavController.navigate(Screen.SignUp.rout)
                },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append(stringResource(R.string.don_t_have_an_account)) }
                withStyle(style = SpanStyle(Primary)) {
                    append(stringResource(R.string.sign_up))
                }
            },
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    mainNavController.navigate(Screen.Main.rout)
                },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append(stringResource(R.string.login_as)) }
                withStyle(style = SpanStyle(Primary)) {
                    append(stringResource(R.string.guest))
                }
            },
            textAlign = TextAlign.Center
        )

        singInFLow.value.let {
            when (it) {
                is AuthResource.Failure -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }

                AuthResource.Loading -> {
                    CircularProgressIndicator()
                }

                is AuthResource.Success -> {
                    LaunchedEffect(Unit) {
                        mainNavController.navigate(Screen.Main.rout) {
                            popUpTo(Screen.SignIn.rout) { inclusive = true }
                        }
                    }

                }

                null -> Unit
            }
        }
    }

}
