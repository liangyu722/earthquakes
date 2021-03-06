package com.github.liangyu.earthquakes.data.networking

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory(
    private val gson: Gson,
    private val httpClient: OkHttpClient
) {

    fun <T> createService(clazz: Class<T>, endpoint: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(httpClient)
            .addConverterFactory(EarthquakeResponseConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(clazz)
    }
}
