package com.example.hvapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.fragment.app.FragmentActivity
import androidx.compose.ui.platform.LocalContext

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val activity = LocalContext.current as FragmentActivity
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
        composable("videos_screen") {
            VideosScreen(navController = navController, activity = activity)
        }
    }
}
