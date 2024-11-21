package com.example.whatsapp_hle.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.whatsapp_hle.Message
import com.example.whatsapp_hle.R

@Composable
fun ChatDetailScreen(navController: NavHostController, contactName: String) {
    // Lista de mensajes iniciales
    val initialMessages = listOf(
        Message("Iyoooo pasame lo de fol", isUser = false),
        Message("Venga pixa, te lo mando por correo", isUser = true),
        Message("Gracias coone, que no vea que pesaita la lorena 😊", isUser = false),
        Message("hahaha ya ves, pero más le pesa el culo", isUser = true),
        Message("jajajaja la verdad que tiene un empujón", isUser = false)

        
    )

    // Estado para manejar los mensajes y el texto actual
    val messages = remember { mutableStateListOf<Message>(*initialMessages.toTypedArray()) }
    var currentMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = contactName) },
                backgroundColor = colorResource(id = R.color.whatsapp_green_dark),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Lista de mensajes
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    reverseLayout = true // Mensajes más recientes abajo
                ) {
                    items(messages) { message ->
                        ChatBubble(message)
                    }
                }

                // Campo de entrada y botón de enviar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = currentMessage,
                        onValueChange = { currentMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text(text = "Escribe un mensaje...") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray.copy(alpha = 0.1f)
                        )
                    )
                    IconButton(
                        onClick = {
                            if (currentMessage.isNotBlank()) {
                                // Añade el mensaje del usuario
                                messages.add(0, Message(currentMessage, isUser = true))
                                currentMessage = "" // Limpia el campo
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Enviar",
                            tint = colorResource(id = R.color.whatsapp_green_dark)
                        )
                    }
                }
            }
        }
    )
}



@Composable
fun ChatBubble(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.content,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            modifier = Modifier
                .background(
                    color = if (message.isUser) colorResource(id = R.color.whatsapp_green_dark)
                    else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        )
    }
}