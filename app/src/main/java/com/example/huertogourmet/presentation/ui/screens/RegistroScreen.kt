package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertogourmet.presentation.navigation.Screen
import com.example.huertogourmet.presentation.viewmodel.UsuarioRoomViewModel
import com.example.huertogourmet.presentation.viewmodel.UsuarioRoomViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController
) {
    val application = LocalContext.current.applicationContext as android.app.Application
    val viewModel: UsuarioRoomViewModel = viewModel(
        factory = UsuarioRoomViewModelFactory(application)
    )

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val correoRequester = remember { FocusRequester() }
    val claveRequester = remember { FocusRequester() }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    var mensajeError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Registro de Usuario 🌱") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { correoRequester.requestFocus() }
                )
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(correoRequester),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { claveRequester.requestFocus() }
                )
            )

            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(claveRequester),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )
                Spacer(Modifier.width(8.dp))
                Text("Acepto los términos y condiciones")
            }

            mensajeError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = {
                    mensajeError = null

                    if (nombre.isBlank()) {
                        mensajeError = "Ingrese su nombre"
                        return@Button
                    }
                    if (correo.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        mensajeError = "Correo inválido"
                        return@Button
                    }
                    if (clave.length < 6) {
                        mensajeError = "La contraseña debe tener al menos 6 caracteres"
                        return@Button
                    }
                    if (!aceptaTerminos) {
                        mensajeError = "Debe aceptar los términos"
                        return@Button
                    }

                    isLoading = true

                    scope.launch {
                        viewModel.crearUsuario(
                            nombre = nombre,
                            correo = correo,
                            clave = clave,
                            telefono = telefono
                        ) { ok ->
                            isLoading = false
                            if (ok) {
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Registro.route) { inclusive = true }
                                }
                            } else {
                                mensajeError = "No se pudo registrar el usuario"
                            }
                        }
                    }
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Registrar")
                }
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Screen.Login.route) }
            ) {
                Text("Volver al login")
            }
        }
    }
}