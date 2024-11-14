package com.example.whatsapp_hle

import java.util.Date

data class Contacto (
    val nombre: String,
    val imagen: Int,  // Para el drawable de la imagen
    var ultimoMensaje: String,
    var fechaMensaje: Date
)