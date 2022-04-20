package com.example.ilm_izlab.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilm_izlab.R
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.utils.Constants

class PagerAdapter(var images: ArrayList<String>): RecyclerView.Adapter<PagerAdapter.ViewPagerHolder>() {
    inner class ViewPagerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_pager_container, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val pager = images[position]
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+pager).into(holder.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}