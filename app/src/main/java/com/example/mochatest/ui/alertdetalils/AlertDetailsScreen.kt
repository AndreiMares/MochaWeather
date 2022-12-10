package com.example.mochatest.ui.alertdetalils

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mochatest.R
import com.example.mochatest.ui.components.AsyncImageWrapper
import com.example.mochatest.ui.components.DateFormat
import com.example.mochatest.ui.components.ExpandableText
import com.example.mochatest.ui.utils.EmptyAppBar
import com.example.mochatest.ui.utils.withGradient
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.roundToInt

@Composable
fun AlertDetailsScreen(
    id: String,
    imageUrl: String,
    viewModel: AlertDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {

    val systemUiController = rememberSystemUiController()
    val alertsStateFlow = viewModel.alertsFlow.collectAsState()
    val zoneNamesFlow = viewModel.zoneNamesFlow.collectAsState()

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = false)
    }

    LaunchedEffect(id) {
        viewModel.loadAlertDetails(id = id, imageUrl = imageUrl)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        alertsStateFlow.value?.let {
            AlertDetailsView(
                modifier = Modifier.fillMaxSize(),
                alertDetails = it,
                zoneNames = zoneNamesFlow.value
            )
        }

        EmptyAppBar(
            modifier = Modifier.systemBarsPadding(),
            navIcon = painterResource(id = R.drawable.ic_nav_up_secondary),
            onNavigationClick = onBackClick
        )
    }
}

@Composable
private fun AlertDetailsView(
    modifier: Modifier,
    alertDetails: AlertDetailsState,
    zoneNames: List<String>
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    LazyColumn(modifier = modifier) {
        item {
            AsyncImageWrapper(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .aspectRatio(1f / 1f)
                    .withGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.White
                        )
                    )
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            },
                            onDragEnd = {
                                offsetX = 0f
                                offsetY = 0f
                            }
                        )
                    },
                imageUrl = alertDetails.imageUrl,
                contentDescription = null
            )
        }


        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                DateFormat(
                    modifier = Modifier
                        .align(Alignment.End),
                    label = R.string.alert_started_at,
                    date = alertDetails.startDate,
                    labelStyle = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    dateStyle = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                alertDetails.endDate?.let {
                    DateFormat(
                        modifier = Modifier
                            .align(Alignment.End),
                        label = R.string.alert_ended_at,
                        date = it,
                        labelStyle = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        dateStyle = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }


                Text(text = alertDetails.sourceName, fontSize = 24.sp, fontWeight = FontWeight.Bold)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    AlertTag(alertDetails.certainty)
                    AlertTag(alertDetails.severity)
                    AlertTag(alertDetails.urgency)
                }

                Row {
                    Text(
                        text = zoneNames.joinToString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                ExpandableText(
                    modifier = Modifier.padding(top = 20.dp),
                    text = alertDetails.description,
                )

                alertDetails.instruction?.let {
                    ExpandableText(
                        modifier = Modifier.padding(top = 12.dp),
                        text = it,
                    )
                }
            }
        }
    }
}

@Composable
private fun AlertTag(name: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.baseline_tag_24),
            contentDescription = null
        )
        Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Normal)
    }

}