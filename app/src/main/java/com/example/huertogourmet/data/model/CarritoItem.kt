package com.example.huertogourmet.data.model

data class CarritoItem(
    val id: Long = System.currentTimeMillis(), // simple id local
    val plato: Plato,
    var cantidad: Int
) {
    val subtotal: Double
        get() = plato.precio * cantidad
}
