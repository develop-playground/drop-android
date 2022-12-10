package com.dev.playground.data.data_source.remote

import com.dev.playground.data.model.MemoryData
import retrofit2.http.GET
import retrofit2.http.Query

interface MemoryService {

    @GET("memory")
    suspend fun getMemoryList(@Query("page") page: Int): List<MemoryData>

}