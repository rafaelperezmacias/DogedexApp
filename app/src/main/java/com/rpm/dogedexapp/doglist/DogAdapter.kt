package com.rpm.dogedexapp.doglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rpm.dogedexapp.Dog
import com.rpm.dogedexapp.databinding.DogListItemBinding

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallBack) {

    companion object DiffCallBack : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private var onItemClickListener: ((Dog) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: (Dog) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder(DogListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dogViewHolder.bind(dog)
    }

    inner class DogViewHolder(private val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(dog: Dog) {
                binding.dogListItemLayout.setOnClickListener {
                    onItemClickListener?.invoke(dog)
                }
                binding.dogImage.load(dog.imageUrl)
            }

    }

}