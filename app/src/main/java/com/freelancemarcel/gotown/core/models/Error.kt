package com.freelancemarcel.gotown.core.models

data class Error(val type: ErrorType):Throwable()

enum class ErrorType{
    LOAD_FAILURE_HOUSE, LOAD_FAILURE_HOUSES, LOAD_FAILURE_NETWORK
}
