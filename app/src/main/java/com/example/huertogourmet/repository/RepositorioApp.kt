package com.example.huertogourmet.repository

import android.content.Context
import com.example.huertogourmet.data.AppDatabase
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.data.model.Usuario
import com.example.huertogourmet.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RepositorioApp(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val usuarioDao = db.usuarioDao()
    private val platoDao = db.platoDao()
    private val remoteRepo = RemoteRepository()

    // ----------------------------
    // SINCRONIZACIÓN REMOTA
    // ----------------------------

    suspend fun sincronizarPlatosDesdeRemoto() {
        withContext(Dispatchers.IO) {
            try {
                val resp = remoteRepo.obtenerPlatosRemotos()
                if (resp.isSuccessful) {
                    val listaRemota = resp.body() ?: emptyList()
                    listaRemota.forEach {
                        platoDao.insertar(it.toPlatoLocal())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun sincronizarUsuariosDesdeRemoto() {
        withContext(Dispatchers.IO) {
            try {
                val resp = RetrofitInstance.api.obtenerUsuarios()
                if (resp.isSuccessful) {
                    val lista = resp.body() ?: emptyList()
                    lista.forEach {
                        usuarioDao.insertar(it.toUsuarioLocal())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ----------------------------
    // ROOM - USUARIOS
    // ----------------------------

    fun obtenerUsuarios(): Flow<List<Usuario>> = usuarioDao.obtenerTodos()

    suspend fun insertarUsuario(u: Usuario): Long = usuarioDao.insertar(u)
    suspend fun actualizarUsuario(u: Usuario) = usuarioDao.actualizar(u)
    suspend fun eliminarUsuario(u: Usuario) = usuarioDao.eliminar(u)

    suspend fun obtenerUsuarioPorCorreoYClave(
        correo: String,
        clave: String
    ): Usuario? {
        return usuarioDao.obtenerUsuarioLogin(correo, clave)
    }

    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario? {
        return usuarioDao.obtenerPorCorreo(correo)
    }

    // ----------------------------
    // ROOM - PLATOS
    // ----------------------------

    fun obtenerPlatos(): Flow<List<Plato>> = platoDao.obtenerTodos()
    suspend fun insertarPlato(p: Plato): Long = platoDao.insertar(p)
    suspend fun actualizarPlato(p: Plato) = platoDao.actualizar(p)
    suspend fun eliminarPlato(p: Plato) = platoDao.eliminar(p)
}