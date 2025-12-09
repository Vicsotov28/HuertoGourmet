package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertogourmet.R
import com.example.huertogourmet.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Huerto Gourmet 🍃") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.huertogourmet_logo),
                contentDescription = "Logo Huerto Gourmet",
                modifier = Modifier.size(150.dp)
            )

            Spacer(Modifier.height(16.dp))
            Text("Bienvenido a Huerto Gourmet", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.Registro.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.Menu.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Menú")
            }
        }
    }
}
