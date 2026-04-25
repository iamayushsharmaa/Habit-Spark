package com.ayush.habitspark.view.auth.component

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayush.habitspark.R
import com.ayush.habitspark.core.auth.provideGoogleSignInClient
import com.ayush.habitspark.ui.theme.AppColor
import com.ayush.habitspark.ui.theme.poppinsFontFamily
import com.ayush.habitspark.viewModel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun GoogleSignInButton(viewModel: AuthViewModel) {
    val context = LocalContext.current
    val googleClient = remember { provideGoogleSignInClient(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken
                if (idToken != null) {
                    viewModel.signInWithGoogle(idToken)
                } else {
                    Log.e("GoogleSignIn", "idToken is NULL") // ✅ add this
                }
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "ApiException code: ${e.statusCode} — ${e.message}") // ✅ add this
            }
        } else {
            Log.e("GoogleSignIn", "Result not OK: ${result.resultCode}") // ✅ add this
        }
    }
    OutlinedButton(
        onClick = { launcher.launch(googleClient.signInIntent) },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = AppColor.WhiteFade,
            contentColor = AppColor.Black
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.googlesvg), // add google icon to drawable
            contentDescription = "Google",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Continue with Google",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}