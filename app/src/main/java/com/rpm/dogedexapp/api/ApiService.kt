package com.rpm.dogedexapp.api

import com.rpm.dogedexapp.ADD_DOG_TO_USER_URL
import com.rpm.dogedexapp.BASE_URL
import com.rpm.dogedexapp.GET_ALL_DOGS_URL
import com.rpm.dogedexapp.GET_DOG_BY_ML_ID
import com.rpm.dogedexapp.GET_USER_DOGS_URL
import com.rpm.dogedexapp.SIGN_IN_URL
import com.rpm.dogedexapp.SIGN_UP_URL
import com.rpm.dogedexapp.api.dto.AddDogToUserDTO
import com.rpm.dogedexapp.api.dto.LoginDTO
import com.rpm.dogedexapp.api.dto.SignUpDTO
import com.rpm.dogedexapp.api.responses.AuthApiResponse
import com.rpm.dogedexapp.api.responses.DefaultResponse
import com.rpm.dogedexapp.api.responses.DogApiResponse
import com.rpm.dogedexapp.api.responses.DogListApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
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

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMLId(@Query("ml_id") mlId: String): DogApiResponse

}

object DogsAPI {

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}