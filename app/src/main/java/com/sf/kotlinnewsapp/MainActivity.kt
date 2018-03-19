package com.sf.kotlinnewsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.sf.kotlinnewsapp.Adapter.ListSourceAdapter
import com.sf.kotlinnewsapp.Common.Common
import com.sf.kotlinnewsapp.Interface.NewsService
import com.sf.kotlinnewsapp.Model.WebSite
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: SpotsDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init paperdb
        Paper.init(this)

        mService = Common.newsService

        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)

        loadWebSiteSource(false)

    }

    private fun loadWebSiteSource(isRefresh: Boolean) {
        if (!isRefresh) {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null") {
                // read cache
                val webSite = Gson().fromJson<WebSite>(cache, WebSite::class.java)
                adapter = ListSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            } else {
                // load website
                dialog.show()
                // fetch new data
                mService.sources.enqueue(object : Callback<WebSite> {
                    override fun onFailure(call: Call<WebSite>?, t: Throwable?) {
                        dialog.dismiss()
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<WebSite>?, response: Response<WebSite>?) {
                        adapter = ListSourceAdapter(baseContext, response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        // save to cache
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))
                        dialog.dismiss()
                    }

                })
            }
        } else {
            swipe_to_refresh.isRefreshing = true
            // fetch new data
            mService.sources.enqueue(object : Callback<WebSite> {
                override fun onFailure(call: Call<WebSite>?, t: Throwable?) {
                    dialog.dismiss()
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WebSite>?, response: Response<WebSite>?) {
                    adapter = ListSourceAdapter(baseContext, response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    // save to cache
                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))
                    swipe_to_refresh.isRefreshing = false
                }

            })
        }
    }
}
