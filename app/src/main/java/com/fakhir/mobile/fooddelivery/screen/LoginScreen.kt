package com.fakhir.mobile.fooddelivery

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fakhir.mobile.fooddelivery.model.AppViewModel
import com.fakhir.mobile.fooddelivery.model.User
import com.fakhir.mobile.fooddelivery.ui.theme.FoodDeliveryComposeTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController: NavController?=null, viewModel: AppViewModel?=null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel?.appState!!.collectAsState()

    FoodDeliveryComposeTheme{
        Column(
            modifier = Modifier.fillMaxSize(),

            ) {
            Image(
                painter = painterResource(R.drawable.chinese_food_banner),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.panda),
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.Center)
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(35.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text(text = "Email") },
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = {
                        Log.d("TAG", "Login Button Clicked")
                        var auth = Firebase.auth
                        val currentUser = auth.currentUser
                        Log.d("TAG", "Current User: ${currentUser}")

                        auth?.signInWithEmailAndPassword(email, password)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("TAG", "Login Successful")
                                    var user:User = User()
                                    user.name = currentUser!!.displayName.toString()
                                    user.email = currentUser!!.email.toString()

                                    viewModel?.setUser(user)
                                    navController?.navigate("home")
                                } else {
                                    Log.d("TAG", "Login Failed" + task.exception)
                                }
                            }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Log in")
                }
            }
        }
    }
}