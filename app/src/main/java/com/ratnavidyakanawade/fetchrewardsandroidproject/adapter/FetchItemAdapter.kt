package com.ratnavidyakanawade.fetchrewardsandroidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ratnavidyakanawade.fetchrewardsandroidproject.R
import com.ratnavidyakanawade.fetchrewardsandroidproject.databinding.ItemsLayoutBinding
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems

class FetchItemAdapter(private val itemClicked:(FetchItems) -> Unit) : RecyclerView.Adapter<FetchItemAdapter.ItemViewHolder>() {

    var items: List<FetchItems>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),  R.layout.items_layout,
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items?.get(position)
        holder.binding.item = item
        //calling bind method to activate click listener
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ItemViewHolder(val binding: ItemsLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: FetchItems){

            itemView.setOnClickListener {
                itemClicked(item)
            }
        }

    }
}