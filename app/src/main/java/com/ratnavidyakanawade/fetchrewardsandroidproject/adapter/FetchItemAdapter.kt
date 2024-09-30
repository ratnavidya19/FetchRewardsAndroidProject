package com.ratnavidyakanawade.fetchrewardsandroidproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ratnavidyakanawade.fetchrewardsandroidproject.databinding.ItemsLayoutBinding
import com.ratnavidyakanawade.fetchrewardsandroidproject.model.FetchItems

class FetchItemAdapter(private val itemClicked:(FetchItems) -> Unit
): RecyclerView.Adapter<FetchItemAdapter.FetchRewardsViewHolder>() {
    private var itemList: List<FetchItems> = emptyList()

    fun bindData(items: List<FetchItems>){
        itemList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FetchRewardsViewHolder {
        val binding = ItemsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FetchRewardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FetchRewardsViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class FetchRewardsViewHolder(
        val binding: ItemsLayoutBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(item: FetchItems){
            binding.textViewItemListId.text = "List ID: ${item.listId}"
            binding.textViewItemName.text = "Name: ${item.name}"

            itemView.setOnClickListener {
                itemClicked(item)
            }
        }
    }
}