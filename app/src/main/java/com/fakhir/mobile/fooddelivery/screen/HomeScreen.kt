package com.fakhir.mobile.fooddelivery

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fakhir.mobile.fooddelivery.data.HotelData
import com.fakhir.mobile.fooddelivery.model.AppViewModel
import com.fakhir.mobile.fooddelivery.model.FoodItem
import com.fakhir.mobile.fooddelivery.model.Hotel
import com.fakhir.mobile.fooddelivery.ui.theme.FoodDeliveryComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(auth: FirebaseAuth?=null, navController: NavController?=null, viewModel: AppViewModel?=null) {

    var user = viewModel?.getUser()
    Log.d("TAG", "User: ${user}")
    val state by viewModel?.appState!!.collectAsState()

    var hotelList: MutableList<Hotel> = mutableListOf()

    val burger = FoodItem(
        imageId = 123,
        name = "Cheeseburger",
        description = "A delicious burger with a beef patty, cheese, lettuce, tomato, and special sauce, served on a toasted bun.",
        price = 8
    )

    val pizza = FoodItem(
        imageId = 456,
        name = "Pepperoni Pizza",
        description = "A delicious pizza with a crispy crust, tomato sauce, and lots of spicy pepperoni.",
        price = 12
    )

    val hotel = Hotel(
        id = 1,
        imageId = 1,
        name = "Dera",
        rating = 4.1,
        delivery = 150,
        foodList = mutableListOf(burger, pizza)
    )

    hotelList.add(hotel)

    FoodDeliveryComposeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //top bar using scaffold
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = user!!.email!!)
                        },
                        actions = {
                            IconButton(onClick = {
                                auth?.signOut()
                                navController?.navigate("LOGIN")
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_logout),
                                    contentDescription = "Logout"
                                )
                            }
                        }
                    )
                }
            ) {

                HotelList(hotelList = hotelList)
                loadHotelsFromFirebase()
            }
        }
    }
}

@Composable
private fun HotelList(hotelList:MutableList<Hotel>, modifier: Modifier = Modifier) {
    Log.d("TAG", "HotelList: ${hotelList}")
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(hotelList){ hotel ->
            HotelCard(hotel)
        }
    }
}

// Function to draw Hotel card which has a banner image. Bold hotel name and rating in the same row. Name is left aligned and rating is right aligned.
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HotelCard(hotel: Hotel, navController: NavController?=null) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        elevation = 3.dp,
        onClick = {
            Log.d("TAG", "Hotel ${hotel}")
            navController?.navigate("food")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Image(
                painter = painterResource(id = getImageResourceId(hotel.imageId)),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = hotel.name,
                        style = MaterialTheme.typography.h5,
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    Text(
                        text = "${hotel.rating}/5",
                        style = MaterialTheme.typography.h5,
                    )
                }
                Text(
                    text = "Delivery: Rs. ${hotel.delivery}",
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}


fun getImageResourceId(imgId:Int):Int{
    val resId = when(imgId){
        1 -> R.drawable.dera
        2 -> R.drawable.dragon_chinese
        3 -> R.drawable.nandos
        4 -> R.drawable.kfc
        5 -> R.drawable.health_bar
        else -> R.drawable.panda
    }
    return resId
}

@Composable
fun loadHotelsFromFirebase(){
    var hotelList: MutableList<Hotel> = mutableListOf()

    var db = FirebaseFirestore.getInstance()
    db.collection("hotels").get()
        .addOnSuccessListener { result ->
            result.forEach { document ->
                //Log.d("TAG", "${document.id} => ${document.data}")
                //Instantiate a hotel object and initialize it with document.data fields
                var hotel = Hotel()
                hotel.id = (document.data["id"] as Long).toInt()
                hotel.imageId = (document.data["imageId"] as Long).toInt()
                hotel.name = document.data["name"] as String
                hotel.rating = document.data["rating"] as Double
                hotel.delivery = (document.data["delivery"] as Long).toInt()

               //Get food collection from document
                //var foodList: MutableList<FoodItem> = mutableListOf()
                //var foodCollection = document.data["food"] as List<Map<String, FoodItem>>


                hotelList.add(hotel)
            }
        }
        .addOnFailureListener { exception ->
            Log.d("TAG", "Error getting documents: ", exception)
        }

}