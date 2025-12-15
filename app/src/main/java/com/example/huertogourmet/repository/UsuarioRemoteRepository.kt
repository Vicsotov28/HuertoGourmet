package com.example.huertogourmet.repository

import com.example.huertogourmet.data.remote.UsuarioRemote
import com.example.huertogourmet.data.remote.RetrofitInstance
import retrofit2.Response

class UsuarioRemoteRepository {
    suspend fun obtenerUsuario(id: Long): Response<UsuarioRemote> = RetrofitInstance.api.obtenerUsuario(id)
    suspend fun crearUsuario(usuario: UsuarioRemote): Response<UsuarioRemote> = RetrofitInstance.api.crearUsuario(usuario)
}