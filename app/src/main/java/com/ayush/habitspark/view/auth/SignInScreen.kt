package com.ayush.habitspark.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ayush.habitspark.common.ui_states.AuthState
import com.ayush.habitspark.ui.theme.AppColor
import com.ayush.habitspark.view.auth.component.AuthTextField
import com.ayush.habitspark.view.auth.component.GoogleSignInButton
import com.ayush.habitspark.view.navigation.Screen
import com.ayush.habitspark.viewModel.AuthViewModel

@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        when (state) {
            is AuthState.Success -> navController.navigate(Screen.Main.route) {
                popUpTo(Screen.SignIn.route) { inclusive = true }
            }
            is AuthState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Sign in.",
                modifier = Modifier.padding(vertical = 4.dp),
                fontSize = 38.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Welcome back!",
                modifier = Modifier.padding(vertical = 4.dp),
                fontSize = 13.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
            )

            Spacer(Modifier.height(16.dp))

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )
            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                imeAction = ImeAction.Done,
                onImeAction = { focusManager.clearFocus() },
                isPassword = true
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { viewModel.signIn(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding( vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Sign in",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                val annotatedText = buildAnnotatedString {
                    append("Don't have an account? ")
                    pushStringAnnotation(tag = "SIGN_UP", annotation = "sign_up")
                    withStyle(SpanStyle(color = Color(0xFF0538D1), fontWeight = FontWeight.SemiBold)) {
                        append("Sign up")
                    }
                    pop()
                }
                ClickableText(text = annotatedText, onClick = { offset ->
                    annotatedText.getStringAnnotations("SIGN_UP", offset, offset)
                        .firstOrNull()?.let {
                            navController.navigate(Screen.SignUp.route) {
                                popUpTo(Screen.SignIn.route) { inclusive = true }
                            }
                        }
                })
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("  OR  ", fontSize = 12.sp, color = Color.Gray)
                HorizontalDivider(modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            GoogleSignInButton(viewModel = viewModel)
        }

        if (state is AuthState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}