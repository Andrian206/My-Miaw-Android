package com.pab.mymiaw

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface CatApiService {
    @GET("breeds?limit=20&has_breeds=1")
    @Headers("x-api-key: live_ZXfQ8uWXmV75zl0dddaI1qNNxM9eqyvGgm430a3SrjxB0AwFil6QF7fph0G4jHK6")
    fun getBreeds(): Call<List<CatBreed>>

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/v1/"

        fun create(): CatApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(CatApiService::class.java)
        }
    }
}