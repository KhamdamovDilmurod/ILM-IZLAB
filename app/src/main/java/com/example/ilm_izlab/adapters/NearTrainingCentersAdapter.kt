package com.example.ilm_izlab.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilm_izlab.databinding.NearTrainingCentersItemLayoutBinding
import com.example.ilm_izlab.model.TrainingCentersModel
import com.example.ilm_izlab.screen.edudetail.EduDetailActivity
import com.example.ilm_izlab.utils.Constants

class NearTrainingCentersAdapter (val items: List<TrainingCentersModel>):
    RecyclerView.Adapter<NearTrainingCentersAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: NearTrainingCentersItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            NearTrainingCentersItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.rating.text = String.format("%.1f", item.rating)
        holder.binding.name.text = item.name
        var sciences = ""
        item.courses.forEach {
            sciences += it.science.title + ", "
        }
        holder.binding.sciences.text = sciences
        holder.binding.comment.text = item.comment
        holder.binding.district.text = item.district.district_name
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.main_image).into(holder.binding.image)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, EduDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA,item)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}