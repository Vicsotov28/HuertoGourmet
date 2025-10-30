package com.example.huertogourmet.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UsuarioRoomViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioRoomViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
