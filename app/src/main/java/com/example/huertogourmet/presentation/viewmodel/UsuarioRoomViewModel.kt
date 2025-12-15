package com.example.huertogourmet.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertogourmet.data.model.Usuario
import com.example.huertogourmet.repository.RepositorioApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.huertogourmet.repository.UsuarioRemoteRepository

class UsuarioRoomViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepositorioApp(application)
    private val remoteRepo = UsuarioRemoteRepository()

    val usuarios = repo.obtenerUsuarios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            repo.sincronizarUsuariosDesdeRemoto()
            repo.sincronizarPlatosDesdeRemoto()
        }
    }

    fun crearUsuario(nombre: String, correo: String, clave: String, telefono: String, onResultado: (Boolean) -> Unit) {
        viewModelScope.launch {
            val nuevo = Usuario(
                nombre = nombre,
                correo = correo,
                clave = clave,
                telefono = telefono
            )
            val ok = repo.crearUsuarioCompleto(nuevo)
            onResultado(ok)
        }
    }

    suspend fun loginUsuario(correo: String, clave: String): Boolean {
        val usuario = repo.obtenerUsuarioPorCorreoYClave(correo, clave)
        return usuario != null
    }
}
