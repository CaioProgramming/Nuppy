package com.ilustris.nuppy.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ilustris.nuppy.R
import com.ilustris.nuppy.view.binder.MainActBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        MainActBinder(this,DataBindingUtil.setContentView(this, R.layout.activity_main))
    }

}