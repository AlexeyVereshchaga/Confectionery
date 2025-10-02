package com.sun.confectionery

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sun.confectionery.auth.presentation.LoginScreen
import com.sun.confectionery.core.navigation.Destinations
import com.sun.confectionery.core.navigation.NavigationEvent
import com.sun.confectionery.core.navigation.NavigationManager
import com.sun.confectionery.features.products.presentation.screen.ProductsScreen
import org.koin.compose.koinInject


@Composable
fun App() {
    val navigationManager: NavigationManager = koinInject()

    val navController = rememberNavController()

    // Обработка навигационных событий
    LaunchedEffect(navigationManager) {
        navigationManager.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.NavigateToProducts -> {
                    navController.navigate(Destinations.Products.route) {
                        popUpTo(Destinations.Login.route) { inclusive = true }
                    }
                }
                is NavigationEvent.NavigateToProductDetail -> {
                    //navController.navigate(Destinations.ProductDetail.createRoute(event.productId))
                }
                NavigationEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Destinations.Login.route
        ) {
            composable(Destinations.Login.route) {
                LoginScreen()
            }
            composable(Destinations.Products.route) {
                ProductsScreen()
            }
//            composable(
//                route = Destinations.ProductDetail.route,
//                arguments = listOf(navArgument("productId") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val productId = backStackEntry.arguments?.getString("productId") ?: ""
//                ProductDetailScreen(productId = productId)
//            }
        }
    }
}