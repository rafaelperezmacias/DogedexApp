package com.rpm.dogedexapp.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.databinding.ActivityDogListBinding
import com.rpm.dogedexapp.dogdetail.DogDetailActivity

private const val GRID_SPAN_COUNT = 3

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.recyclerDog
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        val adapter = DogAdapter()
        adapter.setOnItemClickListener { dog ->
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DogDetailActivity.DOG_KEY, dog)
            startActivity(intent)
        }
        adapter.setOnLongItemClickListener { dog ->
            dogListViewModel.addDogToUser(dog.id)
        }
        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) {
            dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) {
            status ->
            when ( status ) {
                is ApiResponseStatus.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, status.message, Toast.LENGTH_LONG).show()
                }
                is ApiResponseStatus.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}