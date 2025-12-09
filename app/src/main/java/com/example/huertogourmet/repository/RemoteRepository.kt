package com.example.huertogourmet.repository

import com.example.huertogourmet.data.remote.ComentarioRemote
import com.example.huertogourmet.data.remote.PlatoRemote
import com.example.huertogourmet.data.remote.RetrofitInstance
import com.example.huertogourmet.data.remote.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class RemoteRepository {

    // --- PLATOS (Ya lo tenías) ---
    suspend fun obtenerPlatosRemotos(): Response<List<PlatoRemote>> =
        RetrofitInstance.api.obtenerDishes()

    suspend fun obtenerPlatoRemoto(id: Long): Response<PlatoRemote> =
        RetrofitInstance.api.obtenerDish(id)

    // --- NUEVO: SUBIDA DE IMAGENES (Para Xano) ---
    suspend fun subirImagen(archivo: File): Response<UploadResponse> {
        // 1. Preparamos el archivo indicando que es una imagen
        val requestFile = archivo.asRequestBody("image/*".toMediaTypeOrNull())

        // 2. Creamos la "parte" del formulario.
        // IMPORTANTE: "file" es el nombre del Input que configuramos en Xano
        val body = MultipartBody.Part.createFormData("file", archivo.name, requestFile)

        // 3. Llamamos a la API
        return RetrofitInstance.api.subirImagen(body)
    }

    // --- NUEVO: COMENTARIOS ---
    suspend fun crearComentario(comentario: ComentarioRemote): Response<ComentarioRemote> {
        return RetrofitInstance.api.crearComentario(comentario)
    }

    suspend fun obtenerDishesRemotos(): Response<List<PlatoRemote>> =
        RetrofitInstance.api.obtenerDishes()

}