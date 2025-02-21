package com.example.habittracker.view.auth


import android.util.Log
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
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.data.auth.AuthResult
import com.example.habittracker.viewModel.AuthViewModel


@Composable
fun SignUpScreen(
    navController : NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect (viewModel, context){
        viewModel.authResult.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    navController.navigate("main_screen"){
                        popUpTo("signup"){
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "You are not authorized ${result.message}", Toast.LENGTH_SHORT).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "An Unknown error occurred ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager =  LocalFocusManager.current

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
                .background(color = androidx.compose.material3.MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start

        ) {
            Text(
                text = "Create a account",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
            )
            Text(
                text = "Please fill all the details to sign up.",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            )
            OutlinedTextField(
                value = state.signupUsername,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignUpUsernameChanged(it))
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
                )
            )

            OutlinedTextField(
                value = state.signupPassword,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it))
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
                )
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.onEvent(AuthUiEvent.SignUp)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Sign up")
            }
            Spacer(modifier = Modifier.height(20.dp))

            val annotatedText = buildAnnotatedString {
                append("Already have an account? ")

                pushStringAnnotation(tag = "SIGN_IN", annotation = "sign_in")
                withStyle(style = SpanStyle(color = Color(0xFF0538D1))) {
                    append("Sign in!")
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
                        annotatedText.getStringAnnotations(tag = "SIGN_IN", start = offset, end = offset)
                            .firstOrNull()?.let {
                                navController.navigate("signin")
                            }
                    }
                )
            }
        }
    }
}