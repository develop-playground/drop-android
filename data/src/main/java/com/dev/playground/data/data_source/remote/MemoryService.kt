package com.dev.playground.data.data_source.remote

import com.dev.playground.data.model.MemoryData
import com.dev.playground.domain.model.MemoryInput
import retrofit2.http.*

interface MemoryService {

    @GET("memory")
    suspend fun getMemoryList(@Query("page") page: Int): List<MemoryData>

    @POST("memory")
    suspend fun postMemory(@Body memoryInput: MemoryInput): MemoryData

}