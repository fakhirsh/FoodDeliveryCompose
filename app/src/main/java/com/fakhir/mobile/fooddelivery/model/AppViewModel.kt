package com.fakhir.mobile.fooddelivery.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    //app state using MutableStateFlow
    var appState : MutableStateFlow<AppState> = MutableStateFlow(AppState())

    //write getter and setters for app state
    fun getUser() : User {
        return appState.value.user
    }

    fun setUser(user: User) {
        appState.update { currentState ->
            currentState.copy(
                user = user
            )
        }
    }

    fun getHotelList() : MutableList<Hotel> {
        return appState.value.hotelList
    }

    fun setHotelList(hotelList: MutableList<Hotel>) {
        appState.update { currentState ->
            currentState.copy(
                hotelList = hotelList
            )
        }
    }


}