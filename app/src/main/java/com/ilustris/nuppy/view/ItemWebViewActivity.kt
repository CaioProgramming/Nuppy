package com.ilustris.nuppy.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.WEDDING_ITEM_COMPANION_BUNDLE
import com.ilustris.nuppy.bean.WeddingItem
import kotlinx.android.synthetic.main.activity_item_web_view.*



class ItemWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_web_view)
        setSupportActionBar(webToolbar)
        val itemData = intent.getSerializableExtra(WEDDING_ITEM_COMPANION_BUNDLE) as? WeddingItem
        actionBar?.setDisplayHomeAsUpEnabled(true)
        itemData?.let {
            itemWebView.loadUrl(it.link)
        }
    }

    companion object {

        fun showItemLink(context: Context,item: WeddingItem) {
            Intent(context,ItemWebViewActivity::class.java).apply {
                putExtra(WEDDING_ITEM_COMPANION_BUNDLE,item)
                context.startActivity(this)
            }
        }


    }
}