package com.ilustris.wishes.view.binder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.animations.slideInRight
import com.ilustris.nuppy.bean.WishItem
import com.ilustris.wishes.bean.ListType
import com.ilustris.wishes.bean.WishList
import com.ilustris.wishes.databinding.HeaderRecyclerLayoutBinding
import com.ilustris.wishes.presenter.WeddingPresenter
import com.ilustris.wishes.view.WishListActivity
import com.ilustris.wishes.view.adapter.WeddingItemAdapter
import com.ilustris.wishes.view.dialog.NewLinkItemDialog
import com.silent.ilustriscore.core.utilities.visible
import com.silent.ilustriscore.core.view.BaseView

class ListHeadBinder(
        override val context: Context,
        override val viewBind: HeaderRecyclerLayoutBinding,
        private val wishList: WishList,
) : BaseView<WishList>() {
    override fun presenter() = WeddingPresenter(this)

    init {
        initView()
    }

    override fun initView() {
        viewBind.run {
            val itemCount = if (wishList.items.size > 1 && wishList.listType != ListType.TEXT_LIST) 2 else 1
            title.text = wishList.title
            recyclerChild.apply {
                adapter = WeddingItemAdapter(wishList.listType, wishList.items, wishList.items.count() > 4, removeItemCallback = { wish ->
                    wishList.items.remove(wish)
                    presenter().updateData(wishList)
                }, newItemCallback = {
                    newItemDialog()
                })
                layoutManager = GridLayoutManager(context, itemCount, RecyclerView.VERTICAL, false)
            }

            wishesCard.setOnLongClickListener {
                presenter().deleteList(wishList.id)
                false
            }
            if (wishList.items.count() > 4) {
                viewMoreButton.run {
                    visible()
                    setOnClickListener {
                        WishListActivity.launchWishList(context, wishList.id)
                    }
                }
            }
            viewBind.root.slideInRight()
        }
    }

    private fun newItemDialog() {
        NewLinkItemDialog.newInstance(wishList.listType, onSaveItem = { newItem ->
            if (!wishList.itemExists(newItem)) {
                wishList.items.add(
                        WishItem(
                                link = newItem,
                                whoAdded = presenter().currentUser!!.uid
                        )
                )
                presenter().saveData(wishList, wishList.id)
            }
        }).show((context as AppCompatActivity).supportFragmentManager, "newItemDialog")
    }
}