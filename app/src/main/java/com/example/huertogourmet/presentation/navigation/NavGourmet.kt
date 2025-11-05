package com.example.huertogourmet.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.huertogourmet.presentation.ui.screens.InicioScreen
import com.example.huertogourmet.presentation.ui.screens.LoginScreen
import com.example.huertogourmet.presentation.ui.screens.MenuScreen
import com.example.huertogourmet.presentation.ui.screens.RegistroScreen

sealed class Screen(val route: String) {

    object Inicio : Screen("inicio_screen")
    object Login : Screen("login_screen")
    object Registro : Screen("registro_screen")
    object Menu : Screen("menu_screen")
}

@Composable
fun NavGourmet(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route
    ) {
        composable(route = Screen.Inicio.route) {
            InicioScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Registro.route) {
            RegistroScreen(navController)
        }
        composable(route = Screen.Menu.route) {
            MenuScreen(navController)
        }
    }
}
