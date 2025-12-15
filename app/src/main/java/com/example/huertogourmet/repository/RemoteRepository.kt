package com.example.huertogourmet.repository

import com.example.huertogourmet.data.remote.ComentarioRemote
import com.example.huertogourmet.data.remote.PlatoRemote
import com.example.huertogourmet.data.remote.RetrofitInstance
import com.example.huertogourmet.data.remote.UploadResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response

class RemoteRepository {

    // --- PLATOS (Ya lo tenías) ---
    suspend fun obtenerPlatosRemotos(): Response<List<PlatoRemote>> =
        RetrofitInstance.api.obtenerDishes()

    suspend fun obtenerPlatoRemoto(id: Long): Response<PlatoRemote> =
        RetrofitInstance.api.obtenerDish(id)

    // --- NUEVO: SUBIDA DE IMAGENES (Para Xano) ---
    suspend fun subirImagen(part: MultipartBody.Part) =
        RetrofitInstance.api.subirImagen(part)

    // --- NUEVO: COMENTARIOS ---
    suspend fun crearComentario(comentario: ComentarioRemote): Response<ComentarioRemote> {
        return RetrofitInstance.api.crearComentario(comentario)
    }

    suspend fun obtenerDishesRemotos(): Response<List<PlatoRemote>> =
        RetrofitInstance.api.obtenerDishes()

}