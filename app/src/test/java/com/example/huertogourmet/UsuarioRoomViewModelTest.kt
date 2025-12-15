package com.example.huertogourmet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.huertogourmet.data.model.Usuario
import com.example.huertogourmet.repository.RepositorioApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class UsuarioRoomViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repositorio: RepositorioApp

    @Before
    fun setup() {
        repositorio = mock(RepositorioApp::class.java)
    }

    @Test
    fun loginUsuario_correcto_retornaTrue() = runTest {

        val correo = "test@gmail.com"
        val clave = "123456"

        val usuarioMock = Usuario(
            id = 1,
            nombre = "Test",
            correo = correo,
            clave = clave,
            telefono = "123456789"
        )

        `when`(repositorio.obtenerUsuarioPorCorreoYClave(correo, clave))
            .thenReturn(usuarioMock)

        val resultado = repositorio.obtenerUsuarioPorCorreoYClave(correo, clave)

        assertNotNull(resultado)
    }

    @Test
    fun loginUsuario_incorrecto_retornaNull() = runTest {

        val correo = "fake@gmail.com"
        val clave = "000000"

        `when`(repositorio.obtenerUsuarioPorCorreoYClave(correo, clave))
            .thenReturn(null)

        val resultado = repositorio.obtenerUsuarioPorCorreoYClave(correo, clave)

        assertNull(resultado)
    }
}