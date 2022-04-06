package com.example.ilm_izlab.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ilm_izlab.databinding.TeacherItemLayoutBinding
import com.example.ilm_izlab.model.CourseTeachersModel

class TeachersAdapter (val items: List<CourseTeachersModel>):
    RecyclerView.Adapter<TeachersAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: TeacherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            TeacherItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
            holder.binding.teacherName.text = item.name
            holder.binding.specialization.text = item.specialization
            holder.binding.experience.text ="${item.experience} yillik tajriba"
            val url = item.info_link

        holder.binding.connectWithTeacher.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                it.context.startActivity(intent)
            }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}