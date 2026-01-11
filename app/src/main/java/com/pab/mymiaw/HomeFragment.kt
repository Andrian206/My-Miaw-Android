package com.pab.mymiaw

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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