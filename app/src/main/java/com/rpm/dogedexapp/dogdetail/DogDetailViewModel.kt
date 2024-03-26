package com.rpm.dogedexapp.dogdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.doglist.DogRepository
import com.rpm.dogedexapp.model.Dog
import kotlinx.coroutines.launch

class DogDetailViewModel : ViewModel() {

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    private val dogRepository = DogRepository()

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        status.value = apiResponseStatus
    }

}