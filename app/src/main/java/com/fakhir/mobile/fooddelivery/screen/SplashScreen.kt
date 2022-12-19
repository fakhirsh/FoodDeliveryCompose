package com.fakhir.mobile.fooddelivery.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fakhir.mobile.fooddelivery.model.AppViewModel
import com.fakhir.mobile.fooddelivery.model.User
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SplashScreen(auth:FirebaseAuth, navController: NavController?=null, viewModel: AppViewModel?=null) {

    val currentUser = auth.currentUser
    var gotoScreen = if(currentUser == null){
        "LOGIN"

    }else{
        "HOME"
    }

    var user: User = User()
    user.name = currentUser!!.displayName.toString()
    user.email = currentUser!!.email.toString()

    viewModel?.setUser(user)
    navController?.navigate(gotoScreen)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = gotoScreen,
            fontSize = 30.sp
        )
    }
}