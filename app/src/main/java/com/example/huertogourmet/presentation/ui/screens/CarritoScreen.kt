package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.huertogourmet.presentation.viewmodel.CarritoViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import com.example.huertogourmet.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {
    val items by carritoViewModel.items.collectAsState()
    val total by carritoViewModel.totalPagar.collectAsState(initial = 0.0)

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Tu Pedido 🛒") }) },
        bottomBar = {
            if (items.isNotEmpty()) {
                Surface(tonalElevation = 8.dp, shadowElevation = 8.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total:", style = MaterialTheme.typography.titleLarge)
                            Text("$${"%,.0f".format(total)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            val primerId = carritoViewModel.primerPlatoCompradoId() ?: 0L
                            carritoViewModel.vaciarCarrito()
                            if (primerId > 0L) {
                                navController.navigate(Screen.ComentariosDetail.createRoute(primerId, 0L))
                            } else {
                                navController.popBackStack()
                            }
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text("Confirmar Compra")
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("El carrito está vacío 😔")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(items) { item ->
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {

                            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                if (!item.plato.imagen.isNullOrBlank()) {
                                    AsyncImage(model = item.plato.imagen, contentDescription = item.plato.nombre, modifier = Modifier.size(64.dp).padding(end = 8.dp))
                                } else {
                                    // placeholder local
                                    AsyncImage(model = null, contentDescription = item.plato.nombre, modifier = Modifier.size(64.dp).padding(end = 8.dp))
                                }

                                Column {
                                    Text(item.plato.nombre, style = MaterialTheme.typography.titleMedium)
                                    Text("${item.cantidad} x $${"%,.0f".format(item.plato.precio)}")
                                    Text("Subtotal: $${"%,.0f".format(item.subtotal)}", color = MaterialTheme.colorScheme.primary)
                                }
                            }

                            IconButton(onClick = { carritoViewModel.removerDelCarrito(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}