package com.sf.kotlinnewsapp.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sf.kotlinnewsapp.Adapter.ViewHolder.ListSourceViewHolder
import com.sf.kotlinnewsapp.Interface.ItemClickListener
import com.sf.kotlinnewsapp.Model.WebSite
import com.sf.kotlinnewsapp.R

/**
 * Created by mesutgenc on 19.03.2018.
 */

class ListSourceAdapter(private val context: Context, private val webSite: WebSite) : RecyclerView.Adapter<ListSourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.source_news_layout, parent, false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return webSite.sources!!.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder?, position: Int) {
        holder!!.source_title.text = webSite.sources!![position].name

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(context, "BJK" + position, Toast.LENGTH_SHORT).show()
            }
        })
    }

}