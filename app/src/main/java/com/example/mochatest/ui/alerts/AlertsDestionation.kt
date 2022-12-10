package com.example.mochatest.ui.alerts

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.example.mochatest.utils.NavDestination
import com.example.mochatest.utils.NavDestinationConfig
import kotlinx.serialization.Serializable


@Serializable
class AlertsDestination : NavDestination {
    companion object : NavDestinationConfig<AlertsDestination> {
        override val route = "alerts"
        override val args: List<NamedNavArgument> = listOf()
        override val deepLinks: List<NavDeepLink> = listOf()
    }

    override fun action(): String = route
}