package com.rpm.dogedexapp.doglist

import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.model.Dog
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.api.DogsAPI.retrofitService
import com.rpm.dogedexapp.api.dto.AddDogToUserDTO
import com.rpm.dogedexapp.api.dto.DogDTOMapper
import com.rpm.dogedexapp.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.lang.Exception

class DogRepository {

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if ( allDogsListResponse is ApiResponseStatus.Error ) {
                allDogsListResponse
            } else if ( userDogsListResponse is ApiResponseStatus.Error ) {
                userDogsListResponse
            } else if ( allDogsListResponse is ApiResponseStatus.Success &&
                userDogsListResponse is ApiResponseStatus.Success) {
                ApiResponseStatus.Success(
                    getCollectionList(allDogsListResponse.data, userDogsListResponse.data)
                )
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog> {
        return allDogList.map { dog ->
            if ( userDogList.contains(dog) ) {
                dog
            } else {
                Dog(
                    dog.id, dog.index, "", "", "", "", "",
                    "", "", "", "",
                    inCollection = false
                )
            }
        }.sorted()
    }

    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getUserDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
        return makeNetworkCall {
            val addDogToUserDTO = AddDogToUserDTO(dogId)
            val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

            if ( !defaultResponse.isSuccess ) {
                throw Exception(defaultResponse.message)
            }
        }
    }

    suspend fun getDogByMLId(mlDogId: String): ApiResponseStatus<Dog> {
        return makeNetworkCall {
            val response = retrofitService.getDogByMLId(mlDogId)

            if ( !response.isSuccess ) {
                throw Exception(response.message)
            }

            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOToDogDomain(response.data.dog)
        }
    }

}