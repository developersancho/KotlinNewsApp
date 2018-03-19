package com.sf.kotlinnewsapp.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.sf.kotlinnewsapp.Interface.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*

/**
 * Created by mesutgenc on 19.03.2018.
 */
class ListSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener
    var source_title = itemView.source_news_name

    init {
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition)
    }

}