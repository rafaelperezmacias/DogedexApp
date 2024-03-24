package com.rpm.dogedexapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.rpm.dogedexapp.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {

    companion object {

        const val PHOTO_URI_KEY = "photo_uri"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_KEY)
        val uri = Uri.parse(photoUri)
        val path = uri.path

        if ( path == null ) {
            Toast.makeText(
                this, "Error showing image no photo uri", Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        binding.wholeImage.load(File(path))
    }

}