package com.sf.kotlinnewsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Adapter
import android.widget.Toast
import com.google.gson.Gson
import com.sf.kotlinnewsapp.Adapter.ListNewsAdapter
import com.sf.kotlinnewsapp.Common.Common
import com.sf.kotlinnewsapp.Interface.NewsService
import com.sf.kotlinnewsapp.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNewsActivity : AppCompatActivity() {

    var source = ""
    var webHotUrl: String? = ""

    lateinit var dialog: SpotsDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService
        dialog = SpotsDialog(this)
        swipe_to_refresh.setOnRefreshListener { loadNews(source, true) }

        diagonalLayout.setOnClickListener {
            val detail = Intent(baseContext, NewsDetailActivity::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent != null) {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                loadNews(source, false)
        }


    }

    private fun loadNews(source: String?, isRefresh: Boolean) {
        if (!isRefresh) {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()

                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url

                            // load all remain articles
                            val removeFirstItem = response!!.body()!!.articles
                            // because we get first item to hot new, so we need remove it
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                        }

                    })
        } else {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : Callback<News> {
                        override fun onFailure(call: Call<News>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()

                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)

                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url

                            // load all remain articles
                            val removeFirstItem = response!!.body()!!.articles
                            // because we get first item to hot new, so we need remove it
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                            swipe_to_refresh.isRefreshing = false
                        }

                    })
        }

    }
}
