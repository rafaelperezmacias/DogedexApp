package com.rpm.dogedexapp.main

import androidx.camera.core.ImageProxy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.doglist.DogRepository
import com.rpm.dogedexapp.machinelearning.Classifier
import com.rpm.dogedexapp.machinelearning.ClassifierRepository
import com.rpm.dogedexapp.machinelearning.DogRecognition
import com.rpm.dogedexapp.model.Dog
import kotlinx.coroutines.launch
import java.nio.MappedByteBuffer

class MainViewModel : ViewModel() {

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog>
        get() = _dog

    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status: LiveData<ApiResponseStatus<Dog>>
        get() = _status

    private val _dogRecognition = MutableLiveData<DogRecognition>()
    val dogRecognition: LiveData<DogRecognition>
        get() = _dogRecognition

    private val dogRepository = DogRepository()
    private lateinit var classifierRepository: ClassifierRepository

    fun setUpClassifier(tfLiteModel: MappedByteBuffer, labels: List<String>) {
        val classifier = Classifier(tfLiteModel, labels)
        classifierRepository = ClassifierRepository(classifier)
    }

    fun recognizeImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            _dogRecognition.value = classifierRepository.recognizeImage(imageProxy)
            imageProxy.close()
        }
    }

    fun getDogByMLId(mlDogId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            handleResponseStatus(dogRepository.getDogByMLId(mlDogId))
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<Dog>) {
        if ( apiResponseStatus is ApiResponseStatus.Success ) {
            _dog.value = apiResponseStatus.data
        }

        _status.value = apiResponseStatus
    }

}