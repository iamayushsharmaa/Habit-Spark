package com.example.habittracker.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.habittracker.data.UserData
import com.example.habittracker.viewModel.AuthViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userData: UserData?,
    onSignOut : @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Profile",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if(userData?.profilePhoto != null){
            AsyncImage(
                model = userData.profilePhoto,
                contentDescription = "profile",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        if (userData?.userName != null){
            Text(
                text = userData.userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 15.dp, top = 5.dp)
            )
        }
        Text(
            text = " ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 3.dp)
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 17.dp, vertical = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(text ="Sign out" )
        }
    }
}


