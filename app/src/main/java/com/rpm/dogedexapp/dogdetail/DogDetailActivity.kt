package com.rpm.dogedexapp.dogdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.rpm.dogedexapp.model.Dog
import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {

    companion object {

        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"

    }

    private val dogDetailViewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if ( dog == null ) {
            Toast.makeText(
                this, R.string.error_showing_dog_not_found, Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text = getString(R.string.dog_life_expectancy_format, dog.lifeExpectancy)
        binding.dog = dog
        binding.dogImage.load(dog.imageUrl)

        dogDetailViewModel.status.observe(this) {
            status ->
            when ( status ) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.message, Toast.LENGTH_LONG).show()
                }
                is ApiResponseStatus.Loading -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    binding.loadingWheel.visibility = View.GONE
                    finish()
                }
            }
        }

        binding.closeButton.setOnClickListener {
            if ( isRecognition ) {
                dogDetailViewModel.addDogToUser(dog.id)
            } else {
                finish()
            }
        }
    }

}