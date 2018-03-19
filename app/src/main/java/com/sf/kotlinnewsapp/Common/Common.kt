package com.sf.kotlinnewsapp.Common

import com.sf.kotlinnewsapp.Interface.NewsService
import com.sf.kotlinnewsapp.Remote.RetrofitClient

/**
 * Created by mesutgenc on 19.03.2018.
 */

object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "254a6bf8848c4a7a85d4d2762f2de433"


    val newsService: NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)
}