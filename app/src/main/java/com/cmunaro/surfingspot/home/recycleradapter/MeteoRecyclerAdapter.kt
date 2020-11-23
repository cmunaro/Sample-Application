package com.cmunaro.surfingspot.home.recycleradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmunaro.surfingspot.R
import com.cmunaro.surfingspot.data.room.entity.City
import com.cmunaro.surfingspot.databinding.CityCardBinding

class MeteoRecyclerAdapter : RecyclerView.Adapter<CityViewHolder>() {
    private var items = mutableListOf<City>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CityCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.city_card, parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = items[position]
        holder.bind(city)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        @Suppress("UNCHECKED_CAST")
        val combinedChange = createCombinedPayload(payloads as List<Change<City>>)
        val oldData = combinedChange.oldData
        val newData = combinedChange.newData

        if (newData.imageURL != oldData.imageURL) {
            holder.updateImageURL(newData.imageURL)
        }

        if (newData.temperature != oldData.temperature) {
            holder.temperature = newData.temperature ?: return
        }
    }

    fun setItems(newItems: List<City>) {
        val result = DiffUtil.calculateDiff(CitiesListDiffUtilFilter(this.items, newItems))
        result.dispatchUpdatesTo(this)
        this.items.clear()
        this.items.addAll(newItems)
    }

    class CitiesListDiffUtilFilter(
        private var oldItems: List<City>,
        private var newItems: List<City>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].name == newItems[newItemPosition].name

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return Change(oldItem, newItem)
        }
    }
}