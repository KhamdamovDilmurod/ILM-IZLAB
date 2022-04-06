package com.example.ilm_izlab.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilm_izlab.databinding.VibrationItemLayoutBinding
import com.example.ilm_izlab.model.AllNewsModel
import com.example.ilm_izlab.model.SciencesModel
import com.example.ilm_izlab.screen.edudetail.EduDetailActivity
import com.example.ilm_izlab.screen.news.newsdetail.NewsDetailActivity
import com.example.ilm_izlab.utils.Constants


class NewsAdapter(val items: List<AllNewsModel>):
    RecyclerView.Adapter<NewsAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: VibrationItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            VibrationItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.name.text = item.center_name
        holder.binding.title.text = item.title
        holder.binding.date.text = item.date
        holder.binding.district.text = item.district_name
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.image).into(holder.binding.image)
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.center_image).into(holder.binding.centerImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, NewsDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,item)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}