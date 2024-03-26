package com.rpm.dogedexapp.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.dogdetail.ui.theme.DogedexAppTheme
import com.rpm.dogedexapp.model.Dog

@ExperimentalCoilApi
class DogDetailComposeActivity : ComponentActivity() {

    companion object {

        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"

    }

    private val dogDetailViewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if ( dog == null ) {
            Toast.makeText(
                this, R.string.error_showing_dog_not_found, Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        setContent {
            val status = dogDetailViewModel.status
            DogedexAppTheme {
                DogDetailScreen(dog = dog, status.value)
            }
        }
    }
}