package com.fakhir.mobile.fooddelivery.model

data class AppState (
    var user: User = User(),
    var hotelList: MutableList<Hotel> = mutableListOf<Hotel>()
)
