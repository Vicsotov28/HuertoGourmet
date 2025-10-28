package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertogourmet.presentation.navigation.Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Huerto Gourmet 🍃") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.Menu.route)
                    },
                    modifier = Modifier.width(220.dp)
                ) {
                    Text("Entrar al Menú")
                }

                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.Registro.route)
                    },
                    modifier = Modifier.width(220.dp)
                ) {
                    Text("Registrarme")
                }
            }
        }
    }
}