package com.example.huertogourmet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.huertogourmet.presentation.navigation.NavGourmet
import com.example.huertogourmet.ui.theme.HuertoGourmetTheme
import com.example.huertogourmet.presentation.viewmodel.CarritoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuertoGourmetTheme {
                val navController = rememberNavController()


                val carritoViewModel: CarritoViewModel = viewModel()

                Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier) {
                    NavGourmet(navController = navController, carritoViewModel = carritoViewModel)
                }
            }
        }
    }
}
