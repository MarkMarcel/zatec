package com.freelancemarcel.gotown.core

import android.app.Application
import com.freelancemarcel.gotown.core.network.IceAndFireAPI
import com.freelancemarcel.gotown.houses.data.HouseRepository
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val TIMEOUT_DEFAULT_CONFIGURATION = 15_000L

class IceAndFireApplication : Application() {
    val houseRepository by lazy { HouseRepository(iceAndFireAPI) }

    private val iceAndFireAPI by lazy { createIceAndFireAPI() }

    private fun createIceAndFireAPI(): IceAndFireAPI {
        val converter = GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        ) as Converter.Factory
        val httpClient = OkHttpClient.Builder().apply {
            //TODO: remove if (BuildConfig.DEBUG)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor)
                .callTimeout(TIMEOUT_DEFAULT_CONFIGURATION, TimeUnit.SECONDS)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.anapioficeandfire.com/api/")
            .client(httpClient)
            .addConverterFactory(converter)
            .build()
        return retrofit.create(IceAndFireAPI::class.java)
    }
}