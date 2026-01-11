package com.pab.mymiaw.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.pab.mymiaw.R
import com.pab.mymiaw.data.model.Cat

class CatAdapter(
    private val cats: List<Cat>,
    private val onItemClick: (Cat) -> Unit
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardCat: MaterialCardView = itemView.findViewById(R.id.card_cat)
        val ivCatImage: ImageView = itemView.findViewById(R.id.iv_cat_image)
        val tvCatName: TextView = itemView.findViewById(R.id.tv_cat_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]

        holder.tvCatName.text = cat.name

        // Load image using Glide
        cat.image?.url?.let { imageUrl ->
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.color.primary_yellow)
                .error(R.color.primary_yellow)
                .centerCrop()
                .into(holder.ivCatImage)
        }

        holder.cardCat.setOnClickListener {
            onItemClick(cat)
        }
    }

    override fun getItemCount(): Int = cats.size
}
