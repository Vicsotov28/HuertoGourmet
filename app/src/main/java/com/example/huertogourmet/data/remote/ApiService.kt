package com.example.huertogourmet.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // --- PLATOS ---
    @GET("dishes")
    suspend fun obtenerDishes(): Response<List<PlatoRemote>>

    @GET("dishes/{id}")
    suspend fun obtenerDish(@Path("id") id: Long): Response<PlatoRemote>

    // SI deseas crear/editar desde Android (opcional)
    @POST("dishes")
    suspend fun crearDish(@Body plato: PlatoRemote): Response<PlatoRemote>

    @PUT("dishes/{id}")
    suspend fun actualizarDish(@Path("id") id: Long, @Body plato: PlatoRemote): Response<PlatoRemote>

    @DELETE("dishes/{id}")
    suspend fun eliminarDish(@Path("id") id: Long): Response<Unit>

    // --- USUARIOS ---
    @GET("users")
    suspend fun obtenerUsuarios(): Response<List<UsuarioRemote>>

    @GET("users/{id}")
    suspend fun obtenerUsuario(@Path("id") id: Long): Response<UsuarioRemote>

    @POST("users")
    suspend fun crearUsuario(@Body usuario: UsuarioRemote): Response<UsuarioRemote>

    // --- NUEVO: SUBIDA DE IMÁGENES (XANO) ---
    // Asegúrate que la ruta ("upload/image" o "upload") sea la misma de tu Xano
    @Multipart
    @POST("upload/image")
    suspend fun subirImagen(
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>

    // --- NUEVO: COMENTARIOS ---
    @GET("comments")
    suspend fun obtenerComentarios(@Query("platoId") platoId: Long?): Response<List<ComentarioRemote>>

    @POST("comments")
    suspend fun crearComentario(@Body comentario: ComentarioRemote): Response<ComentarioRemote>
}