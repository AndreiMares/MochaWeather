package com.example.mochatest.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mochatest.ui.theme.Tertiary


@Composable
fun DateFormat(
    modifier: Modifier = Modifier,
    date: String,
    @StringRes label: Int,
    labelStyle: TextStyle = TextStyle(
        color = Tertiary,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    dateStyle: TextStyle = TextStyle(
        color = Tertiary,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = label),
            style = labelStyle,
        )


        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = date,
            style = dateStyle,
        )
    }
}