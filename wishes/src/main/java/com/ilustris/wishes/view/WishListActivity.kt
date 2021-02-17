package com.ilustris.wishes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ilustris.wishes.R
import com.ilustris.wishes.view.binder.WishListBinder

const val WISH_LIST_ID_BUNDLE = "WISHLIST"

class WishListActivity : AppCompatActivity(R.layout.activity_wish_list) {

    private fun getWishesID(): String = intent.getStringExtra(WISH_LIST_ID_BUNDLE) as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WishListBinder(this, DataBindingUtil.setContentView(this, R.layout.activity_wish_list), getWishesID()).initView()

    }

    companion object {
        fun launchWishList(context: Context, wishListID: String) {
            context.startActivity(Intent(context, WishListActivity::class.java).apply {
                putExtra(WISH_LIST_ID_BUNDLE, wishListID)
            })
        }
    }
}