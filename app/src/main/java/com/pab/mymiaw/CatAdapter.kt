package com.pab.mymiaw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CatAdapter(
    private val list: List<CatBreed>,
    private val onItemClick: (CatBreed) -> Unit
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCat: ImageView = view.findViewById(R.id.iv_cat_image)
        val tvName: TextView = view.findViewById(R.id.tv_cat_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = list[position]
        holder.tvName.text = cat.name

        Glide.with(holder.itemView.context)
            .load(cat.image?.url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgCat)

        holder.itemView.setOnClickListener { onItemClick(cat) }
    }

    override fun getItemCount(): Int = list.size
}