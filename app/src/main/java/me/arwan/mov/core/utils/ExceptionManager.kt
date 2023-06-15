package me.arwan.mov.core.utils

import me.arwan.mov.R
import timber.log.Timber

object ExceptionManager {

    const val NO_NETWORK_ERROR = "NO_NETWORK_ERROR"

    fun getMessageForException(throwable: Throwable, message: String): Int {
        return getMessageForException(Exception(throwable), message)
    }

    fun getMessageForException(exception: Exception, message: String): Int {
        Timber.e("${message}: $exception")
        return if (exception is NullPointerException) {
            R.string.error_time_out_server
        } else {
            when (exception.message) {
                NO_NETWORK_ERROR -> R.string.error_connection
                else -> R.string.error_unknown
            }
        }
    }
}