package com.example.huertogourmet.data.remote

data class ComentarioRemote(
    val id: Long = 0,
    val platoId: Long,
    val usuarioId: Long,
    val texto: String,
    val imagenUrl: String? = null,
    val creadoEn: String? = null // ISO date string opcional
)