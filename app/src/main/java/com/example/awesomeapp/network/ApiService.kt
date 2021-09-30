package com.example.awesomeapp.network

import com.example.awesomeapp.BuildConfig
import com.example.awesomeapp.helper.Helper
import com.example.awesomeapp.model.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: ${BuildConfig.KEY}")
    @GET(Helper.END_POINT)
    suspend fun getData(
        @Query("page") page: Int,
    ): retrofit2.Response<Response>
}