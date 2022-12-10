package com.example.mochatest.ui.alerts

import com.example.mochatest.api.endpoints.AlertStatus
import com.example.mochatest.api.endpoints.MessageType
import com.example.mochatest.services.AlertsService
import com.example.mochatest.ui.base.BaseViewModel
import com.example.mochatest.ui.models.Alert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

val alertDateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMM d, h:mm a")

@HiltViewModel
class AlertsViewModel @Inject constructor(private val alertsService: AlertsService) :
    BaseViewModel() {

    private val _alertsFlow = MutableStateFlow<List<AlertState>>(emptyList())
    val alertsFlow = _alertsFlow.asStateFlow()

    init {
        launch {
            runCatching {
                alertsService.getAlerts(
                    status = AlertStatus.ACTUAL, messageType = MessageType.Alert
                )
            }.onSuccess {
                _alertsFlow.value = it.map { AlertState(it) }
            }.onFailure {
                //todo handle error
            }
        }
    }
}

data class AlertState(private val alert: Alert) {
    val id = alert.id
    val name = alert.name
    val sourceName = alert.sourceName
    val imageUrl = alert.imageUrl
    val imageDetailsUrl = alert.imageDetailsUrl
    val startDate = alert.startDate.format(alertDateFormatter)
    val endDate = alert.endDate?.format(alertDateFormatter)
}