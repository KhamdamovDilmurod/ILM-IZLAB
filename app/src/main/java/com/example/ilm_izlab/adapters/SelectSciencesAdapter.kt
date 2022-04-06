package com.example.ilm_izlab.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ilm_izlab.databinding.DistrictItemLayoutBinding
import com.example.ilm_izlab.model.SciencesModel

interface  SelectSciencesAdapterCallback{
    fun onCheckItem(item: SciencesModel)
}
class SelectSciencesAdapter (val items: List<SciencesModel>, var callback:SelectSciencesAdapterCallback) :
    RecyclerView.Adapter<SelectSciencesAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: DistrictItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            DistrictItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.districtName.text = item.title
        holder.binding.rbChecked.isChecked = item.checked
        holder.binding.lyChecked.setOnClickListener {
            holder.binding.rbChecked.isChecked = !holder.binding.rbChecked.isChecked
            callback.onCheckItem(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}