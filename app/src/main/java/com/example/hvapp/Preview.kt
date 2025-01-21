package com.example.hvapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.hvapp.ui.theme.HVappTheme

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    HVappTheme {
        LoginScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    HVappTheme {
        RegisterScreen(navController = rememberNavController())
    }
}
