package com.example.huertogourmet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: Map<String, String?> = emptyMap()
)

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado = _estado.asStateFlow()

    private val usuariosRegistrados = mutableListOf<Pair<String, String>>() // (correo, clave)

    // --- Manejadores de cambios ---
    fun onNombreChange(valor: String) {
        _estado.value = _estado.value.copy(nombre = valor)
    }

    fun onCorreoChange(valor: String) {
        _estado.value = _estado.value.copy(correo = valor)
    }

    fun onClaveChange(valor: String) {
        _estado.value = _estado.value.copy(clave = valor)
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.value = _estado.value.copy(aceptaTerminos = valor)
    }

    // --- Validación ---
    fun validarFormulario(): Boolean {
        val errores = mutableMapOf<String, String?>()

        if (_estado.value.nombre.isBlank()) errores["nombre"] = "Ingrese su nombre"
        if (_estado.value.correo.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_estado.value.correo).matches())
            errores["correo"] = "Correo inválido"
        if (_estado.value.clave.length < 6) errores["clave"] = "Mínimo 6 caracteres"
        if (!_estado.value.aceptaTerminos) errores["aceptaTerminos"] = "Debe aceptar los términos"

        _estado.value = _estado.value.copy(errores = errores)
        return errores.isEmpty()
    }

    fun registrarUsuario() {
        viewModelScope.launch {
            usuariosRegistrados.add(_estado.value.correo to _estado.value.clave)
        }
    }

    fun validarLogin(correo: String, clave: String): Boolean {
        return usuariosRegistrados.any { it.first == correo && it.second == clave }
    }
}
