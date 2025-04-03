package com.example.whiskeyreviewer.utils

object GlobalNavigator {
    private var handler: GlobalNavigationHandler? = null


    fun registerHandler(handler: GlobalNavigationHandler) {
        this.handler = handler
    }


    fun unregisterHandler() {
        handler = null
    }


    fun logout() {
        handler?.retryLogin()
    }


}

interface GlobalNavigationHandler {
    fun retryLogin()

}