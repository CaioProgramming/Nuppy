package com.ilustris.wishes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilustris.nuppy.bean.WEDDING_ITEM_COMPANION_BUNDLE
import com.ilustris.nuppy.bean.WishItem
import com.ilustris.wishes.R
import kotlinx.android.synthetic.main.activity_wish_item_web_view.*

class WishItemWebViewActivity : AppCompatActivity(R.layout.activity_wish_item_web_view) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(webToolbar)
        val itemData = intent.getSerializableExtra(WEDDING_ITEM_COMPANION_BUNDLE) as? WishItem
        actionBar?.setDisplayHomeAsUpEnabled(true)
        itemData?.let {
            itemWebView.loadUrl(it.link)
        }
    }

    companion object {

        fun showItemLink(context: Context, item: WishItem) {
            Intent(context,
                    WishItemWebViewActivity::class.java).apply {
                putExtra(WEDDING_ITEM_COMPANION_BUNDLE, item)
                context.startActivity(this)
            }
        }


    }
}