package com.example.awesomeapp.network

import com.example.awesomeapp.model.Response
import com.example.awesomeapp.utils.Utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WebService {
    @Headers("Authorization: $API_KEY")
    @GET("v1/curated")
    suspend fun getData(
        @Query("page") page: Int,
    ): retrofit2.Response<Response>
}