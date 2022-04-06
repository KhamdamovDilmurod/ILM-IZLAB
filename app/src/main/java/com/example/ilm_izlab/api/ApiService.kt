package com.example.ilm_izlab.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.ilm_izlab.screen.main.MyApp
import com.example.ilm_izlab.utils.Constants
import com.example.ilm_izlab.utils.PrefUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    var retrofit: Retrofit? = null
    fun apiClient(): Api{
        if (retrofit == null){
            val client = OkHttpClient().newBuilder()
                .addInterceptor(
                    ChuckerInterceptor.Builder(MyApp.app)
                        .collector(ChuckerCollector(MyApp.app)).maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build()
                ).addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("key", "n3oEGoWOZtO4sxSC5q5VyQEDKx9vaE")
                        .addHeader("token",PrefUtils.getToken()).build()
                    return@addInterceptor it.proceed(request)
                }
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client).build()
        }
        return retrofit!!.create(Api::class.java)
    }
}
