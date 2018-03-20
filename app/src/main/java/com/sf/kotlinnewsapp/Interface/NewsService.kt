package com.sf.kotlinnewsapp.Interface

import com.sf.kotlinnewsapp.Model.News
import com.sf.kotlinnewsapp.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by mesutgenc on 19.03.2018.
 */
interface NewsService {
    @get:GET("v2/sources?apiKey=254a6bf8848c4a7a85d4d2762f2de433")
    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}