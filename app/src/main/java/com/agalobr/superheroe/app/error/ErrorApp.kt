package com.agalobr.superheroe.app.error

sealed class ErrorApp {
    object UnKnownError : ErrorApp()
    object DatabaseErrorApp: ErrorApp()
    object InternetErrorApp: ErrorApp()
}