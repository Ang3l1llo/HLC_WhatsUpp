package com.example.whatsapp_hle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColors(
    primary = Color(0xFF075E54),
    primaryVariant = Color(0xFF128C7E),
    secondary = Color(0xFF25D366)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF075E54),
    primaryVariant = Color(0xFF128C7E),
    secondary = Color(0xFF25D366)
)

@Composable
fun WhatsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}