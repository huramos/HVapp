// ProfileScreen.kt
package com.example.hvapp

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hvapp.Fonts.customFontFamily
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun obtenerUsuarioActual(context: Context): Usuario? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val nombre = sharedPreferences.getString("username", null)
    val email = sharedPreferences.getString("email", null)
    val contraseña = sharedPreferences.getString("password", null)
    val fechaCreacionString = sharedPreferences.getString("fechaDeAlta", null)

    return if (nombre != null && email != null && contraseña != null && fechaCreacionString != null) {
        val fechaCreacion = LocalDate.parse(fechaCreacionString)
        Usuario(nombre, email, contraseña, fechaCreacion)
    } else null
}

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val usuarioActual = obtenerUsuarioActual(context)
    val greenMossColor = Color(red = 85, green = 107, blue = 47)
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun obfuscatePassword(password: String): String {
        return "*".repeat(password.length)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Perfil del Usuario",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = customFontFamily,
            color = greenMossColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        usuarioActual?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD0F0C0), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                // Fila para Usuario
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(greenMossColor.copy(alpha = 0.1f))
                        .border(1.dp, greenMossColor)
                ) {
                    Text("Usuario", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp), color = greenMossColor)
                    Text(it.nombre, modifier = Modifier.padding(8.dp), color = greenMossColor)
                }

                // Fila para Correo Electrónico
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(greenMossColor.copy(alpha = 0.1f))
                        .border(1.dp, greenMossColor)
                ) {
                    Text("Email", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp), color = greenMossColor)
                    Text(it.email, modifier = Modifier.padding(8.dp), color = greenMossColor)
                }

                // Fila para Contraseña
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(greenMossColor.copy(alpha = 0.1f))
                        .border(1.dp, greenMossColor)
                ) {
                    Text("Password", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp), color = greenMossColor)
                    Text(obfuscatePassword(it.contraseña), modifier = Modifier.padding(8.dp), color = greenMossColor)
                }

                // Fila para Fecha de Alta
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(greenMossColor.copy(alpha = 0.1f))
                        .border(1.dp, greenMossColor)
                ) {
                    Text("Fecha de Alta", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp), color = greenMossColor)
                    Text(it.fechaCreacion.format(dateFormatter), modifier = Modifier.padding(8.dp), color = greenMossColor)
                }
            }
        } ?: run {
            Text("No se encontró el usuario actual.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
            modifier = Modifier.width(250.dp).height(60.dp)
        ) {
            Text("Volver", fontSize = 20.sp, color = Color.White)
        }
    }
}
