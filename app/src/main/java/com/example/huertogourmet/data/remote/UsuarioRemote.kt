package com.example.huertogourmet.data.remote


data class UsuarioRemote(
    val id: Long = 0,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val clave: String? = null
)