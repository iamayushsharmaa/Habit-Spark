package com.example.habittracker.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.habittracker.data.auth.AuthResult
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.viewModel.AuthViewModel

@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
) {

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect (viewModel, context){
        viewModel.authResult.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    navController.navigate("main_screen"){
                        popUpTo("signin"){
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, result.message , Toast.LENGTH_SHORT).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    val focusRequester = remember{
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
                .background(color = AppColor.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start

        ) {
            Text(
                text = "Sign in.",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 40.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
            )
            Text(
                text = "Please enter email and password to sign in.",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
            )
            OutlinedTextField(
                value = state.signinUsername,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it))
                },
                label = { Text(text = "Create a username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp),

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColor.Black,
                    unfocusedTextColor = AppColor.Black,
                    focusedContainerColor = AppColor.WhiteFade,
                    unfocusedContainerColor = AppColor.WhiteFade,
                    focusedIndicatorColor = AppColor.Black,
                    unfocusedIndicatorColor = AppColor.WhiteFade,
                    focusedLabelColor = AppColor.BlackFade,
                    unfocusedLabelColor = AppColor.BlackFade,

                )

            )

            OutlinedTextField(
                value = state.signinPassword,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
                },
                label = { Text(text = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),

                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColor.Black,
                    unfocusedTextColor = AppColor.Black,
                    focusedContainerColor = AppColor.WhiteFade,
                    unfocusedContainerColor = AppColor.WhiteFade,
                    focusedIndicatorColor = AppColor.Black,
                    unfocusedIndicatorColor = AppColor.WhiteFade,
                    focusedLabelColor = AppColor.BlackFade,
                    unfocusedLabelColor = AppColor.BlackFade
                )
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.onEvent(AuthUiEvent.SignIn)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Sign in")
            }
            Spacer(modifier = Modifier.height(20.dp))

            val annotatedText = buildAnnotatedString {
                append("Don't have an account? ")

                pushStringAnnotation(tag = "SIGN_UP", annotation = "sign_up")
                withStyle(style = SpanStyle(color = Color(0xFF0538D1))) {
                    append(" Sign up! ")
                }
                pop()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "SIGN_UP", start = offset, end = offset)
                            .firstOrNull()?.let {
                                navController.navigate("signup"){
                                    popUpTo("signin"){
                                        inclusive = true
                                    }
                                }
                            }
                    }
                )
            }
        }

    }

}