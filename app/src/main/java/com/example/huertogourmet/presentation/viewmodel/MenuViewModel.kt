package com.example.huertogourmet.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.repository.RemoteRepository
import com.example.huertogourmet.repository.RepositorioApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.huertogourmet.repository.toPlatoLocal

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repoLocal = RepositorioApp(application)
    private val repoRemote = RemoteRepository()

    // Flow de platos (viene de Room)
    val platos = repoLocal.obtenerPlatos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    // Estado para la UI
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        // Intentamos sincronizar al iniciar
        sincronizarConRemoto()
    }

    /**
     * Sincroniza DB local con remoto.
     * Intenta primero la ruta principal (obtenerPlatosRemotos).
     * Si falla (404, UnknownHost u otro) intenta la ruta alternativa (dishes).
     */
    fun sincronizarConRemoto() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                withContext(Dispatchers.IO) {
                    var resp = try {
                        repoRemote.obtenerPlatosRemotos()
                    } catch (e: Exception) {
                        Log.w("MenuViewModel", "Error primer intento (platos): ${e.message}")
                        null
                    }

                    // Si respuesta nula o no OK, probamos ruta alternativa (dishes)
                    if (resp == null || !resp.isSuccessful) {
                        Log.i("MenuViewModel", "Intentando endpoint alternativo 'dishes' ...")
                        val altResp = try {
                            repoRemote.obtenerDishesRemotos()
                        } catch (e: Exception) {
                            Log.w("MenuViewModel", "Error segundo intento (dishes): ${e.message}")
                            null
                        }
                        if (altResp != null && altResp.isSuccessful) {
                            resp = altResp
                        } else {
                            // si altResp existe y no es successful, preferimos mostrar su code
                            if (altResp != null) {
                                val msg = "Error remota (dishes) ${altResp.code()} ${altResp.message()}"
                                Log.e("MenuViewModel", msg)
                                _error.value = msg
                            } else if (resp != null) {
                                val msg = "Error remota (platos) ${resp.code()} ${resp.message()}"
                                Log.e("MenuViewModel", msg)
                                _error.value = msg
                            } else {
                                _error.value = "No se pudo contactar al servidor remoto"
                            }
                        }
                    }

                    // Si ya tenemos resp exitosa, insertamos en BD local
                    if (resp != null && resp.isSuccessful) {
                        val listaRemota = resp.body() ?: emptyList()
                        // Opcional: si querés limpiar la tabla antes, podés implementarlo aquí
                        listaRemota.forEach { remote ->
                            val local = remote.toPlatoLocal()
                            // insertar o REPLACE
                            repoLocal.insertarPlato(local)
                        }
                        Log.d("MenuViewModel", "Sincronización OK: ${listaRemota.size} platos")
                    }
                }
            } catch (e: Exception) {
                Log.e("MenuViewModel", "Exception sincronizar", e)
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Métodos para manipular BD local (opcional)
    fun agregarPlato(nombre: String, descripcion: String, precio: Double) {
        viewModelScope.launch {
            val p = Plato(nombre = nombre, descripcion = descripcion, precio = precio)
            repoLocal.insertarPlato(p)
        }
    }

    fun actualizarPlato(plato: Plato) {
        viewModelScope.launch { repoLocal.actualizarPlato(plato) }
    }

    fun eliminarPlato(plato: Plato) {
        viewModelScope.launch { repoLocal.eliminarPlato(plato) }
    }
}
