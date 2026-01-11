package com.pab.mymiaw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.pab.mymiaw.adapter.CatAdapter
import com.pab.mymiaw.data.api.RetrofitClient
import com.pab.mymiaw.data.model.Cat
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var rvCats: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvHello: TextView
    private val catList = mutableListOf<Cat>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init views
        rvCats = view.findViewById(R.id.rv_cats)
        progressBar = view.findViewById(R.id.progress_bar)
        tvHello = view.findViewById(R.id.tv_hello)
        val ivMenu = view.findViewById<ImageView>(R.id.iv_menu)

        // Setup RecyclerView with Grid 2 columns
        rvCats.layoutManager = GridLayoutManager(context, 2)
        val adapter = CatAdapter(catList) { cat ->
            // Navigate to detail with cat data
            val bundle = Bundle().apply {
                putSerializable("cat", cat)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
        rvCats.adapter = adapter

        // Get username from Firestore
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").document(userId).get()
                .addOnSuccessListener { doc ->
                    val username = doc.getString("username") ?: "User"
                    tvHello.text = "Hello, $username"
                }
                .addOnFailureListener {
                    tvHello.text = "Hello, User"
                }
        } else {
            tvHello.text = "Hello, Guest"
        }

        // Open drawer
        // 2

        // Fetch cats from API
        fetchCats(adapter)
    }

    private fun fetchCats(adapter: CatAdapter) {
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val cats = RetrofitClient.instance.getBreeds()
                catList.clear()
                catList.addAll(cats)
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(context, "Failed to load data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}