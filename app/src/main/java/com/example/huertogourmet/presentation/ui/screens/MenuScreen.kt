package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage // Librería para cargar imágenes de internet
import com.example.huertogourmet.R
import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.presentation.navigation.Screen
import com.example.huertogourmet.presentation.viewmodel.CarritoViewModel
import com.example.huertogourmet.presentation.viewmodel.MenuViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.RowScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = viewModel(),
    carritoViewModel: CarritoViewModel
) {
    val platos by viewModel.platos.collectAsState(initial = emptyList())
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(500)
        isLoading = false
    }

    var selectedPlato by remember { mutableStateOf<Plato?>(null) }
    var showCantidadDialog by remember { mutableStateOf(false) }
    var cantidadSeleccionada by remember { mutableStateOf(1) }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF2E7D32))
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = { Text("Menú Huerto Gourmet 🌿", fontWeight = FontWeight.Bold) })
            },
            bottomBar = { BottomBar(navController) }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F4))
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Logo
                item {
                    Image(
                        painter = painterResource(R.drawable.huertogourmet_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(90.dp).padding(top = 8.dp)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "HuertoGourmet Santiago",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(Modifier.height(10.dp))
                }

                // Lista de Platos
                if (platos.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text("Cargando menú...", color = Color.Gray)
                        }
                    }
                } else {
                    items(platos) { plato ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // --- Carga de Imagen ---
                                // Si hay URL, usa AsyncImage. Si no, usa imagen local por defecto.
                                if (!plato.imagen.isNullOrBlank()) {
                                    AsyncImage(
                                        model = plato.imagen,
                                        contentDescription = plato.nombre,
                                        modifier = Modifier.size(90.dp).clip(RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(Modifier.width(12.dp))

                                Column(Modifier.weight(1f)) {
                                    Text(
                                        plato.nombre,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                    )
                                    Text(
                                        plato.descripcion,
                                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF555555)),
                                        maxLines = 2
                                    )
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        "$${"%,.0f".format(plato.precio)}",
                                        color = Color(0xFF388E3C),
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Botones
                                Column {
                                    IconButton(onClick = {
                                        navController.navigate(Screen.ComentariosDetail.createRoute(plato.id, 1L))
                                    }) {
                                        Text("💬", fontSize = 20.sp)
                                    }
                                    Button(onClick = {
                                        selectedPlato = plato
                                        cantidadSeleccionada = 1
                                        showCantidadDialog = true
                                    }) {
                                        Text("+")
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(40.dp))
                    Text("🍃 Gracias por visitar HuertoGourmet 🍃", textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
    }

    // Dialogo
    if (showCantidadDialog && selectedPlato != null) {
        AlertDialog(
            onDismissRequest = { showCantidadDialog = false },
            confirmButton = {
                Button(onClick = {
                    carritoViewModel.agregarAlCarrito(selectedPlato!!, cantidadSeleccionada)
                    showCantidadDialog = false
                }) { Text("Agregar") }
            },
            dismissButton = { OutlinedButton(onClick = { showCantidadDialog = false }) { Text("Cancelar") } },
            title = { Text("Cantidad") },
            text = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = { if (cantidadSeleccionada > 1) cantidadSeleccionada-- }) { Text("-") }
                    Spacer(Modifier.width(16.dp))
                    Text("$cantidadSeleccionada", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.width(16.dp))
                    OutlinedButton(onClick = { cantidadSeleccionada++ }) { Text("+") }
                }
            }
        )
    }
}

// BottomBar
@Composable
fun BottomBar(navController: NavController) {
    var selected by remember { mutableStateOf("menu") }
    Surface(tonalElevation = 5.dp, shadowElevation = 5.dp, color = Color(0xFFE8F5E9)) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceAround) {
            BottomBarButton("Inicio", selected == "inicio") { navController.navigate(Screen.Inicio.route) }
            BottomBarButton("Menú", selected == "menu") { navController.navigate(Screen.Menu.route) }
            BottomBarButton("Carrito", selected == "carrito") { navController.navigate(Screen.Carrito.route) }
        }
    }
}

@Composable
fun RowScope.BottomBarButton(label: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor by animateColorAsState(if (selected) Color(0xFFC8E6C9) else Color.Transparent)
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = bgColor, contentColor = Color(0xFF1B5E20)), modifier = Modifier.weight(1f)) {
        Text(label, fontSize = 12.sp)
    }
}