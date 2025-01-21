package com.example.hvapp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "Desconocido") ?: "Desconocido"
    val email = sharedPreferences.getString("email", "Desconocido") ?: "Desconocido"
    val password = sharedPreferences.getString("password", "****") ?: "****"

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
        Text(text = "Perfil del Usuario", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nombre de usuario: $username", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Correo electrónico: $email", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Contraseña: ${obfuscatePassword(password)}", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("login_screen")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 85, green = 107, blue = 47, alpha = 255)
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(text = "Cerrar sesión", fontSize = 20.sp, color = Color.White)
        }
    }
}
