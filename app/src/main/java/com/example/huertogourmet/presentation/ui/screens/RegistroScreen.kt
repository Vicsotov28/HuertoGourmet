package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertogourmet.presentation.navigation.Screen
import com.example.huertogourmet.presentation.viewmodel.UsuarioViewModel
import com.example.huertogourmet.presentation.viewmodel.UsuarioRoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    usuarioVM: UsuarioViewModel = viewModel(),
    roomVM: UsuarioRoomViewModel = viewModel()
) {
    val estado by usuarioVM.estado.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Registro de Usuario 🌿") })
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Campos del formulario ---
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = usuarioVM::onNombreChange,
                label = { Text("Nombre") },
                isError = estado.errores["nombre"] != null,
                supportingText = {
                    estado.errores["nombre"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.correo,
                onValueChange = usuarioVM::onCorreoChange,
                label = { Text("Correo Electrónico") },
                isError = estado.errores["correo"] != null,
                supportingText = {
                    estado.errores["correo"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado.clave,
                onValueChange = usuarioVM::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores["clave"] != null,
                supportingText = {
                    estado.errores["clave"]?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = usuarioVM::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text("Acepto los términos y condiciones")
            }

            estado.errores["aceptaTerminos"]?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            // --- Botón de registro ---
            Button(
                onClick = {
                    if (usuarioVM.validarFormulario()) {
                        // Guardar el usuario en Room
                        roomVM.crearUsuario(
                            nombre = estado.nombre,
                            apellido = "—", // luego puedes agregar este campo
                            correo = estado.correo
                        )

                        // Navegar al menú principal
                        navController.navigate(Screen.Menu.route)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }
    }
}
