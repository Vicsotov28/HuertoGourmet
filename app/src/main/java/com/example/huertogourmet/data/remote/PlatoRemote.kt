package com.example.huertogourmet.data.remote

data class PlatoRemote(
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String? = null
)