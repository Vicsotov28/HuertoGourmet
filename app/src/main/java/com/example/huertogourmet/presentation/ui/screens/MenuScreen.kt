package com.example.huertogourmet.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
            CenterAlignedTopAppBar(title = { Text("Menú Huerto Gourmet 🌿") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // 🌿 Logo pequeño arriba
                Image(
                    painter = painterResource(R.drawable.huertogourmet_logo),
                    contentDescription = "Logo Huerto Gourmet",
                    modifier = Modifier
                        .size(90.dp)
                )

                Spacer(Modifier.height(8.dp))

                // 🏙️ Título y ubicación
                Text(
                    text = "HuertoGourmet Santiago",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                // 🌄 Imagen panorámica del restaurante
                Image(
                    painter = painterResource(R.drawable.huerto_gourmet_santiago),
                    contentDescription = "Imagen del restaurante Huerto Gourmet",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(Modifier.height(12.dp))

                // 📝 Descripción del restaurante
                Text(
                    text = "Somos un restaurante vegetariano y sostenible, comprometido con ofrecer comida saludable, local y deliciosa. En HuertoGourmet fusionamos sabor, frescura y conciencia ecológica para crear una experiencia gastronómica única en Santiago.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))
            }

            // 🍽️ Listado de platos
            items(platos.size) { index ->
                val (imagen, nombre, descripcionPrecio) = platos[index]
                val (descripcion, precio) = descripcionPrecio

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imagen),
                            contentDescription = nombre,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(nombre, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                            Text(descripcion, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "$${"%,.0f".format(precio)}",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}
