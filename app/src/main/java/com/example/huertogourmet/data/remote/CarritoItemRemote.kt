package com.example.huertogourmet.data.remote

data class CarritoItemRemote(
    val id: Long = 0,
    val platoId: Long,
    val cantidad: Int,
    val usuarioId: Long? = null
)