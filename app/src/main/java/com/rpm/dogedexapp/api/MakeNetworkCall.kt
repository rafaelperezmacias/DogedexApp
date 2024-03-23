package com.rpm.dogedexapp.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(call: suspend() -> T): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch ( ex: UnknownHostException ) {
            ApiResponseStatus.Error("There is no internet connection")
        } catch ( ex: Exception) {
            ApiResponseStatus.Error("There was an error")
        }
    }
}