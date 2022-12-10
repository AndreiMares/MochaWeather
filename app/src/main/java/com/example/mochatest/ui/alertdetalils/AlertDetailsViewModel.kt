package com.example.mochatest.ui.alertdetalils

import com.example.mochatest.services.AlertsService
import com.example.mochatest.ui.alerts.alertDateFormatter
import com.example.mochatest.ui.base.BaseViewModel
import com.example.mochatest.ui.models.AlertDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertDetailsViewModel @Inject constructor(
    private val alertsService: AlertsService
) : BaseViewModel() {

    private val _alertDetailsFlow = MutableStateFlow<AlertDetailsState?>(null)
    val alertsFlow = _alertDetailsFlow.asStateFlow()

    private val _zoneNamesFlow = MutableStateFlow<List<String>>(emptyList())
    val zoneNamesFlow = _zoneNamesFlow.asStateFlow()

    fun loadAlertDetails(id: String, imageUrl: String) {
        launch {
            runCatching {
                alertsService.getAlertDetails(id = id, imageUrl = imageUrl)
            }.onSuccess {
                _alertDetailsFlow.value = AlertDetailsState(it)
                getZoneNames(it.zoneIds)
            }.onFailure {
                //todo handle error
            }
        }
    }

    private suspend fun getZoneNames(zoneIds: List<String>) {
        runCatching {
            alertsService.getZoneNames(zoneIds = zoneIds)
        }.onSuccess {
            _zoneNamesFlow.value = it
        }.onFailure {
            //todo handle error
        }
    }
}

data class AlertDetailsState(private val alertDetails: AlertDetails) {
    val imageUrl = alertDetails.imageUrl
    val severity = alertDetails.severity
    val certainty = alertDetails.certainty
    val urgency = alertDetails.urgency
    val sourceName = alertDetails.sourceName
    val description = alertDetails.description
    val instruction = alertDetails.instruction
    val startDate = alertDetails.startDate.format(alertDateFormatter)
    val endDate = alertDetails.endDate?.format(alertDateFormatter)
}