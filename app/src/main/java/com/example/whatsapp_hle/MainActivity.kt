package com.example.whatsapp_hle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.whatsapp_hle.ui.theme.WhatsAppTheme
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.whatsapp_hle.Navigation.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                MainScreen()
            }
        }
    }
}

//Para la barra superior de la pantalla
@Composable
fun TopBar() {
    var expanded by remember { mutableStateOf(false) } // Estado para mostrar/ocultar el menú desplegable
    var isSearching by remember { mutableStateOf(false) } // Estado para mostrar/ocultar busqueda
    var searchText by remember { mutableStateOf("") } // Estado para el texto de la busqueda

    //Para crear una barra superior
    TopAppBar(
        backgroundColor = colorResource(id = R.color.whatsapp_green_dark),
        title = {
            if (isSearching) {

                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White)
                )
            } else {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.White,
                    style = MaterialTheme.typography.h6
                )
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono de la cámara
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Cámara",
                        tint = Color.White
                    )
                }

                // Icono de la lupa
                IconButton(onClick = {
                    isSearching = !isSearching
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }

                // Icono de los tres puntos
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Más opciones",
                        tint = Color.White
                    )
                }

                // Menú desplegable
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Nuevo grupo")
                    }
                    DropdownMenuItem(onClick = { }) {
                        Text(text = "Nueva difusión")
                    }
                    DropdownMenuItem(onClick = { }) {
                        Text(text = "Dispositivos vinculados")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Mensajes destacados")
                    }
                    DropdownMenuItem(onClick = {  }) {
                        Text(text = "Ajustes")
                    }
                }
            }
        }
    )
}

//Para elegir entre chats, llamadas y estados
@Composable
fun TabView(paddingValues: PaddingValues, navController: NavHostController) {
    val tabTitles = listOf(
        stringResource(id = R.string.chats),
        stringResource(id = R.string.status),
        stringResource(id = R.string.calls)
    )
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(paddingValues)) {
        TabRow(
            selectedTabIndex = selectedTab,
            backgroundColor = colorResource(id = R.color.whatsapp_green)
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTab == index) Color.White else Color.LightGray
                        )
                    }
                )
            }
        }
        when (selectedTab) {
            0 -> ChatScreen(navController)
            1 -> StatusScreen()
            2 -> CallScreen()
        }
    }
}

//La pantalla de los chats
@Composable
fun ChatScreen(navController: NavHostController) {
    val contacts = listOf(
        Contacto(
            nombre = "Raul Tolai",
            imagen = R.drawable.rau_jabali,
            ultimoMensaje = "Ángel han puesto las notas y tienes un 0",
            fechaMensaje = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse("14/11/2024 15:00")!!
        ),
        Contacto(
            nombre = "Luis Faena",
            imagen = R.drawable.luis_caballo,
            ultimoMensaje = "Que más quiere CADI",
            fechaMensaje = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse("14/11/2024 14:45")!!
        ),
        Contacto(
            nombre = "JJ",
            imagen = R.drawable.josue_moto,
            ultimoMensaje = "Me he comprao un nuevo escape pa la moto",
            fechaMensaje = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse("14/11/2024 13:30")!!
        )
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Chats", style = MaterialTheme.typography.h5, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(contacts) { contact ->
                ChatItem(contact = contact) {
                    navController.navigate("chat_detail/${contact.nombre}")
                }
            }
        }
    }
}

//Las características de cada chat
@Composable
fun ChatItem(contact: Contacto, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(contact.fechaMensaje)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = contact.imagen),
            contentDescription = "Imagen de ${contact.nombre}",
            modifier = Modifier
                .size(50.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = contact.nombre,
                style = MaterialTheme.typography.body1,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = contact.ultimoMensaje,
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = formattedDate,
            style = MaterialTheme.typography.body2,
            color = Color.Gray
        )
    }
}

//La pantalla de estado
@Composable
fun StatusScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay estados para mostrar",
            textAlign = TextAlign.Center
        )
    }
}

//La pantalla de llamadas
@Composable
fun CallScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay llamadas recientes",
            textAlign = TextAlign.Center
        )
    }
}

