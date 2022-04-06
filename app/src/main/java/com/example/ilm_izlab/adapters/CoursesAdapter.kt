package com.example.ilm_izlab.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ilm_izlab.databinding.CoursesItemLayoutBinding
import com.example.ilm_izlab.model.CategoryModel
import com.example.ilm_izlab.model.CoursesModel
import com.example.ilm_izlab.model.SciencesModel
import com.example.ilm_izlab.screen.edudetail.EduDetailActivity
import com.example.ilm_izlab.screen.teacher.TeacherActivity
import com.example.ilm_izlab.utils.Constants

class CoursesAdapter(val items: List<CoursesModel>):
    RecyclerView.Adapter<CoursesAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CoursesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CoursesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.coursesName.text = item.name
        holder.binding.monthlyPayment.text = item.monthly_payment.toString() + "/oylik"
        holder.binding.description.text = item.name
        holder.binding.btnTeacher.setOnClickListener {
            val intent = Intent(it.context, TeacherActivity::class.java)
            intent.putExtra("course_id",item.id)
            it.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }
}