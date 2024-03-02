package com.rpm.dogedexapp.api

import com.rpm.dogedexapp.BASE_URL
import com.rpm.dogedexapp.GET_ALL_DOGS_URL
import com.rpm.dogedexapp.api.responses.DogListApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()


interface ApiService {

    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

}

object DogsAPI {

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}