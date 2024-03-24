package com.rpm.dogedexapp.api.responses

import com.squareup.moshi.Json

class DefaultResponse(
    @field:Json(name = "message") val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean
)