package com.github.liangyu.earthquakes.di.networking

import com.github.liangyu.earthquakes.data.networking.GeoNameEarthquakeService
import com.github.liangyu.earthquakes.data.networking.ServiceFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ServiceModule {

    companion object {
        private const val ENDPOINT = "http://api.geonames.org/"
        private const val READ_TIMEOUT_SECONDS = 5L
        private const val CONNECTION_TIMEOUT_SECONDS = 5L
        private const val USERNAME = "mkoppelman"
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("username", USERNAME)
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .url(original)
                    .build()

                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    fun providesServiceFactory(gson: Gson, okHttp: OkHttpClient): ServiceFactory {
        return ServiceFactory(gson, okHttp)
    }

    @Provides
    fun providesGeoNameEarthquakeService(factory: ServiceFactory): GeoNameEarthquakeService {
        return factory.createService(GeoNameEarthquakeService::class.java, ENDPOINT)
    }
}
