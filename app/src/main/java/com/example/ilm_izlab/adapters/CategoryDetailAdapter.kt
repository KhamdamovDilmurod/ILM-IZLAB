package com.example.ilm_izlab.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.CategoryDetailItemLayoutBinding
import com.example.ilm_izlab.model.SciencesModel

interface CategoryDetailAdapterCallback {
    fun onClickItem(item:SciencesModel)
}

class CategoryDetailAdapter(val items: List<SciencesModel>, val callback: CategoryDetailAdapterCallback):
    RecyclerView.Adapter<CategoryDetailAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CategoryDetailItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryDetailItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.categoryTitle.text = item.title
        holder.itemView.setOnClickListener {
            items.forEach {
                it.checked=false
            }
            item.checked = true

            callback.onClickItem(item)
            notifyDataSetChanged()
        }
        if (item.checked){
            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gold))
            holder.binding.categoryTitle.setTextColor(Color.WHITE )
            holder.binding.imageIcon.setColorFilter(ContextCompat.getColor(holder.itemView.context,R.color.white),android.graphics.PorterDuff.Mode.MULTIPLY)

        } else{
            holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.binding.categoryTitle.setTextColor(Color.BLACK )
            holder.binding.imageIcon.setColorFilter(ContextCompat.getColor(holder.itemView.context,R.color.gold),android.graphics.PorterDuff.Mode.MULTIPLY)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}