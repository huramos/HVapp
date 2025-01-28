package com.example.hvapp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var registroResultado by remember { mutableStateOf("") }

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Registrarse", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !isValidEmail(it)
                    },
                    label = { Text("Correo Electrónico") },
                    isError = emailError
                )

                if (emailError) {
                    Text(
                        text = "Correo electrónico no válido",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                val fechaDeAlta = obtenerFechaActual(ZoneId.of("America/Santiago"))

                Button(
                    onClick = {
                        if (!emailError) {
                            val result = registrarUsuario(username, email, password)
                            if (result.startsWith("Usuario")) {
                                saveUser(context, username, email, password, fechaDeAlta.toString())
                            }
                            registroResultado = result
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(registroResultado, duration = SnackbarDuration.Short)
                            }
                            if (usuarios.size < 6) {
                                navController.navigate("login_screen")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 85, green = 107, blue = 47, alpha = 255)
                    ),
                    modifier = Modifier
                        .width(250.dp)
                        .height(60.dp)
                ) {
                    Text(text = "Registrar", fontSize = 20.sp, color = Color.White)
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
    )
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun obtenerFechaActual(zone: ZoneId = ZoneId.of("America/Santiago")): LocalDate {
    return LocalDate.now(zone)
}

fun saveUser(context: Context, username: String, email: String, password: String, fechaDeAlta: String) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("username", username)
    editor.putString("email", email)
    editor.putString("password", password)
    editor.putString("fechaDeAlta", fechaDeAlta)
    editor.apply()
}
