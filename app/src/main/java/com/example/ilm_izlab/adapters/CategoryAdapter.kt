package com.example.ilm_izlab.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilm_izlab.databinding.CategoryItemLayoutBinding
import com.example.ilm_izlab.model.CategoryModel
import com.example.ilm_izlab.screen.categories.CategoriesActivity
import com.example.ilm_izlab.screen.edudetail.EduDetailActivity
import com.example.ilm_izlab.utils.Constants


class CategoryAdapter(val items: List<CategoryModel>):
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.categoryTitle.text = item.title
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+item.icon).into(holder.binding.categoryIcon)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CategoriesActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,item)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}