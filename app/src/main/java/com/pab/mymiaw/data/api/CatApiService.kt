package com.pab.mymiaw.data.api

import com.pab.mymiaw.data.model.Cat
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(
        @Header("x-api-key") apiKey: String = "live_iqhILBJkgJ0bNDXWXFqUgPpKqd7bc1xk0BHCqjBgVzVe3qZQyUlIJb2OeAQ5qMYP",
        @Query("limit") limit: Int = 10
    ): List<Cat>
}
