package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertogourmet.R
import com.example.huertogourmet.presentation.navigation.Screen
import com.example.huertogourmet.presentation.viewmodel.UsuarioRoomViewModel
import com.example.huertogourmet.presentation.viewmodel.UsuarioRoomViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current.applicationContext as android.app.Application
    val viewModel: UsuarioRoomViewModel = viewModel(
        factory = UsuarioRoomViewModelFactory(context)
    )

    val scope = rememberCoroutineScope()
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Animacion Cargando
    LaunchedEffect(Unit) {
        delay(600)
        isLoading = false
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val focusManager = LocalFocusManager.current
        val claveRequester = remember { FocusRequester() }

        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text("Huerto Gourmet 🍃") }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.huertogourmet_logo),
                    contentDescription = "Logo Huerto Gourmet",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(Modifier.height(16.dp))

                // Campo de correo
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { claveRequester.requestFocus() }
                    )
                )

                // Campo de contraseña
                OutlinedTextField(
                    value = clave,
                    onValueChange = { clave = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(claveRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            scope.launch {
                                val ok = viewModel.loginUsuario(correo, clave)
                                if (ok) {
                                    navController.navigate(Screen.Menu.route)
                                } else {
                                    mensajeError = "Correo o contraseña incorrectos"
                                }
                            }
                        }
                    )
                )

                mensajeError?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                Spacer(Modifier.height(16.dp))

                // Animacion del boton
                var pressed by remember { mutableStateOf(false) }
                val bgColor by animateColorAsState(
                    targetValue = if (pressed) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = {
                        pressed = true
                        scope.launch {
                            val ok = viewModel.loginUsuario(correo, clave)
                            delay(150)
                            pressed = false
                            if (ok) {
                                navController.navigate(Screen.Menu.route)
                            } else {
                                mensajeError = "Correo o contraseña incorrectos"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = bgColor)
                ) {
                    Text("Iniciar Sesión")
                }

                OutlinedButton(
                    onClick = { navController.navigate(Screen.Registro.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse")
                }
            }
        }
    }
}
