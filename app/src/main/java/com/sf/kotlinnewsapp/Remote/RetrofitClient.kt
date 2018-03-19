package com.sf.kotlinnewsapp.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by mesutgenc on 19.03.2018.
 */
object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String?): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}