package com.example.habittracker.view.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.habittracker.R
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.view.main.component.SectionTitle
import com.example.habittracker.view.navigation.BottomNavItem

@Composable
fun UpdatePassword(
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Text(
                text = "Update Password",
                fontSize = 30.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(Modifier.height(16.dp))

            PasswordField(
                title = "Current password",
                value = currentPassword,
                onValueChange = { currentPassword = it },
                isVisible = false,
                onVisibilityChange = {}
            )

            PasswordField(
                title = "New password",
                value = newPassword,
                onValueChange = { newPassword = it },
                isVisible = isNewPasswordVisible,
                onVisibilityChange = {
                    isNewPasswordVisible = !isNewPasswordVisible
                }
            )

            PasswordField(
                title = "Confirm password",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isVisible = isConfirmPasswordVisible,
                onVisibilityChange = {
                    isConfirmPasswordVisible = !isConfirmPasswordVisible
                }
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (newPassword != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (newPassword.isBlank() || confirmPassword.isBlank() || currentPassword.isBlank()) {
                        Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    navController.navigate(BottomNavItem.Profile.route) {
                        popUpTo("update_password") {
                            inclusive = true
                        }
                    }

                    Toast.makeText(context, "Password updated", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 15.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colors.onPrimary,
                    containerColor = colors.primary
                )
            ) {
                Text(
                    text = "Update password",
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}

@Composable
fun PasswordField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onVisibilityChange: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column {
        SectionTitle(title)

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (isVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = { Text(title) },
            trailingIcon = {
                Icon(
                    painter = painterResource(
                        id = if (isVisible)
                            R.drawable.eye_open
                        else
                            R.drawable.eye_closed
                    ),
                    contentDescription = "toggle visibility",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onVisibilityChange() },
                    tint = colors.onSurfaceVariant
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = colors.onSurface
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface,
                focusedContainerColor = colors.surfaceVariant,
                unfocusedContainerColor = colors.surfaceVariant,
                focusedIndicatorColor = colors.primary,
                unfocusedIndicatorColor = colors.outline
            )
        )
    }
}
