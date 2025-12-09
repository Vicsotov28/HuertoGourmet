package com.example.huertogourmet

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    // Regla para iniciar la actividad principal
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun cuandoIngresoDatosYPresionoBoton_LaAppNoCrashea() {
        // IMPORTANTE: Asegúrate de que la App inicia en el Login o navega hacia él.
        // Si tu app inicia en "Inicio", primero debemos ir al Login.

        // 1. Navegar al Login (Si es necesario)
        // Busca el botón que lleva al login en tu InicioScreen.
        // Si ya inicia en Login, borra las siguientes 2 líneas.
        if (existeTexto("Iniciar Sesión")) {
            composeTestRule.onNodeWithText("Iniciar Sesión").performClick()
        }

        // 2. Buscar campos y escribir (Usamos los textos que tienes en tu LoginScreen)
        composeTestRule
            .onNodeWithText("Correo Electrónico") // Debe coincidir exacto con tu UI
            .performTextInput("vicentee@gmail.com")

        composeTestRule
            .onNodeWithText("Telefono") // Debe coincidir exacto con tu UI
            .performTextInput("112233445")

        composeTestRule
            .onNodeWithText("Contraseña") // Debe coincidir exacto
            .performTextInput("1122334")

        // 3. Cerrar teclado para asegurar que se ve el botón
        composeTestRule.onRoot().performTouchInput { swipeUp() }

        // 4. Click en el botón de ingresar
        // Busca el texto exacto dentro del botón de tu LoginScreen
        // Puede ser "Ingresar", "Login", "Iniciar Sesión", etc. Revisa tu código UI.
        composeTestRule
            .onNodeWithText("Iniciar Sesión") // <--- Revisa si tu botón dice "Ingresar" o "Iniciar Sesión"
            .assertExists()
            .performClick()

        // 5. Esperar resultado
        composeTestRule.waitForIdle()

        // Aquí no podemos validar mucho más sin modificar código,
        // pero si el test llega aquí sin error en rojo, ¡Pasó!
    }
    // Función auxiliar para verificar si existe un texto sin fallar
    private fun existeTexto(texto: String): Boolean {
        return try {
            composeTestRule.onNodeWithText(texto).assertExists()
            true
        } catch (e: AssertionError) {
            false
        }
    }
}