package com.example.hvapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.hvapp.Fonts.customFontFamily

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showError by remember { mutableStateOf(false) }

    val greenMossColor = Color(red = 85, green = 107, blue = 47)
    val titleStyle = remember {
        TextStyle(
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = customFontFamily,
            color = greenMossColor
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            style = titleStyle,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val userFound = usuarios.find { it.nombre == username && it.contraseña == password }
                if (userFound != null) {
                    navController.navigate("welcome_screen/${username}")
                } else {
                    showError = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 85, green = 107, blue = 47, alpha = 255)
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text("Iniciar", fontSize = 20.sp, color = Color.White)
        }

        if (showError) {
            LaunchedEffect(key1 = context) {
                Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("register_screen") },
            colors = ButtonDefaults.buttonColors(
                containerColor = greenMossColor
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text("Registrarse", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("recover_password_screen") },
            colors = ButtonDefaults.buttonColors(
                containerColor = greenMossColor
            ),
            modifier = Modifier
                .width(250.dp)
                .height(60.dp)
        ) {
            Text("Recuperar contraseña", fontSize = 20.sp, color = Color.White)
        }
    }
}
