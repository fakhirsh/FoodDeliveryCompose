package com.fakhir.mobile.fooddelivery

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavViewModel : ViewModel() {
    var _uiState : MutableStateFlow<NavUIState> = MutableStateFlow(NavUIState())
    val uiState: StateFlow<NavUIState> = _uiState.asStateFlow()

    fun setName(name: String) {
        //state.value = state.value.copy(name = name)
        _uiState.update {
            it.copy(name = name)
        }
    }

    fun setPassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    //val name: String
    //    get() = state.value.name

    //val password: String
    //    get() = state.value.password
}