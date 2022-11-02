package com.dev.playground.data.di

import com.dev.playground.data.api.DropApi
import com.dev.playground.data.util.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 1L
private const val READ_TIMEOUT = 20L
const val BASE_URL_KEY = "base_url_key"

val networkModule = module {

    single {
        val logging = HttpLoggingInterceptor { message ->
            try {
                JSONObject(message)
                HttpLoggingInterceptor.Logger.DEFAULT.log(message)
            } catch (e: JSONException) {
                HttpLoggingInterceptor.Logger.DEFAULT.log(message)
            }
        }
        logging.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(AuthenticationInterceptor(get()))
            .addInterceptor(logging)
            .retryOnConnectionFailure(true)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<String>(named(BASE_URL_KEY)))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(DropApi::class.java) }
}