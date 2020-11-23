package com.cmunaro.surfingspot.home.recycleradapter

import android.widget.ImageView.ScaleType
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cmunaro.surfingspot.R
import com.cmunaro.surfingspot.data.room.entity.City
import com.cmunaro.surfingspot.databinding.CityCardBinding

class CityViewHolder(private val binding: CityCardBinding) : RecyclerView.ViewHolder(binding.root) {
    var temperature: Int = 0
        set(value) {
            field = value
            binding.city?.temperature = value
            binding.invalidateAll()
        }

    fun bind(city: City) {
        binding.city = city
        updateImageURL(city.imageURL)
    }

    fun updateImageURL(imageURL: String?) {
        binding.backgroundImage.load(imageURL) {
            placeholder(R.drawable.loader_gif)
            listener(
                onSuccess = { _, _ ->
                    binding.backgroundImage.scaleType = ScaleType.CENTER_CROP
                    binding.invalidateAll()
                },
            )
        }
    }
}