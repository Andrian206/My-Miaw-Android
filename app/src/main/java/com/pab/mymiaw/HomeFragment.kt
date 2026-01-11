package com.pab.mymiaw

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.iv_menu).setOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val emailUser = user.email?.split("@")?.get(0) ?: "User"
            view.findViewById<TextView>(R.id.tv_hello).text = "Hello, $emailUser"
        }

        val rvCats = view.findViewById<RecyclerView>(R.id.rv_cats)
        rvCats.layoutManager = GridLayoutManager(context, 2)

        CatApiService.create().getBreeds().enqueue(object : Callback<List<CatBreed>> {
            override fun onResponse(call: Call<List<CatBreed>>, response: Response<List<CatBreed>>) {
                if (response.isSuccessful) {
                    val listKucing = response.body() ?: emptyList()
                    val adapterKucing = CatAdapter(listKucing) { kucing ->
                        val bundle = Bundle()
                        bundle.putParcelable("dataKucing", kucing)
                        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                    }
                    rvCats.adapter = adapterKucing
                }
            }

            override fun onFailure(call: Call<List<CatBreed>>, t: Throwable) {
                Toast.makeText(context, "Koneksi Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}