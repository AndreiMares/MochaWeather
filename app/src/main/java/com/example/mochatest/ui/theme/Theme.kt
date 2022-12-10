package com.example.mochatest.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColors(
    primary = Primary,
    secondary = Error
)

@Composable
fun MochaTestTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorScheme,
        typography = Typography,
        content = content
    )
}



