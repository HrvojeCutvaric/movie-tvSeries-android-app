package com.example.movieandroidapp.presentation.core.signUpScreen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextPrimary
import com.example.movieandroidapp.presentation.theme.TextSecondary

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignUpScreen() {

    val signUpViewModel = hiltViewModel<SignUpViewModel>()
    val focusManager = LocalFocusManager.current
    val showPassword = remember {
        mutableStateOf(false)
    }
    val showConfirmPassword = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Sign Up",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "Welcome! Create new account with your credentials.",
            color = TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Email",
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signUpViewModel.email,
            onValueChange = { email -> signUpViewModel.updateEmail(email) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email")
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
                    text = "Enter your email",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = false,

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Password",
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signUpViewModel.password,
            onValueChange = { password -> signUpViewModel.updatePassword(password) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password")
            },
            trailingIcon = {

                val icon = if(showPassword.value) {
                    Icons.Filled.VisibilityOff
                }else {
                    Icons.Filled.Visibility
                }

                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(imageVector = icon, contentDescription = "Visible Icon")
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
                    text = "Enter your password",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Confirm Password",
            color = TextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signUpViewModel.confirmPassword,
            onValueChange = { password -> signUpViewModel.updateConfirmPassword(password) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password")
            },
            trailingIcon = {

                val icon = if(showConfirmPassword.value) {
                    Icons.Filled.VisibilityOff
                }else {
                    Icons.Filled.Visibility
                }

                IconButton(onClick = { showConfirmPassword.value = !showConfirmPassword.value }) {
                    Icon(imageVector = icon, contentDescription = "Visible Icon")
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
                    text = "Confirm your password",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            visualTransformation = if (showConfirmPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = false
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            ),
            onClick = { }
        ) {
            Text(text = "Sing Up", color = Background, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append("Already have an account? ") }
                withStyle(style = SpanStyle(Primary)) {
                    append("Sign In")
                }
            },
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append("Login as ") }
                withStyle(style = SpanStyle(Primary)) {
                    append("Guest")
                }
            },
            textAlign = TextAlign.Center
        )
    }

}
