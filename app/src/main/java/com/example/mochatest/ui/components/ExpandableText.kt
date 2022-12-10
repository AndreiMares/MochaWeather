package com.example.mochatest.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mochatest.R

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    minimizedMaxLines: Int = 2
) {
    var expanded by remember { mutableStateOf(false) }
    var displayMore by remember { mutableStateOf(false) }

    Column(modifier = Modifier.wrapContentSize()) {
        Text(
            modifier = modifier.animateContentSize(),
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2,
            onTextLayout = {
                displayMore = it.lineCount > 2 || it.hasVisualOverflow
            }
        )

        if (displayMore) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp)
                    .clickable(onClick = { expanded = !expanded })
                    .align(Alignment.End),
                text = if (expanded) {
                    stringResource(id = R.string.alert_description_less)
                } else {
                    stringResource(id = R.string.alert_description_more)
                },
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}