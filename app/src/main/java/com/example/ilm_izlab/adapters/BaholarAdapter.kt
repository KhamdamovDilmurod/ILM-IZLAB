package com.example.ilm_izlab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilm_izlab.databinding.BaholarItemLayoutBinding
import com.example.ilm_izlab.model.RatingsModel
import com.example.ilm_izlab.utils.Constants

class BaholarAdapter(val items: List<RatingsModel>) :
    RecyclerView.Adapter<BaholarAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: BaholarItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            BaholarItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.name.text = item.user_fullname
        holder.binding.date.text = item.date
        holder.binding.comment.text = item.comment
        holder.binding.ratingNumber.text = String.format("%.1f", item.rating)
        holder.binding.ratingBar.rating = item.rating

        if (item.user_avatar != null) {
            Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.user_avatar)
                .into(holder.binding.userImage)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}