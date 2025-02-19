package com.example.habittracker.view.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily
import com.example.habittracker.view.navigation.BottomNavItem

@Composable
fun UpdatePassword(
    navController: NavController
) {

    val context = LocalContext.current
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Update Password",
                fontSize = 30.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
        Spacer(Modifier.height(16.dp))
        TextForm("Current password")
        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            //visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = {
                Text(
                    text = "Current password",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
                ) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "icon lock",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 5.dp),
                    tint = AppColor.Black
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                color = AppColor.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Black,
                unfocusedIndicatorColor = AppColor.WhiteFade
            )
        )

        TextForm("Password")
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = {
                Text(
                    text = "New password",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
                ) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (isNewPasswordVisible) R.drawable.eye_open else R.drawable.eye_closed),
                    contentDescription = "icon lock",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 5.dp)
                        .clickable { isNewPasswordVisible = !isNewPasswordVisible },
                    tint = AppColor.Black
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                color = AppColor.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Black,
                unfocusedIndicatorColor = AppColor.WhiteFade
            )
        )

        TextForm("Password")
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = {
                Text(
                    text = "Confirm password",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (isConfirmPasswordVisible) R.drawable.eye_open else R.drawable.eye_closed),
                    contentDescription = "icon lock",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 5.dp)
                        .clickable { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                    tint = AppColor.Black
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                color = AppColor.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = AppColor.WhiteFade,
                unfocusedContainerColor = AppColor.WhiteFade,
                focusedIndicatorColor = AppColor.Black,
                unfocusedIndicatorColor = AppColor.WhiteFade
            )
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                if (newPassword != currentPassword){
                    Toast.makeText(context, "Password not matched", Toast.LENGTH_SHORT).show()
                }
                if (newPassword == null || confirmPassword == null || currentPassword == null) {
                    Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                }
//                if (currentPassword != user.password){
//
//                }
                navController.navigate(BottomNavItem.Profile.route){
                    popUpTo("update_password"){
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
                contentColor = AppColor.White,
                containerColor = AppColor.Black
            )
        ) {
            Text("Update password")
        }

    }
}