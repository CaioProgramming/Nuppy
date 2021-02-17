package com.ilustris.wishes.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.wishes.R
import com.ilustris.wishes.bean.WishList
import com.ilustris.wishes.databinding.CreateListLayoutBinding
import com.ilustris.wishes.databinding.HeaderRecyclerLayoutBinding
import com.ilustris.wishes.view.binder.ListHeadBinder

class WeddingHeaderAdapter(private var wishListItems: List<WishList>, val coupleID: String, val createListCallback: () -> Unit) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val LAST_ITEM = 0
    private val COMMON_ITEM = 1

    fun updateWeddingList(wishListItems: List<WishList>) {
        this.wishListItems = wishListItems.sortedBy { it.title }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            LAST_ITEM
        } else COMMON_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == COMMON_ITEM)
            WeddingHeaderHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.header_recycler_layout, parent, false))
        else NewListHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.create_list_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < wishListItems.size) {
            (holder as WeddingHeaderHolder).bind(wishListItems[position])
        }
    }

    override fun getItemCount(): Int {
        return wishListItems.count() + 1
    }

    inner class WeddingHeaderHolder(private val headerRecyclerLayoutBinding: HeaderRecyclerLayoutBinding)
        : RecyclerView.ViewHolder(headerRecyclerLayoutBinding.root) {
        private val context: Context = headerRecyclerLayoutBinding.root.context

        fun bind(wishList: WishList) {
            ListHeadBinder(context, headerRecyclerLayoutBinding, wishList)
        }

    }

    inner class NewListHolder(private val createListLayoutBinding: CreateListLayoutBinding) : RecyclerView.ViewHolder(createListLayoutBinding.root) {

        init {
            createListLayoutBinding.addItemToListCard.setOnClickListener {
                createListCallback.invoke()
            }
        }
    }
}