package com.example.mochatest.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mochatest.R

@Composable
fun EmptyAppBar(
    modifier: Modifier = Modifier,
    navIcon: Painter? = null,
    actionIcon: Painter? = null,
    onNavigationClick: (() -> Unit)? = null,
    onActionClick: (() -> Unit)? = null,
    bgColor: Color = colorResource(id = R.color.transparent),
    elevation: Dp = 0.dp,
) {
    AppBar(
        modifier = modifier,
        title = "",
        navIcon = navIcon,
        actionIcon = actionIcon,
        onNavigationClick = onNavigationClick,
        onActionClick = onActionClick,
        bgColor = bgColor,
        elevation = elevation
    )
}

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    navIcon: Painter? = null,
    actionIcon: Painter? = null,
    onNavigationClick: (() -> Unit)? = null,
    onActionClick: (() -> Unit)? = null,
    bgColor: Color = colorResource(id = R.color.transparent),
    elevation: Dp = 0.dp,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            if (onNavigationClick != null && navIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Image(
                        painter = navIcon,
                        contentDescription = null
                    )
                }
            }
        },
        backgroundColor = bgColor,
        elevation = elevation,
        actions = {
            if (onActionClick != null && actionIcon != null) {
                IconButton(onClick = onActionClick) {
                    Image(
                        painter = actionIcon,
                        contentDescription = null
                    )
                }
            }
        }
    )
}