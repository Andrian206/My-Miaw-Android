package com.pab.mymiaw

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

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
        val urlString = cat.image?.url ?: "https://cdn2.thecatapi.com/images/${cat.referenceImageId}.jpg"

        val glideUrl = GlideUrl(
            urlString,
            LazyHeaders.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                .build()
        )

        holder.tvName.text = cat.name

        println("DEBUG_MIAW: Nama: ${cat.name}, URL: $glideUrl")

        Glide.with(holder.itemView.context)
            .load(glideUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.mipmap.ic_launcher)
            .into(holder.imgCat)

        holder.itemView.setOnClickListener { onItemClick(cat) }
    }

    override fun getItemCount(): Int = list.size
}