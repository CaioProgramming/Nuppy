package com.ilustris.wishes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilustris.animations.*
import com.ilustris.nuppy.bean.WishItem
import com.ilustris.wishes.R
import com.ilustris.wishes.bean.ListType
import com.ilustris.wishes.databinding.NewGridItemBinding
import com.ilustris.wishes.databinding.NewTextItemBinding
import com.ilustris.wishes.databinding.WishGridItemLayoutBinding
import com.ilustris.wishes.databinding.WishTextItemLayoutBinding
import com.ilustris.wishes.view.WishItemWebViewActivity
import com.mega4tech.linkpreview.GetLinkPreviewListener
import com.mega4tech.linkpreview.LinkPreview
import com.mega4tech.linkpreview.LinkUtil

class WeddingItemAdapter(private val listType: ListType,
                         private val wishItems: List<WishItem>,
                         private val hasSeeMore: Boolean = false,
                         val removeItemCallback: (WishItem) -> Unit,
                         val newItemCallback: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val GRID = 0
    private val TEXT = 1
    private val NEWITEM = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GRID) {
            GridViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.wish_grid_item_layout,
                            parent,
                            false
                    )
            )
        } else if (viewType == TEXT) {
            TextViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.wish_text_item_layout,
                            parent,
                            false
                    )
            )
        } else {
            if (listType == ListType.GRID_LIST) {
                NewGridItemHolder(DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.new_grid_item, parent, false
                ))
            } else {
                NewTextItemHolder(DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.new_text_item, parent, false
                ))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == wishItems.size && !hasSeeMore) {
            NEWITEM
        } else {
            if (listType == ListType.GRID_LIST) {
                GRID
            } else TEXT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < wishItems.size) {
            if (listType == ListType.GRID_LIST) (holder as GridViewHolder).bindData(wishItems[position]) else (holder as TextViewHolder).bindData(wishItems[position])
        }
    }

    override fun getItemCount(): Int {
        return if (!hasSeeMore) wishItems.count() + 1 else if (wishItems.count() > 4) 4 else wishItems.count() + 1
    }

    inner class GridViewHolder(private val gridItemLayoutBinding: WishGridItemLayoutBinding) : RecyclerView.ViewHolder(gridItemLayoutBinding.root), GetLinkPreviewListener {

        fun bindData(wishItem: WishItem) {
            val context = gridItemLayoutBinding.root.context
            gridItemLayoutBinding.linkCard.slideInBottom()
            LinkUtil.getInstance().getLinkPreview(context, wishItem.link, this)
            gridItemLayoutBinding.linkCard.apply {
                isEnabled = false
                setOnClickListener {
                    WishItemWebViewActivity.showItemLink(it.context, wishItem)
                }
                setOnLongClickListener {
                    removeItemCallback.invoke(wishItem)
                    false
                }
            }
        }

        private fun WishGridItemLayoutBinding.setupLink(preview: LinkPreview) {
            (root.context as AppCompatActivity).runOnUiThread {
                Glide.with(root.context).load(preview.imageFile).into(this.linkImage)
                this.itemTitle.text = preview.title
                this.preview = preview
                this.linkImage.fadeIn()
                this.itemTitle.fadeIn()
                this.linkCard.isEnabled = true
                this.loadingLink.fadeOut()
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

    inner class TextViewHolder(private val textItemLayoutBinding: WishTextItemLayoutBinding) : RecyclerView.ViewHolder(textItemLayoutBinding.root) {
        fun bindData(wishItem: WishItem) {
            textItemLayoutBinding.weddItem = wishItem
            textItemLayoutBinding.root.slideInRight()
            textItemLayoutBinding.root.setOnLongClickListener {
                removeItemCallback.invoke(wishItem)
                false
            }
        }
    }

    inner class NewTextItemHolder(val newTextItemBinding: NewTextItemBinding) : RecyclerView.ViewHolder(newTextItemBinding.root) {
        init {
            newTextItemBinding.newItemView.setOnClickListener {
                newItemCallback.invoke()
            }
        }
    }

    inner class NewGridItemHolder(val newGridItemBinding: NewGridItemBinding) : RecyclerView.ViewHolder(newGridItemBinding.root) {
        init {
            newGridItemBinding.newItemView.setOnClickListener {
                newItemCallback.invoke()
            }
        }
    }

}