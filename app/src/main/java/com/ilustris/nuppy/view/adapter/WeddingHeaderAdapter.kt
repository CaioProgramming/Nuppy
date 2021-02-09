package com.ilustris.nuppy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.WeddingList
import com.ilustris.nuppy.databinding.HeaderRecyclerLayoutBinding
import com.ilustris.nuppy.view.binder.ListHeadBinder

class WeddingHeaderAdapter(private var weddingListItems: List<WeddingList>):
    RecyclerView.Adapter<WeddingHeaderAdapter.WeddingHeaderHolder>() {

    fun updateWeddingList(weddingListItems: List<WeddingList>) {
        this.weddingListItems = weddingListItems.sortedBy { it.title }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeddingHeaderHolder {
        return WeddingHeaderHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.header_recycler_layout,parent,false))
    }

    override fun onBindViewHolder(holder: WeddingHeaderHolder, position: Int) {
        holder.bind(weddingListItems[position])
    }

    override fun getItemCount(): Int {
        return weddingListItems.count()
    }

    inner class WeddingHeaderHolder(val headerRecyclerLayoutBinding: HeaderRecyclerLayoutBinding)
        : RecyclerView.ViewHolder(headerRecyclerLayoutBinding.root) {
        val context = headerRecyclerLayoutBinding.root.context

        fun bind(weddingList: WeddingList) {
            ListHeadBinder(context, headerRecyclerLayoutBinding,weddingList)
        }

    }
}