package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertogourmet.R
import com.example.huertogourmet.presentation.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, viewModel: MenuViewModel = viewModel()) {

    val platos = listOf(
        Triple(
            R.drawable.pastel_choclo,
            "Pastel de Choclo",
            "Pastel de maíz tradicional chileno relleno con pino de proteína de soya, champiñones, aceitunas y huevo duro." to 10000.0
        ),
        Triple(
            R.drawable.curry_garbanzos,
            "Curry de Garbanzos y Verduras",
            "Un guiso aromático con garbanzos, patatas, espinacas, leche de coco y verduras frescas. Servido con arroz basmati." to 9500.0
        ),
        Triple(
            R.drawable.lasagna_vegetal,
            "Lasaña Bolognesa Vegetal",
            "Pasta en capas con salsa boloñesa vegetal, bechamel y queso gratinado." to 10500.0
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Menú Huerto Gourmet 🌿", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F4)) // Fondo claro natural
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado del restaurante
            item {
                Image(
                    painter = painterResource(R.drawable.huertogourmet_logo),
                    contentDescription = "Logo Huerto Gourmet",
                    modifier = Modifier
                        .size(90.dp)
                        .padding(top = 8.dp)
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "HuertoGourmet Santiago",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF2E7D32)
                )

                Spacer(Modifier.height(10.dp))

                Image(
                    painter = painterResource(R.drawable.huerto_gourmet_santiago),
                    contentDescription = "Imagen del restaurante",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(14.dp))
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Somos un restaurante vegetariano y sostenible, comprometido con ofrecer comida saludable, local y deliciosa. En HuertoGourmet fusionamos sabor, frescura y conciencia para crear una experiencia gastronómica única en Santiago.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF4E4E4E)),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(Modifier.height(26.dp))
            }

            // Lista de platos
            items(platos.size) { index ->
                val (imagen, nombre, descripcionPrecio) = platos[index]
                val (descripcion, precio) = descripcionPrecio

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imagen),
                            contentDescription = nombre,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                nombre,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32)
                                )
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                descripcion,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF555555)
                                ),
                                lineHeight = 18.sp
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "$${"%,.0f".format(precio)}",
                                color = Color(0xFF388E3C),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(40.dp))
                Text(
                    text = "🍃 Gracias por visitar HuertoGourmet 🍃",
                    textAlign = TextAlign.Center,
                    color = Color(0xFF4E4E4E),
                    fontSize = 14.sp
                )
            }
        }
    }
}
