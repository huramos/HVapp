package com.example.hvapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login_screen",
        modifier = modifier
    ) {
        composable("login_screen") {
            LoginScreen(navController = navController)
        }
        composable("register_screen") {
            RegisterScreen(navController = navController)
        }
        composable("profile_screen") {
            ProfileScreen(navController = navController)
        }
        composable("recover_password_screen") {
            RecoverPasswordScreen(navController = navController)
        }
        composable("welcome_screen/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            WelcomeScreen(navController = navController, username = username)
        }
        composable("users_screen") {
            UsersScreen(navController = navController)
        }
    }
}
