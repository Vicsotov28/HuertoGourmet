package com.example.huertogourmet.data.remote


import com.google.gson.annotations.SerializedName

data class UploadResponse(
    // Xano en tu prueba devuelve 'path' (ej: /vault/....png).
    // Guardamos path y algunos metadatos por si después los quieres usar.
    @SerializedName("path") val path: String?,
    @SerializedName("access") val access: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("size") val size: Int? = null,
    @SerializedName("mime") val mime: String? = null
)