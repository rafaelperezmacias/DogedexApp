package com.rpm.dogedexapp.doglist

import com.rpm.dogedexapp.Dog
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.api.DogsAPI.retrofitService
import com.rpm.dogedexapp.api.dto.DogDTOMapper
import com.rpm.dogedexapp.api.makeNetworkCall

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> {
        return makeNetworkCall {
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }

}