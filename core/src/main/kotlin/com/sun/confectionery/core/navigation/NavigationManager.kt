package com.sun.confectionery.core.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigationManager {
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    suspend fun navigate(event: NavigationEvent) {
        _navigationEvents.send(event)
    }
}