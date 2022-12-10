package com.example.mochatest.utils

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.json.Json
import net.mready.json.FluidJson
import net.mready.json.JsonAdapter
import net.mready.json.JsonTransformer
import net.mready.json.adapters.KotlinxJsonAdapter
import net.mready.json.jsonObject
import kotlin.reflect.KType

private object BundleJsonTransformer : JsonTransformer {
    override fun transform(value: Any?, type: KType, adapter: JsonAdapter): FluidJson? {
        return when (value) {
            is Bundle -> jsonObject(adapter) {
                value.keySet().forEach { key ->
                    when (val bundleValue = value.get(key)) {
                        is String, is Number, is Boolean -> obj[key] = adapter.wrap(bundleValue)
                        else -> obj[key] = adapter.wrap(null)
                    }
                }
            }

            else -> null
        }
    }
}

object NavJsonAdapter : KotlinxJsonAdapter(
    jsonSerializer = Json(defaultSerializer) {
        encodeDefaults = false
    },
    transformers = setOf(BundleJsonTransformer)
)

interface NavDestination {
    fun action(): String
}

interface NavDestinationConfig<T : NavDestination> {
    val route: String
    val args: List<NamedNavArgument>
    val deepLinks: List<NavDeepLink>
}

inline fun <reified T : NavDestination> NavGraphBuilder.composable(
    key: NavDestinationConfig<T>,
    crossinline content: @Composable (NavBackStackEntry, T?) -> Unit
) {
    composable(key.route, key.args, key.deepLinks) { backStackEntry ->
        content(
            backStackEntry,
            NavJsonAdapter.fromJson(NavJsonAdapter.wrap(backStackEntry.arguments))
        )
    }
}