package com.example.movieandroidapp.presentation.core.auth.signUpScreen

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
import androidx.compose.runtime.*
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
import com.example.movieandroidapp.data.remote.firebase.AuthResource
import com.example.movieandroidapp.domain.utils.Screen
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextPrimary
import com.example.movieandroidapp.presentation.theme.TextSecondary
import com.example.movieandroidapp.R
import com.example.movieandroidapp.presentation.core.auth.AuthViewModel

@Composable
fun SignUpScreen(mainNavController: NavHostController) {

    val signUpViewModel = hiltViewModel<SignUpViewModel>()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var showPassword by remember {
        mutableStateOf(false)
    }
    var showConfirmPassword by remember {
        mutableStateOf(false)
    }

    val authViewModel = hiltViewModel<AuthViewModel>()
    val authState = authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.sign_up),
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(R.string.welcome_create_new_account_with_your_credentials),
            color = TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = stringResource(id = R.string.email),
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            value = signUpViewModel.formState.email,
            onValueChange = { email -> signUpViewModel.onEvent(SignUpEvent.EmailChanged(email)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = stringResource(id = R.string.email)
                )
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
                    text = stringResource(id = R.string.enter_your_email),
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
                val isError = signUpViewModel.formState.emailError != null
                Text(
                    text = if (isError) signUpViewModel.formState.emailError!!.asString(context) else "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            isError = signUpViewModel.formState.emailError != null
        )

        Text(
            text = stringResource(id = R.string.password),
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signUpViewModel.formState.password,
            onValueChange = { password ->
                signUpViewModel.onEvent(
                    SignUpEvent.PasswordChange(
                        password
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = stringResource(id = R.string.password)
                )
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
                val isError = signUpViewModel.formState.passwordError != null
                Text(
                    text = if (isError) signUpViewModel.formState.passwordError!!.asString(context) else "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            isError = signUpViewModel.formState.passwordError != null
        )

        Text(
            text = stringResource(R.string.confirm_password),
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signUpViewModel.formState.confirmPassword,
            onValueChange = { password ->
                signUpViewModel.onEvent(
                    SignUpEvent.ConfirmPasswordChange(
                        password
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = stringResource(R.string.password)
                )
            },
            trailingIcon = {

                val icon = if (showConfirmPassword) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                val description = if (showPassword) {
                    stringResource(R.string.visible_off_icon)
                } else {
                    stringResource(R.string.visible_icon)
                }

                IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
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
                    text = stringResource(R.string.confirm_your_password),
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()

                }
            ),
            singleLine = true,
            isError = signUpViewModel.formState.confirmPasswordError != null,
            supportingText = {
                val isError = signUpViewModel.formState.confirmPasswordError != null
                Text(
                    text = if (isError) signUpViewModel.formState.confirmPasswordError!!.asString(
                        context
                    ) else "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            ),
            onClick = {
                val isUserInputValid = signUpViewModel.onEvent(SignUpEvent.Submit)
                if (isUserInputValid) {
                    authViewModel.signUp(
                        signUpViewModel.formState.email,
                        signUpViewModel.formState.password
                    )
                }
            }
        ) {
            Text(
                text = "Sing Up",
                color = Background,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mainNavController.navigate(Screen.SignIn.rout) },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append(stringResource(R.string.already_have_an_account)) }
                withStyle(style = SpanStyle(Primary)) {
                    append(stringResource(id = R.string.sign_in))
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
                withStyle(style = SpanStyle(TextSecondary)) { append(stringResource(id = R.string.login_as)) }
                withStyle(style = SpanStyle(Primary)) {
                    append(stringResource(id = R.string.guest))
                }
            },
            textAlign = TextAlign.Center
        )

        authState.value.let {
            when (it) {
                is AuthResource.Failure -> {
                    Toast.makeText(LocalContext.current, it.exception.message, Toast.LENGTH_LONG)
                        .show()
                }

                AuthResource.Loading -> {
                    CircularProgressIndicator()
                }

                is AuthResource.Success -> {
                    LaunchedEffect(Unit) {
                        mainNavController.navigate(Screen.Main.rout) {
                            popUpTo(Screen.SignUp.rout) { inclusive = true }
                        }
                    }

                }

                null -> Unit
            }
        }
    }

}
