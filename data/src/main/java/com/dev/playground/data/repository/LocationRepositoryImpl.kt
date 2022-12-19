package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.LocationService
import com.dev.playground.data.di.X_NCP_APIGW_API_KEY
import com.dev.playground.data.di.X_NCP_APIGW_API_KEY_ID
import com.dev.playground.data.model.toAddressString
import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val service: LocationService,
    private val apiKeyId: String,
    private val apiKey: String,
) : LocationRepository {

    companion object {
        private const val NAVER_REVERSE_GEOCODING_URL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc"
        private const val COORDINATE_KEY = "coords"
        private const val OUTPUT_KEY = "output"
        private const val OUTPUT_VALUE = "json"
        private const val ORDER_KEY = "orders"
        private const val ORDER_VALUE = "addr"
    }

    override suspend fun getAddress(params: Memory.Location): String = service.getAddress(
        url = NAVER_REVERSE_GEOCODING_URL,
        params =
        mapOf(
            COORDINATE_KEY to params.toCoordinate(),
            OUTPUT_KEY to OUTPUT_VALUE,
            ORDER_KEY to ORDER_VALUE
        ),
        headers = mapOf(
            X_NCP_APIGW_API_KEY_ID to apiKeyId,
            X_NCP_APIGW_API_KEY to apiKey
        )
    ).toAddressString()

}