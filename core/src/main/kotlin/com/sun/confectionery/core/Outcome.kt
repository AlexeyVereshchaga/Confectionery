package com.sun.confectionery.core

sealed interface Outcome<out T> {
    object Loading: Outcome<Nothing>
    data class Success<T>(val data: T): Outcome<T>
    data class Error(val throwable: Throwable): Outcome<Nothing>
}
