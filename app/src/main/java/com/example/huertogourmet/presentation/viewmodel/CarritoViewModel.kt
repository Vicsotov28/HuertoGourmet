package com.example.huertogourmet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.data.model.CarritoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CarritoViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<CarritoItem>>(emptyList())
    val items = _items.asStateFlow()

    private val _totalPagar = MutableStateFlow(0.0)
    val totalPagar = _totalPagar.asStateFlow()

    /** Agrega un plato al carrito. Si ya existe, suma la cantidad. */
    fun agregarAlCarrito(plato: Plato, cantidad: Int) {
        if (cantidad <= 0) return
        val current = _items.value.toMutableList()
        val idx = current.indexOfFirst { it.plato.id == plato.id }
        if (idx >= 0) {
            val old = current[idx]
            current[idx] = old.copy(cantidad = old.cantidad + cantidad)
        } else {
            current.add(CarritoItem(plato = plato, cantidad = cantidad))
        }
        _items.value = current
        recalcularTotal()
    }

    /** Remueve un item (por plato) */
    fun removerDelCarrito(item: CarritoItem) {
        val current = _items.value.toMutableList()
        current.removeAll { it.plato.id == item.plato.id }
        _items.value = current
        recalcularTotal()
    }

    /** Vacia el carrito */
    fun vaciarCarrito() {
        _items.value = emptyList()
        _totalPagar.value = 0.0
    }

    /** Devuelve el id del primer plato del carrito (útil para ir a comentarios post-compra) */
    fun primerPlatoCompradoId(): Long? {
        return _items.value.firstOrNull()?.plato?.id
    }

    private fun recalcularTotal() {
        _totalPagar.value = _items.value.sumOf { it.subtotal }
    }
}
