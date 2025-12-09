package com.example.huertogourmet.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertogourmet.data.remote.ComentarioRemote
import com.example.huertogourmet.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ComentariosViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RemoteRepository()

    // Estados observables para la UI
    private val _isUploading = MutableStateFlow(false)
    val isUploading = _isUploading.asStateFlow()

    private val _lastResult = MutableStateFlow<String?>(null)
    val lastResult = _lastResult.asStateFlow()

    fun crearComentarioConImagen(
        platoId: Long,
        usuarioId: Long,
        texto: String,
        file: File? // Si es null, se crea comentario sin foto
    ) {
        viewModelScope.launch {
            _isUploading.value = true
            _lastResult.value = null

            try {
                var imagenUrl: String? = null

                // 1. Subir imagen a Xano (si existe)
                if (file != null) {
                    // Usamos IO para operaciones de red
                    val resp = withContext(Dispatchers.IO) { repo.subirImagen(file) }

                    if (resp.isSuccessful && resp.body() != null) {
                        // Xano devuelve un objeto con la URL (según tu UploadResponse)
                        imagenUrl = resp.body()!!.path
                    } else {
                        _lastResult.value = "Error al subir imagen: ${resp.code()}"
                        _isUploading.value = false
                        return@launch
                    }
                }

                // 2. Crear el objeto comentario
                val comentario = ComentarioRemote(
                    id = 0, // Xano genera el ID
                    platoId = platoId,
                    usuarioId = usuarioId,
                    texto = texto,
                    imagenUrl = imagenUrl, // Pasamos la URL obtenida (o null)
                    creadoEn = null
                )

                // 3. Enviar comentario a la base de datos
                val createResp = withContext(Dispatchers.IO) { repo.crearComentario(comentario) }

                if (createResp.isSuccessful) {
                    _lastResult.value = "Comentario creado correctamente"
                } else {
                    _lastResult.value = "Error al crear comentario: ${createResp.code()}"
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _lastResult.value = "Error: ${e.message}"
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun limpiarResultado() {
        _lastResult.value = null
    }
}