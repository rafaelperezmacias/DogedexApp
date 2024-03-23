package com.rpm.dogedexapp.api

import com.rpm.dogedexapp.BASE_URL
import com.rpm.dogedexapp.GET_ALL_DOGS_URL
import com.rpm.dogedexapp.SIGN_IN_URL
import com.rpm.dogedexapp.SIGN_UP_URL
import com.rpm.dogedexapp.api.dto.LoginDTO
import com.rpm.dogedexapp.api.dto.SignUpDTO
import com.rpm.dogedexapp.api.responses.DogListApiResponse
import com.rpm.dogedexapp.api.responses.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()


interface ApiService {

    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

}

object DogsAPI {

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}