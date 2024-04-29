package com.example.movieandroidapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieandroidapp.presentation.theme.Background
import com.example.movieandroidapp.presentation.theme.Primary
import com.example.movieandroidapp.presentation.theme.TextPrimary
import com.example.movieandroidapp.presentation.theme.TextSecondary

@Composable
fun ProfileScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Create an account",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            color = TextPrimary
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "Hey there, create an account and begin your journey with our App. Find out what movies are now playing and more.",
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
            onClick = { }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Background
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "ArrowForward",
                    tint = Background
                )

            }
        }

        Text(
            modifier = Modifier.padding(top = 50.dp).clickable {

            },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(TextSecondary)) { append("Already have an account? ") }
                withStyle(style = SpanStyle(Primary)) {
                    append("Sign In")
                }
            },
            color = TextSecondary,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }

}