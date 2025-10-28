package com.example.huertogourmet.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertogourmet.data.model.Usuario
import com.example.huertogourmet.repository.RepositorioApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioRoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepositorioApp(application)

    // Flow que expone la lista de usuarios
    val usuarios = repo.obtenerUsuarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )


    fun crearUsuario(nombre: String, apellido: String, correo: String) {
        viewModelScope.launch {
            val nuevo = Usuario(nombre = nombre, apellido = apellido, correo = correo)
            repo.insertarUsuario(nuevo)
        }
    }

    fun actualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repo.actualizarUsuario(usuario)
        }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repo.eliminarUsuario(usuario)
        }
    }
}
