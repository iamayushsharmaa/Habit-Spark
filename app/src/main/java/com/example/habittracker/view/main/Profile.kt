package com.example.habittracker.view.main

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.R
import com.example.habittracker.ui.theme.AppColor
import com.example.habittracker.ui.theme.poppinsFontFamily

@Composable
fun Profile(
    navController: NavHostController
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("Ayush Sharma") }
    var email by remember { mutableStateOf("ayushs9468@gmail.com") }
    var phoneNumber by remember { mutableStateOf("000000000") }
    var password by remember { mutableStateOf("xyuuuu") }
    var isEditable by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

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
        ){
            Text(
                text = "Profile",
                fontSize = 30.sp,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            if (!isEditable){
                IconButton (
                    onClick = { isEditable = !isEditable },
                    modifier = Modifier
                        .background(shape = RoundedCornerShape(10.dp), color = AppColor.WhiteFade)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = AppColor.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(5.dp))

        TextForm("Name")
        OutlinedTextField(
            value = name,
            onValueChange = { if (isEditable) name = it },
            placeholder = { Text("Name") },
            enabled = isEditable,
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

        Spacer(modifier = Modifier.height(5.dp))

        TextForm("Username")
        OutlinedTextField(
            value = email,
            onValueChange = { if (isEditable) email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text("Name") },
            enabled = isEditable,
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

        Spacer(modifier = Modifier.height(5.dp))

        TextForm("Phone number")

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { if (isEditable) phoneNumber = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeholder = { Text("Name") },
            enabled = isEditable,
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
                unfocusedIndicatorColor = AppColor.WhiteFade,

                )        )

        Spacer(modifier = Modifier.height(5.dp))

        if (!isEditable){
            TextForm("Password")
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = { Text("Name") },
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
                enabled = false,
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

        }

        Spacer(modifier = Modifier.height(18.dp))

        if (isEditable) {
            Button(
                onClick = {
                    isEditable = false
                    Toast.makeText(context, "Information updated", Toast.LENGTH_SHORT).show()
                },
                enabled = isEditable,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppColor.White,
                    containerColor = AppColor.Black
                )
            ) {
                Text("Update Information")
            }
        }else{
            Button(
                onClick = {
                    navController.navigate("update_password")
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
}




