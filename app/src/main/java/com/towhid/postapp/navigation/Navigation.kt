package com.towhid.postapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towhid.postapp.presentation.auth.AuthViewModel
import com.towhid.postapp.presentation.auth.LoginScreen
import com.towhid.postapp.presentation.auth.RegisterScreen
import com.towhid.postapp.presentation.favourite.FavouritesScreen
import com.towhid.postapp.presentation.favourite.FavouritesViewModel
import com.towhid.postapp.presentation.post.PostsScreen
import com.towhid.postapp.presentation.post.PostsViewModel
import com.towhid.postapp.presentation.splash.SplashScreen
import com.towhid.postapp.utils.Prefs


object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTRATION = "register"
    const val FAVOURITES = "favs"
    const val POSTS = "posts"
}
@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val prefs = remember { Prefs(context) }
    val navController = rememberNavController()

    val nextDestination = if (prefs.isLoggedIn()) Routes.POSTS else Routes.LOGIN

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH){
            SplashScreen(
                navigate = {
                    navController.navigate(nextDestination)
                }
            )
        }
        composable(Routes.LOGIN) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel,
                onLogin = {
                    prefs.setLoggedIn(true)
                    navController.navigate(Routes.POSTS) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                goRegistration = { navController.navigate(Routes.REGISTRATION) }
            )
        }
        composable(Routes.REGISTRATION) {
            val viewModel: AuthViewModel = hiltViewModel()
            RegisterScreen(
                viewModel,
                onRegistered = { navController.popBackStack() },
                onLogin = {navController.popBackStack()}
            )
        }
        composable(Routes.POSTS) {
            val viewModel: PostsViewModel = hiltViewModel()
            PostsScreen(
                viewModel,
                openFavs = { navController.navigate(Routes.FAVOURITES) },
                onLogout = {
                    prefs.setLoggedIn(false)
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.FAVOURITES) {
            val viewModel: FavouritesViewModel = hiltViewModel()
            FavouritesScreen(viewModel)
        }
    }
}

