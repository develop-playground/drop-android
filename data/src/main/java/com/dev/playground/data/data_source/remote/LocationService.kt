package com.dev.playground.data.data_source.remote

import com.dev.playground.data.model.AddressData
import retrofit2.http.*

interface LocationService {

    @GET
    suspend fun getAddress(
        @Url url: String,
        @QueryMap(encoded = false) params: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
    ): AddressData

}