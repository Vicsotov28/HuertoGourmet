package com.example.huertogourmet.presentation.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.huertogourmet.presentation.viewmodel.ComentariosViewModel
import com.example.huertogourmet.R
import java.io.File
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import com.example.huertogourmet.presentation.navigation.Screen
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComentariosScreen(
    navController: NavController,
    platoId: Long,
    usuarioId: Long,
    viewModel: ComentariosViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var texto by remember { mutableStateOf(TextFieldValue("")) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFile by remember { mutableStateOf<File?>(null) }

    val isUploading by viewModel.isUploading.collectAsState()
    val lastResult by viewModel.lastResult.collectAsState()

    val pickLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedUri = uri
        selectedFile = uri?.let { uriToFile(context, it) }
    }

    // Cuando el comentario se crea OK, volvemos a Inicio
    LaunchedEffect(lastResult) {
        if (lastResult != null && lastResult == "Comentario creado correctamente") {
            // pequeña pausa para que el usuario vea el mensaje (opcional)
            navController.navigate(Screen.Inicio.route) {
                popUpTo(Screen.Inicio.route) { inclusive = true }
            }
        }
    }

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Comentarios") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            // Logo arriba
            Image(painter = painterResource(R.drawable.huertogourmet_logo), contentDescription = "Logo", modifier = Modifier.size(80.dp).align(Alignment.CenterHorizontally))

            Text("Comenta sobre el plato", fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

            OutlinedTextField(value = texto, onValueChange = { texto = it }, label = { Text("Tu comentario") }, modifier = Modifier.fillMaxWidth(), singleLine = false, maxLines = 4)

            // Preview imagen seleccionada
            selectedUri?.let { uri ->
                AsyncImage(model = uri, contentDescription = "Imagen seleccionada", modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(8.dp)))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(onClick = { pickLauncher.launch("image/*") }, modifier = Modifier.weight(1f)) { Text("Seleccionar foto") }
                OutlinedButton(onClick = { selectedUri = null; selectedFile = null }, modifier = Modifier.weight(1f)) { Text("Quitar foto") }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.crearComentarioConImagen(
                            platoId = platoId,
                            usuarioId = usuarioId,
                            texto = texto.text,
                            file = selectedFile
                        )
                    }
                },
                enabled = !isUploading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isUploading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Enviando...")
                } else {
                    Text("Enviar comentario")
                }
            }

            lastResult?.let { Text(it, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp)) }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
        }
    }
}

/** Helper: copiar Uri a File en cache (si proviene de content://) */
fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val input = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
        input.use { inputStream ->
            FileOutputStream(tempFile).use { output ->
                inputStream.copyTo(output)
            }
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}