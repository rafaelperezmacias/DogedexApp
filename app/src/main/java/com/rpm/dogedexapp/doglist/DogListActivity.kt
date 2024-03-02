package com.rpm.dogedexapp.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpm.dogedexapp.databinding.ActivityDogListBinding
import com.rpm.dogedexapp.dogdetail.DogDetailActivity

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.recyclerDog
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = DogAdapter()
        adapter.setOnItemClickListener { dog ->
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DogDetailActivity.DOG_KEY, dog)
            startActivity(intent)
        }
        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) {
            dogList ->
            adapter.submitList(dogList)
        }
    }

}