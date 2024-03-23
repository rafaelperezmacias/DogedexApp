package com.rpm.dogedexapp.auth

import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.api.DogsAPI
import com.rpm.dogedexapp.api.dto.LoginDTO
import com.rpm.dogedexapp.api.dto.SignUpDTO
import com.rpm.dogedexapp.api.dto.UserDTOMapper
import com.rpm.dogedexapp.api.makeNetworkCall
import com.rpm.dogedexapp.model.User
import java.lang.Exception

class AuthRepository {

    suspend fun signUp(
        email: String, password: String, passwordConfirmation: String
    ): ApiResponseStatus<User> {
        return makeNetworkCall {
            val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
            val signUpResponse = DogsAPI.retrofitService.signUp(signUpDTO)

            if ( !signUpResponse.isSuccess ) {
                throw Exception(signUpResponse.message)
            }

            val userDTO = signUpResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromDTOToDomain(userDTO)
        }
    }

    suspend fun login(
        email: String, password: String
    ): ApiResponseStatus<User> {
        return makeNetworkCall {
            val loginDTO = LoginDTO(email, password)
            val loginResponse = DogsAPI.retrofitService.login(loginDTO)

            if ( !loginResponse.isSuccess ) {
                throw Exception(loginResponse.message)
            }

            val userDTO = loginResponse.data.user
            val userDTOMapper = UserDTOMapper()
            userDTOMapper.fromDTOToDomain(userDTO)
        }
    }

}