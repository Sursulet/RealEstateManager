package com.openclassrooms.realestatemanager.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }

}