package com.fakhir.mobile.fooddelivery.model

data class Hotel (
    var id:Int = 0,
    var imageId:Int = 0,
    var name:String = "",
    var rating:Double = 0.0,
    var delivery:Int = 0,
    var foodList:MutableList<FoodItem> = mutableListOf<FoodItem>()
)