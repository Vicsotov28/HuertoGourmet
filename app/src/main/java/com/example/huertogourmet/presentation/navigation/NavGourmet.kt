package com.example.huertogourmet.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.huertogourmet.presentation.ui.screens.InicioScreen
import com.example.huertogourmet.presentation.ui.screens.LoginScreen
import com.example.huertogourmet.presentation.ui.screens.MenuScreen
import com.example.huertogourmet.presentation.ui.screens.RegistroScreen
import com.example.huertogourmet.presentation.ui.screens.ComentariosScreen
import com.example.huertogourmet.presentation.ui.screens.CarritoScreen
import com.example.huertogourmet.presentation.viewmodel.CarritoViewModel

sealed class Screen(val route: String) {
    object Inicio : Screen("inicio_screen")
    object Login : Screen("login_screen")
    object Registro : Screen("registro_screen")
    object Menu : Screen("menu_screen")
    object Comentarios : Screen("comentarios_screen")
    object ComentariosDetail : Screen("comentarios_screen/{platoId}/{usuarioId}") {
        fun createRoute(platoId: Long, usuarioId: Long) = "comentarios_screen/$platoId/$usuarioId"
    }
    object Carrito : Screen("carrito_screen")
}

@Composable
fun NavGourmet(navController: NavHostController, carritoViewModel: CarritoViewModel) {
    NavHost(navController = navController, startDestination = Screen.Inicio.route) {
        composable(Screen.Inicio.route) { InicioScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Registro.route) { RegistroScreen(navController) }
        composable(Screen.Menu.route) { MenuScreen(navController = navController, carritoViewModel = carritoViewModel) }
        composable(Screen.Carrito.route) { CarritoScreen(navController = navController, carritoViewModel = carritoViewModel) }
        composable(Screen.Comentarios.route) { ComentariosScreen(navController = navController, platoId = 0L, usuarioId = 0L) }
        composable(route = Screen.ComentariosDetail.route,
            arguments = listOf(navArgument("platoId") { type = NavType.LongType }, navArgument("usuarioId") { type = NavType.LongType })
        ) { backStackEntry ->
            val platoId = backStackEntry.arguments?.getLong("platoId") ?: 0L
            val usuarioId = backStackEntry.arguments?.getLong("usuarioId") ?: 0L
            ComentariosScreen(navController = navController, platoId = platoId, usuarioId = usuarioId)
        }
    }
}