package com.example.mochatest.ui.alerts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mochatest.R
import com.example.mochatest.ui.components.AsyncImageWrapper
import com.example.mochatest.ui.components.DateFormat
import com.example.mochatest.ui.theme.Overlay1
import com.example.mochatest.ui.theme.Overlay2
import com.example.mochatest.ui.theme.Secondary
import com.example.mochatest.ui.theme.Tertiary
import com.example.mochatest.ui.utils.withGradient
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AlertsScreen(
    viewModel: AlertsViewModel = hiltViewModel(),
    onAlertClick: (String, String) -> Unit
) {
    val alertsStateFlow = viewModel.alertsFlow.collectAsState()

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
    }

    LazyColumn(
        modifier = Modifier
            .systemBarsPadding()
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
    ) {
        items(items = alertsStateFlow.value, key = { it.id }) { item ->
            AlertItemState(alert = item, onAlertClick = onAlertClick)
        }
    }

}

@Composable
private fun AlertItemState(alert: AlertState, onAlertClick: (String, String) -> Unit) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 0.dp, shape = RoundedCornerShape(16.dp), clip = true)
        .clickable { onAlertClick(alert.id, alert.imageDetailsUrl) }) {

        AsyncImageWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .withGradient(colors = listOf(Overlay1, Overlay2)),
            imageUrl = alert.imageUrl,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = alert.name,
                fontSize = 22.sp,
                color = Tertiary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.alert_sent_by, alert.sourceName),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Secondary
            )

            DateFormat(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                label = R.string.alert_started_at,
                date = alert.startDate
            )

            alert.endDate?.let {
                DateFormat(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.alert_ended_at,
                    date = it
                )
            }
        }
    }
}