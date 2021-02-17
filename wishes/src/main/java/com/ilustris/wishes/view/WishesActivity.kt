package com.ilustris.wishes.view


import android.os.Bundle

import androidx.databinding.DataBindingUtil
import com.ilustris.base.BaseActivity
import com.ilustris.wishes.R
import com.ilustris.wishes.view.binder.WishesActBinder

class WishesActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishes)
        getCoupleID()?.let {
            WishesActBinder(this, DataBindingUtil.setContentView(this, R.layout.activity_wishes), it)
        }

    }
}