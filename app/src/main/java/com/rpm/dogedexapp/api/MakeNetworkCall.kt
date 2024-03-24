package com.rpm.dogedexapp.api

import com.rpm.dogedexapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(call: suspend() -> T): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch ( ex: UnknownHostException ) {
            ApiResponseStatus.Error(R.string.unknown_host_exception)
        } catch ( ex: HttpException ) {
            val errorMessage = if ( ex.code() == UNAUTHORIZED_ERROR_CODE ) {
                R.string.wrong_user_or_password
            } else {
                R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        } catch ( ex: Exception ) {
            val errorMessage = when ( ex.message ) {
                "sign_up_error" -> R.string.error_sign_up
                "sign_in_error" -> R.string.error_sign_in
                "user_already_exists" -> R.string.user_already_exists
                "error_adding_dog" -> R.string.error_adding_dog
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errorMessage)
        }
    }
}