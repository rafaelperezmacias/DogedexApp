package com.rpm.dogedexapp.api.dto

import com.rpm.dogedexapp.model.User

class UserDTOMapper {

    fun fromDTOToDomain(userDTO: UserDTO): User {
        return User(
            userDTO.id, userDTO.email, userDTO.authenticationToken
        )
    }

}