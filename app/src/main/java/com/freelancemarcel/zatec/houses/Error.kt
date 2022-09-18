package com.freelancemarcel.zatec.houses

data class Error(val type: ErrorType)

enum class ErrorType{
    LOAD_FAILURE_HOUSE, LOAD_FAILURE_HOUSES
}
