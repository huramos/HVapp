package com.example.hvapp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import com.example.hvapp.Fonts.customFontFamily

@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var emptyFieldsError by remember { mutableStateOf(false) }
    var registroResultado by remember { mutableStateOf("") }
    var isRegistered by remember { mutableStateOf(false) }
    var registerEnabled by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val greenMossColor = Color(red = 85, green = 107, blue = 47)
    val titleStyle = TextStyle(
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = customFontFamily,
        color = greenMossColor
    )

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
                Text(
                    text = "Registrarse",
                    style = titleStyle,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        emptyFieldsError = username.isBlank() || email.isBlank() || password.isBlank()
                        registerEnabled = !emptyFieldsError && !emailError && !passwordError
                    },
                    label = { Text("Usuario") },
                    isError = emptyFieldsError && username.isBlank()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !isValidEmail(it) || !isEmailUnique(it)
                        emptyFieldsError = username.isBlank() || email.isBlank() || password.isBlank()
                        registerEnabled = !emptyFieldsError && !emailError && !passwordError
                    },
                    label = { Text("Correo Electrónico") },
                    isError = emailError || (emptyFieldsError && email.isBlank())
                )

                if (emailError) {
                    Text(
                        text = if (!isValidEmail(email))
                            "Correo electrónico no válido"
                        else
                            "Correo electrónico ya registrado",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = !isValidPassword(it)
                        emptyFieldsError = username.isBlank() || email.isBlank() || password.isBlank()
                        registerEnabled = !emptyFieldsError && !emailError && !passwordError
                    },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError || (emptyFieldsError && password.isBlank())
                )

                if (passwordError) {
                    Text(
                        text = "La contraseña debe tener al menos 8 caracteres, incluyendo una mayúscula, una minúscula, un número y un símbolo.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (emptyFieldsError) {
                    Text(
                        text = "Todos los campos son obligatorios",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (!emailError && !passwordError && !emptyFieldsError) {
                            val result = registrarUsuario(username, email, password)
                            if (result.startsWith("Usuario")) {
                                saveUser(context, username, email, password, obtenerFechaActual(ZoneId.of("America/Santiago")).toString())
                                isRegistered = true
                                coroutineScope.launch {
                                    delay(500) // Espera 2 segundos antes de navegar
                                    navController.navigate("login_screen")
                                }
                            }
                            registroResultado = result
                        }
                    },
                    enabled = registerEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = greenMossColor
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
                    colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
                    modifier = Modifier
                        .width(250.dp)
                        .height(60.dp)
                ) {
                    Text(text = "Volver", fontSize = 20.sp, color = Color.White)
                }

                if (isRegistered) {
                    LaunchedEffect(Unit) {
                        snackbarHostState.showSnackbar("Registro completo", duration = SnackbarDuration.Short)
                        delay(500)
                        navController.navigate("login_screen")
                    }
                }
            }
        }
    )
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#\$%^&+=]).{8,}$")
    return password.matches(passwordPattern)
}

fun isEmailUnique(email: String): Boolean {
    return !usuarios.any { it.email == email }
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
