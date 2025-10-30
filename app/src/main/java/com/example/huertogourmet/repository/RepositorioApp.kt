package com.example.huertogourmet.repository

import android.content.Context
import com.example.huertogourmet.data.AppDatabase
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.data.model.Usuario
import kotlinx.coroutines.flow.Flow

class RepositorioApp(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val usuarioDao = db.usuarioDao()
    private val platoDao = db.platoDao()

    // Usuarios
    fun obtenerUsuarios(): Flow<List<Usuario>> = usuarioDao.obtenerTodos()
    suspend fun insertarUsuario(u: Usuario): Long = usuarioDao.insertar(u)
    suspend fun actualizarUsuario(u: Usuario) = usuarioDao.actualizar(u)
    suspend fun eliminarUsuario(u: Usuario) = usuarioDao.eliminar(u)

    suspend fun obtenerUsuarioPorId(id: Long) = usuarioDao.obtenerPorId(id)

    suspend fun obtenerUsuarioPorCorreo(correo: String) = usuarioDao.obtenerPorCorreo(correo) // 👈 Nuevo método

    // Platos
    fun obtenerPlatos(): Flow<List<Plato>> = platoDao.obtenerTodos()
    suspend fun insertarPlato(p: Plato): Long = platoDao.insertar(p)
    suspend fun actualizarPlato(p: Plato) = platoDao.actualizar(p)
    suspend fun eliminarPlato(p: Plato) = platoDao.eliminar(p)
}
