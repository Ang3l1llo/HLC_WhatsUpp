package com.example.whatsapp_hle.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp_hle.Screens.ChatDetailScreen
import com.example.whatsapp_hle.Screens.HomeScreen


//Pantalla principal que controla la navegación
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("chat_detail/{contactName}") { backStackEntry ->
            val contactName = backStackEntry.arguments?.getString("contactName") ?: "Desconocido"
            ChatDetailScreen(navController, contactName)
        }
    }
}