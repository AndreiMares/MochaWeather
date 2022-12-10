package com.example.mochatest.api.endpoints

import com.example.mochatest.api.WeatherApiClient
import com.example.mochatest.ui.models.Alert
import com.example.mochatest.ui.models.AlertDetails
import net.mready.apiclient.get
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton


enum class AlertStatus(val value: String) {
    ACTUAL("actual")
}

enum class MessageType(val value: String) {
    Alert("alert")
}

@Singleton
class AlertsApi @Inject constructor(private val apiClient: WeatherApiClient) {

    suspend fun getAlerts(status: AlertStatus, messageType: MessageType): List<Alert> =
        apiClient.get(
            endpoint = "alerts/active?status=actual&message_type=alert",
            query = mapOf(
                "status" to status.value,
                "message_type" to messageType.value
            )
        ) {
            it["features"].array.mapIndexed { index, alert ->
                Alert(
                    id = alert["properties"]["id"].string,
                    name = alert["properties"]["event"].string,
                    sourceName = alert["properties"]["sender"].string,
                    imageUrl = "https://picsum.photos/id/${index}/1920/1080",
                    imageDetailsUrl = "https://picsum.photos/id/${index}/1000",
                    startDate = ZonedDateTime.parse(
                        alert["properties"]["sent"].string,
                        DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    ),
                    endDate = alert["properties"]["ends"].stringOrNull?.let {
                        ZonedDateTime.parse(
                            it,
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME
                        )
                    }
                )
            }
        }

    suspend fun getAlertDetails(id: String, imageUrl: String): AlertDetails =
        apiClient.get(endpoint = "alerts/${id}") {
            AlertDetails(
                imageUrl = imageUrl,
                severity = it["properties"]["severity"].string,
                certainty = it["properties"]["certainty"].string,
                urgency = it["properties"]["urgency"].string,
                sourceName = it["properties"]["senderName"].string,
                description = it["properties"]["description"].string,
                instruction = it["properties"]["instruction"].stringOrNull,
                zoneIds = it["properties"]["geocode"]["UGC"].arrayOrNull?.map { it.toString() }
                    ?: emptyList(),
                startDate = ZonedDateTime.parse(
                    it["properties"]["sent"].string,
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                ),
                endDate = it["properties"]["ends"].stringOrNull?.let {
                    ZonedDateTime.parse(
                        it,
                        DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    )
                }
            )
        }

    suspend fun getZoneNames(zoneIds: List<String>): List<String> =
        apiClient.get(
            endpoint = "zones",
            query = mapOf(
                "id" to zoneIds.joinToString(separator = ",")
                    .replace(oldValue = "\"", newValue = "")
            )
        ) {
            it["features"].array.map {
                it["properties"]["name"].string
            }
        }
}


