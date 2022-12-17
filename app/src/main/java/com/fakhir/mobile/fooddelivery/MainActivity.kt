package com.fakhir.mobile.fooddelivery

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fakhir.mobile.fooddelivery.ui.theme.FoodDeliveryComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var gotoScreen = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        gotoScreen = if(currentUser == null){
            "Login"
        }else{
            "Home"
        }
        Log.d("TAG", "Dest Screen: $gotoScreen")

        setContent {
            FoodDeliveryComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val viewModel: NavViewModel by viewModels()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        /* creating route "home" */
                        composable(route = "login") {
                            /* Using composable function */
                            LoginScreen(auth, navController, viewModel)
                        }
                        composable(
                            route = "home",
                        ) {
                            HomeScreen(navController, viewModel)
                        }
                    }


                    //LoginScreen(auth)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FoodDeliveryComposeTheme {
        LoginScreen()
    }
}

@Composable
fun LoginScreen(auth: FirebaseAuth?=null, navController: NavController?=null, viewModel: NavViewModel?=null) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                                auth?.signInWithEmailAndPassword(email, password)
                                    ?.addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("TAG", "Login Successful")
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

@Preview(showBackground = true)
@Composable
fun HomeScreen(navController: NavController?=null, viewModel: NavViewModel?=null) {
    FoodDeliveryComposeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        }
        Text(text = "Hello World!")
    }
}