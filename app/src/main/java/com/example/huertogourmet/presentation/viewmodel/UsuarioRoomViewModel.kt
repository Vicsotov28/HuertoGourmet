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

    val usuarios = repo.obtenerUsuarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun crearUsuario(nombre: String, correo: String, clave: String) {
        viewModelScope.launch {
            val nuevo = Usuario(nombre = nombre, correo = correo, clave = clave)
            repo.insertarUsuario(nuevo)
        }
    }

    suspend fun loginUsuario(correo: String, clave: String): Boolean {
        val usuario = repo.obtenerUsuarioPorCorreo(correo)
        return usuario?.clave == clave
    }

    fun actualizarUsuario(usuario: Usuario) {
        viewModelScope.launch { repo.actualizarUsuario(usuario) }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch { repo.eliminarUsuario(usuario) }
    }
}
