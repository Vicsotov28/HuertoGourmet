package com.example.huertogourmet.data.dao

import androidx.room.*
import com.example.huertogourmet.data.model.Plato
import kotlinx.coroutines.flow.Flow

@Dao
interface PlatoDao {
    @Query("SELECT * FROM platos ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<Plato>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(plato: Plato): Long

    @Update
    suspend fun actualizar(plato: Plato)

    @Delete
    suspend fun eliminar(plato: Plato)
}
