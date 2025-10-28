package com.example.huertogourmet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platos")
data class Plato(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double
)