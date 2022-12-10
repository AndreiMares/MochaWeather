package com.example.mochatest.ui.models

import java.time.ZonedDateTime

data class Alert(
    val id: String,
    val name: String,
    val sourceName: String,
    val imageUrl: String,
    val imageDetailsUrl: String,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime?
)

data class AlertDetails(
    val imageUrl: String,
    val severity: String,
    val certainty: String,
    val urgency: String,
    val sourceName: String,
    val description: String,
    val instruction: String?,
    val zoneIds: List<String>,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime?
)