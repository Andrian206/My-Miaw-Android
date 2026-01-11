package com.pab.mymiaw

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cat = arguments?.getParcelable<CatBreed>("dataKucing")

        val ivDetail = view.findViewById<ImageView>(R.id.iv_cat)
        val tvName = view.findViewById<TextView>(R.id.tv_cat_name)
        val tvDesc = view.findViewById<TextView>(R.id.tv_description)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)

        cat?.let {
            tvName.text = it.name
            tvDesc.text = "Origin: ${it.origin}\nLife Span: ${it.life_span} years\n\n${it.description}"

            val imageUrl = it.image?.url ?: "https://cdn2.thecatapi.com/images/${it.referenceImageId}.jpg"
            Glide.with(this).load(imageUrl).into(ivDetail)
        }

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}