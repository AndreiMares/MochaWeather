package com.example.mochatest.ui.alertdetalils

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.mochatest.utils.NavDestination
import com.example.mochatest.utils.NavDestinationConfig
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Serializable
class AlertDetailsDestination(val id: String, val imageUrl: String) : NavDestination {
    companion object : NavDestinationConfig<AlertDetailsDestination> {
        override val route = "alerts/{id}/{imageUrl}"
        override val args: List<NamedNavArgument> = listOf(
            navArgument(name = "id") {
                type = NavType.StringType
                nullable = false
            },
            navArgument(name = "imageUrl") {
                type = NavType.StringType
                nullable = false
            }
        )
        override val deepLinks: List<NavDeepLink> = listOf()
    }

    override fun action(): String = route
        .replace(oldValue = "{id}", newValue = id)
        .replace(
            oldValue = "{imageUrl}",
            newValue = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.name())
        )
}

