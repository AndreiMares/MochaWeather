package com.example.mochatest.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.withGradient(colors: List<Color>) =
    this.drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.verticalGradient(
                colors = colors
            )
        )
    }