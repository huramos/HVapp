package com.example.hvapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.hvapp.Fonts.customFontFamily

@Composable
fun WelcomeScreen(navController: NavHostController, username: String) {
    val greenMossColor = Color(red = 85, green = 107, blue = 47)
    val titleStyle = TextStyle(
        fontSize = 50.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = customFontFamily,
        color = greenMossColor
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = customFontFamily,
                        color = greenMossColor
                    )
                ) {
                    append("Bienvenido ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = customFontFamily,
                        color = greenMossColor
                    )
                ) {
                    append(username)
                }
                append("!")
            },
            style = titleStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.hvapp_logo),
            contentDescription = "Logo de HVapp",
            modifier = Modifier.height(200.dp).width(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("profile_screen") },
            colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
            modifier = Modifier.width(250.dp).height(60.dp)
        ) {
            Text("Ir al Perfil", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("users_screen") },
            colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
            modifier = Modifier.width(250.dp).height(60.dp)
        ) {
            Text("Usuarios Registrados", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("videos_screen") }, // Nuevo botón para Videos
            colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
            modifier = Modifier.width(250.dp).height(60.dp)
        ) {
            Text("Ver Videos", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("login_screen") },
            colors = ButtonDefaults.buttonColors(containerColor = greenMossColor),
            modifier = Modifier.width(250.dp).height(60.dp)
        ) {
            Text("Cerrar Sesión", fontSize = 18.sp, color = Color.White)
        }
    }
}
