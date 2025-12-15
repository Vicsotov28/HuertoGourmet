package com.example.huertogourmet.utils
import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun crearMultipartDesdeUri(
    context: Context,
    uri: Uri
): MultipartBody.Part {

    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri) ?: "image/*"

    val inputStream = contentResolver.openInputStream(uri)!!
    val bytes = inputStream.readBytes()
    inputStream.close()

    val requestBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(
        name = "content", // 🔥 MISMO NOMBRE QUE EN XANO
        filename = "imagen.jpg",
        body = requestBody
    )
}
