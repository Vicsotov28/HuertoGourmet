package com.example.huertogourmet.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertogourmet.data.remote.ComentarioRemote
import com.example.huertogourmet.repository.RemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

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
        part: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            _isUploading.value = true
            _lastResult.value = null

            try {
                var imagenUrl: String? = null

                // 1. Subir imagen a Xano
                if (part != null) {
                    val resp = repo.subirImagen(part)

                    if (resp.isSuccessful && resp.body() != null) {
                        imagenUrl = resp.body()!!.path
                    } else {
                        _lastResult.value = "Error al subir imagen: ${resp.code()}"
                        _isUploading.value = false
                        return@launch
                    }
                }

                // 2. Crear comentario
                val comentario = ComentarioRemote(
                    id = 0,
                    platoId = platoId,
                    usuarioId = usuarioId,
                    texto = texto,
                    imagenUrl = imagenUrl,
                    creadoEn = null
                )

                val createResp = repo.crearComentario(comentario)

                _lastResult.value =
                    if (createResp.isSuccessful)
                        "Comentario creado correctamente"
                    else
                        "Error al crear comentario: ${createResp.code()}"

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