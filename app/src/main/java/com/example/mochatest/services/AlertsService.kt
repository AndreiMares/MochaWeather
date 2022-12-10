package com.example.mochatest.services

import com.example.mochatest.api.endpoints.AlertStatus
import com.example.mochatest.api.endpoints.AlertsApi
import com.example.mochatest.api.endpoints.MessageType
import com.example.mochatest.ui.models.Alert
import com.example.mochatest.ui.models.AlertDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertsService @Inject constructor(private val alertsApi: AlertsApi) {

    suspend fun getAlerts(status: AlertStatus, messageType: MessageType): List<Alert> {
        return alertsApi.getAlerts(status = status, messageType = messageType)
    }

    suspend fun getAlertDetails(id: String, imageUrl: String): AlertDetails {
        return alertsApi.getAlertDetails(id = id, imageUrl = imageUrl)
    }

    suspend fun getZoneNames(zoneIds: List<String>): List<String> {
        return alertsApi.getZoneNames(zoneIds = zoneIds)
    }
}