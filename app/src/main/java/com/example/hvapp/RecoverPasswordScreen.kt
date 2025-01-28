package com.example.hvapp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RecoverPasswordScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var recoveredPassword by remember { mutableStateOf<String?>(null) }
    var emailExists by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Recuperar Contraseña", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val result = recoverPassword(context, email)
                recoveredPassword = result
                emailExists = result != null
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 85, green = 107, blue = 47, alpha = 255)
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(text = "Enviar", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (recoveredPassword != null) {
            Text(
                text = "Contraseña recuperada: $recoveredPassword",
                fontSize = 18.sp,
                color = Color.Blue
            )
        } else if (!emailExists) {
            Text(
                text = "Correo electrónico no encontrado",
                fontSize = 18.sp,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("login_screen") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 85, green = 107, blue = 47, alpha = 255)
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text(text = "Volver", fontSize = 20.sp, color = Color.White)
        }
    }
}

fun recoverPassword(context: Context, email: String): String? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val storedEmail = sharedPreferences.getString("email", null)
    val storedPassword = sharedPreferences.getString("password", null)

    return if (storedEmail == email) {
        storedPassword
    } else {
        null
    }
}
