package com.example.huertogourmet.repository
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.data.model.Usuario
import com.example.huertogourmet.data.remote.PlatoRemote
import com.example.huertogourmet.data.remote.UsuarioRemote

fun PlatoRemote.toPlatoLocal(): Plato = Plato(
    id = this.id,
    nombre = this.nombre,
    descripcion = this.descripcion,
    precio = this.precio,
    imagen = this.imagen
)

fun Plato.toPlatoRemote(): PlatoRemote = PlatoRemote(
    id = this.id,
    nombre = this.nombre,
    descripcion = this.descripcion,
    precio = this.precio,
    imagen = this.imagen
)

fun UsuarioRemote.toUsuarioLocal(): Usuario = Usuario(
    id = this.id,
    nombre = this.nombre ?: "",
    correo = this.correo ?: "",
    clave = this.clave ?: "",
    telefono = this.telefono ?: ""
)

fun Usuario.toUsuarioRemote(): UsuarioRemote = UsuarioRemote(
    id = this.id,
    nombre = this.nombre,
    correo = this.correo,
    telefono = this.telefono,
    clave = this.clave
)