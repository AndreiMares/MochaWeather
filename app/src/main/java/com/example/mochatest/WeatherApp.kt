package com.example.mochatest

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.mochatest.ui.alertdetalils.AlertDetailsDestination
import com.example.mochatest.ui.alertdetalils.AlertDetailsScreen
import com.example.mochatest.ui.alerts.AlertsDestination
import com.example.mochatest.ui.alerts.AlertsScreen
import com.example.mochatest.utils.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = Unit) {
        systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    }

    Scaffold {
        NavHost(
            modifier = Modifier.navigationBarsPadding().padding(it),
            navController = navController,
            startDestination = AlertsDestination.route
        ) {

            composable(AlertsDestination) { _, _ ->
                AlertsScreen(
                    onAlertClick = { id, imageUrl ->
                        navController.navigate(
                            AlertDetailsDestination(
                                id = id,
                                imageUrl = imageUrl
                            ).action()
                        )
                    }
                )
            }

            composable(AlertDetailsDestination) { _, currentDestination ->
                currentDestination?.let {
                    AlertDetailsScreen(
                        id = currentDestination.id,
                        imageUrl = currentDestination.imageUrl,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}