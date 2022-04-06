package com.example.ilm_izlab.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ilm_izlab.R
import com.example.ilm_izlab.databinding.RegionItemLayoutBinding
import com.example.ilm_izlab.model.DistrictsModel
import com.example.ilm_izlab.model.RegionsModel

class SelectRegionsAdapter (val items: List<RegionsModel>):
    RecyclerView.Adapter<SelectRegionsAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: RegionItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            RegionItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.recyclerDistrict.visibility = if (item.isBtnVisibility) View.VISIBLE else View.GONE
        holder.binding.regionName.text = item.name_uz
        if(item.isBtnVisibility){
            holder.binding.recyclerDistrict.adapter = SelectDistrictsAdapter(item.districts, object: DistrictsAdapterCallback{
                override fun onCheckItem(item: DistrictsModel) {
                    items.forEach {
                        it.districts.forEach {
                            it.checked = false }
                    }
                    item.checked = true
                    notifyDataSetChanged()
                }
            })
        }
        holder.binding.checkVisibility.setOnClickListener {
            if(item.isBtnVisibility){
                holder.binding.recyclerDistrict.visibility = View.GONE
                holder.binding.iconCheck.setImageResource(R.drawable.ic_baseline_arrow_drop_down)
                item.isBtnVisibility = false
            } else {
                holder.binding.recyclerDistrict.visibility = View.VISIBLE
                holder.binding.iconCheck.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                item.isBtnVisibility = true
                holder.binding.recyclerDistrict.adapter = SelectDistrictsAdapter(item.districts, object: DistrictsAdapterCallback{
                    override fun onCheckItem(item: DistrictsModel) {
                        items.forEach {
                            it.districts.forEach { it.checked = false }
                        }
                        item.checked = true
                        notifyDataSetChanged()
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}