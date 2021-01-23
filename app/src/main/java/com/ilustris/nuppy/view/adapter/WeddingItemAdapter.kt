package com.ilustris.nuppy.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.WeddingItem
import com.ilustris.nuppy.databinding.LinkItemsLayoutBinding
import com.ilustris.nuppy.databinding.LinkPreviewDialogBinding
import com.mega4tech.linkpreview.GetLinkPreviewListener
import com.mega4tech.linkpreview.LinkPreview
import com.mega4tech.linkpreview.LinkUtil
import com.silent.ilustriscore.core.utilities.fadeOut
import java.lang.Exception

class WeddingItemAdapter(val weddingItems: List<WeddingItem>): RecyclerView.Adapter<WeddingItemAdapter.WeddingViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeddingViewHolder {
        return WeddingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.link_items_layout,parent,false))
    }

    override fun onBindViewHolder(holder: WeddingViewHolder, position: Int) {
        if (position < weddingItems.size) {
            holder.bindData(weddingItems[position])
        }
    }

    override fun getItemCount(): Int {
       return weddingItems.count()
    }


    inner class WeddingViewHolder(private val linkItemsLayoutBinding: LinkItemsLayoutBinding): RecyclerView.ViewHolder(linkItemsLayoutBinding.root), GetLinkPreviewListener {

        fun bindData(weddingItem: WeddingItem) {
            val context = linkItemsLayoutBinding.root.context
            LinkUtil.getInstance().getLinkPreview(context, weddingItem.link,this)

        }

        private fun LinkItemsLayoutBinding.setupLink(preview: LinkPreview){
            (root.context as AppCompatActivity).runOnUiThread {
                Glide.with(root.context).load(preview.imageFile).into(this.linkImage)
                this.preview = preview
                this.loadingLink.fadeOut()
            }
        }

        override fun onSuccess(preview: LinkPreview?) {
           preview?.let {
               linkItemsLayoutBinding.setupLink(preview)
           }
        }

        override fun onFailed(p0: Exception?) {

        }

    }

}