package com.pab.mymiaw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.pab.mymiaw.data.model.Cat

class DetailFragment : Fragment() {

    private var cat: Cat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            @Suppress("DEPRECATION")
            cat = it.getSerializable("cat") as? Cat
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        val ivCat = view.findViewById<ImageView>(R.id.iv_cat)
        val tvCatName = view.findViewById<TextView>(R.id.tv_cat_name)
        val tvOrigin = view.findViewById<TextView>(R.id.tv_origin)
        val tvLifespan = view.findViewById<TextView>(R.id.tv_lifespan)
        val tvTemperament = view.findViewById<TextView>(R.id.tv_temperament)
        val tvDescription = view.findViewById<TextView>(R.id.tv_description)

        // Back navigation
        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        // Populate data
        cat?.let { catData ->
            tvCatName.text = catData.name
            tvOrigin.text = catData.origin ?: "Unknown"
            tvLifespan.text = catData.life_span ?: "Unknown"
            tvTemperament.text = catData.temperament ?: "Unknown"
            tvDescription.text = catData.description ?: "No description available"

            // Load image with Glide
            catData.image?.url?.let { imageUrl ->
                Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.logo)
                    .into(ivCat)
            }
        }
    }
}