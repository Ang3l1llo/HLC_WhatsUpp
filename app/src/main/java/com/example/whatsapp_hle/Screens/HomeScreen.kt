package com.example.whatsapp_hle.Screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.whatsapp_hle.TabView
import com.example.whatsapp_hle.TopBar

//Pantalla principal que organiza la barra superior, el sistema de pestañas y la navegación
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar() },
        content = { paddingValues ->
            TabView(paddingValues, navController)
        }
    )
}