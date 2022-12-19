package com.fakhir.mobile.fooddelivery.data

import com.fakhir.mobile.fooddelivery.R
import com.fakhir.mobile.fooddelivery.model.Hotel


class HotelData() {
    fun loadHotels(): List<Hotel> {
        return listOf<Hotel>(
            Hotel(
                1,
                R.drawable.panda,
                "Hotel 1",
                4.0,
                20,
                mutableListOf()
            ),
            Hotel(
                2,
                R.drawable.panda,
                "Hotel 2",
                3.0,
                30,
                mutableListOf()
            ),
            Hotel(
                3,
                R.drawable.panda,
                "Hotel 3",
                5.0,
                40,
                mutableListOf()
            ),
            Hotel(
                4,
                R.drawable.panda,
                "Hotel 4",
                2.0,
                50,
                mutableListOf()
            ),
            Hotel(
                5,
                R.drawable.panda,
                "Hotel 5",
                1.0,
                60,
                mutableListOf()
            )
        )
    }
}