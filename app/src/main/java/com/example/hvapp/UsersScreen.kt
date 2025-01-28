package com.example.hvapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun UsersScreen(navController: NavHostController) {
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("America/Santiago")) }
    val greenMossColor = Color(red = 85, green = 107, blue = 47)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Usuarios Registrados",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = greenMossColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (usuarios.isEmpty()) {
            Text("No hay usuarios registrados.")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD0F0C0), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .background(greenMossColor)
                        .border(1.dp, Color.Black)
                ) {
                    Text(
                        "Usuario",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                    Text(
                        "Fecha de Alta",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }

                usuarios.forEach { usuario ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .background(greenMossColor.copy(alpha = 0.1f))
                            .border(1.dp, greenMossColor)
                    ) {
                        Text(
                            usuario.nombre,
                            modifier = Modifier.padding(8.dp),
                            color = greenMossColor
                        )
                        Text(
                            usuario.fechaCreacion.atStartOfDay(ZoneId.of("America/Santiago")).toLocalDate().format(dateFormatter),
                            modifier = Modifier.padding(8.dp),
                            color = greenMossColor
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(
                containerColor = greenMossColor
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(text = "Regresar", fontSize = 20.sp, color = Color.White)
        }
    }
}
