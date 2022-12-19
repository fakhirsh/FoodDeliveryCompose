package com.fakhir.mobile.fooddelivery

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fakhir.mobile.fooddelivery.model.AppViewModel
import com.fakhir.mobile.fooddelivery.model.User
import com.fakhir.mobile.fooddelivery.screen.SplashScreen
import com.fakhir.mobile.fooddelivery.ui.theme.FoodDeliveryComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class ProfileArgs(val user: User?) : NavArgs {
    companion object {
        fun fromBundle(bundle: Bundle): ProfileArgs {
            return ProfileArgs(
                user = bundle.getParcelable("user")
            )
        }
    }

    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable("user", user)
        return bundle
    }
}


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private var gotoScreen = ""

    private val LOGIN = "LOGIN"
    private val HOME = "HOME"
    private val FOOD = "FOOD"
    private val SPLASH = "SPLASH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            FoodDeliveryComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    auth = Firebase.auth
                    val viewModel: AppViewModel by viewModels()
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = SPLASH
                    ) {
                        composable(route = SPLASH) {
                            SplashScreen(auth, navController, viewModel)
                        }
                        composable(route = LOGIN) {
                            LoginScreen(navController, viewModel)
                        }
                        composable(
                            route = HOME,
                        ) {
                            HomeScreen(auth, navController, viewModel)
                        }

                    }
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

//Home screen preview
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FoodDeliveryComposeTheme {
        HomeScreen()
    }
}

