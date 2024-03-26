package com.rpm.dogedexapp.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.model.Dog
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
                if ( dog.inCollection ) {
                    binding.dogImage.visibility = View.VISIBLE
                    binding.dogListItemLayout.background = ContextCompat.getDrawable(
                        binding.dogImage.context,
                        R.drawable.dog_list_item_background
                    )
                    binding.dogIndex.visibility = View.GONE
                    binding.dogListItemLayout.setOnClickListener {
                        onItemClickListener?.invoke(dog)
                    }
                    binding.dogImage.load(dog.imageUrl)
                } else {
                    binding.dogImage.visibility = View.GONE
                    binding.dogListItemLayout.background = ContextCompat.getDrawable(
                        binding.dogImage.context,
                        R.drawable.dog_list_item_null_background
                    )
                    binding.dogListItemLayout.setOnClickListener(null)
                    binding.dogIndex.visibility = View.VISIBLE
                    binding.dogIndex.text = dog.index.toString()
                }

            }

    }

}