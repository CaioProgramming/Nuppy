package com.ilustris.nuppy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilustris.animations.*
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.ListType
import com.ilustris.nuppy.bean.WeddingItem
import com.ilustris.nuppy.databinding.GridItemLayoutBinding
import com.ilustris.nuppy.databinding.TextItemLayoutBinding
import com.ilustris.nuppy.view.ItemWebViewActivity
import com.mega4tech.linkpreview.GetLinkPreviewListener
import com.mega4tech.linkpreview.LinkPreview
import com.mega4tech.linkpreview.LinkUtil
import com.silent.ilustriscore.core.utilities.*
import java.lang.Exception

class WeddingItemAdapter(private val listType: ListType,private val weddingItems: List<WeddingItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (listType == ListType.GRID_LIST) {
            GridViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.grid_item_layout,
                    parent,
                    false
                )
            )
        } else {
            TextViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.text_item_layout,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < weddingItems.size) {
            if (listType == ListType.GRID_LIST) (holder as GridViewHolder).bindData(weddingItems[position]) else (holder as TextViewHolder).bindData(weddingItems[position])
        }
    }

    override fun getItemCount(): Int {
       return weddingItems.count()
    }

    inner class GridViewHolder(private val gridItemLayoutBinding: GridItemLayoutBinding): RecyclerView.ViewHolder(gridItemLayoutBinding.root), GetLinkPreviewListener {

        fun bindData(weddingItem: WeddingItem) {
            val context = gridItemLayoutBinding.root.context
            LinkUtil.getInstance().getLinkPreview(context, weddingItem.link,this)
            gridItemLayoutBinding.linkCard.apply {
                isEnabled = false
                setOnClickListener {
                    ItemWebViewActivity.showItemLink(it.context,weddingItem)
                }
            }
        }

        private fun GridItemLayoutBinding.setupLink(preview: LinkPreview){
            (root.context as AppCompatActivity).runOnUiThread {
                Glide.with(root.context).load(preview.imageFile).into(this.linkImage)
                this.preview = preview
                this.loadingLink.fadeOut()
                this.linkImage.fadeIn()
                this.itemTitle.fadeIn()
                this.linkCard.isEnabled = true
            }
        }

        override fun onSuccess(preview: LinkPreview?) {
           preview?.let {
               gridItemLayoutBinding.setupLink(preview)
           }
        }

        override fun onFailed(e: Exception?) {
            e?.printStackTrace()
            (itemView.context as AppCompatActivity).runOnUiThread {
                this.gridItemLayoutBinding.linkCard.slideOutLeft()
            }
        }

    }

    inner class TextViewHolder(private val textItemLayoutBinding: TextItemLayoutBinding) : RecyclerView.ViewHolder(textItemLayoutBinding.root) {
        fun bindData(weddingItem: WeddingItem) {
            textItemLayoutBinding.weddItem = weddingItem
            textItemLayoutBinding.root.slideInBottom()
        }
    }


}