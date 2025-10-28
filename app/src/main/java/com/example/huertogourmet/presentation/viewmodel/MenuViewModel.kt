package com.example.huertogourmet.presentation.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.repository.RepositorioApp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = RepositorioApp(application)

    val platos = repo.obtenerPlatos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun agregarPlato(nombre: String, descripcion: String, precio: Double) {
        viewModelScope.launch {
            val p = Plato(nombre = nombre, descripcion = descripcion, precio = precio)
            repo.insertarPlato(p)
        }
    }

    fun actualizarPlato(plato: Plato) {
        viewModelScope.launch { repo.actualizarPlato(plato) }
    }

    fun eliminarPlato(plato: Plato) {
        viewModelScope.launch { repo.eliminarPlato(plato) }
    }
}
