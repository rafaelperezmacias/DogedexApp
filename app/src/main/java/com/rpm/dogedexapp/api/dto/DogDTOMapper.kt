package com.rpm.dogedexapp.api.dto

import com.rpm.dogedexapp.model.Dog

class DogDTOMapper {

    fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id, dogDTO.index, dogDTO.name, dogDTO.type, dogDTO.heightFemale,
            dogDTO.heightMale, dogDTO.imageUrl, dogDTO.lifeExpectancy, dogDTO.temperament,
            dogDTO.weightMale, dogDTO.weightFemale
        )
    }

    fun fromDogDTOListToDogDomainList(dogDTOList: List<DogDTO>): List<Dog> {
        return dogDTOList.map { fromDogDTOToDogDomain(it) }
    }

}