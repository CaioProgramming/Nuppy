package com.ilustris.wishes.view.binder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.nuppy.bean.WishItem
import com.ilustris.wishes.bean.ListType
import com.ilustris.wishes.bean.WishList
import com.ilustris.wishes.databinding.ActivityWishListBinding
import com.ilustris.wishes.presenter.WeddingPresenter
import com.ilustris.wishes.view.adapter.WeddingItemAdapter
import com.ilustris.wishes.view.dialog.NewLinkItemDialog
import com.silent.ilustriscore.core.view.BaseView

class WishListBinder(override val context: Context, override val viewBind: ActivityWishListBinding, val wishListID: String) : BaseView<WishList>() {

    override fun presenter() = WeddingPresenter(this)

    override fun initView() {
        presenter().loadSingleData(wishListID)
    }

    override fun showData(data: WishList) {
        super.showData(data)
        viewBind.run {
            val itemCount = if (data.items.size > 1 && data.listType != ListType.TEXT_LIST) 2 else 1
            coupleHeader.coupleCollapsetoolbar.run {
                title = data.title
                subtitle = "${data.items.size} items"
            }
            wishesRecycler.apply {
                adapter = WeddingItemAdapter(
                        data.listType,
                        data.items,
                        false,
                        removeItemCallback = { wish ->
                            data.items.remove(wish)
                            presenter().updateData(data)
                        }, newItemCallback = {
                    newItemDialog(data)
                })
                layoutManager = GridLayoutManager(context, itemCount, RecyclerView.VERTICAL, false)
            }
            (context as AppCompatActivity).run {
                setSupportActionBar(coupleHeader.coupleToolbar)
                coupleHeader.coupleToolbar.setNavigationOnClickListener {
                    this.finish()
                }
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }

        }

    }

    private fun newItemDialog(wishList: WishList) {
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