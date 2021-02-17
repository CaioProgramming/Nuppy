package com.ilustris.couple.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ilustris.base.Routes
import com.ilustris.couple.R
import com.ilustris.couple.view.binder.CoupleBinder

class CoupleActivity : AppCompatActivity() {

    lateinit var coupleBinder: CoupleBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coupleBinder = CoupleBinder(DataBindingUtil.setContentView(this, R.layout.activity_couple), this, supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.ilustris.base.R.menu.main_act_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.ilustris.base.R.id.wishes -> {
                coupleBinder.couple?.id?.let {
                    Routes.openWishes(this, it)
                    return false
                }
            }
            com.ilustris.base.R.id.wedDate -> {
                coupleBinder.couple?.id?.let {
                    Routes.openEvents(this, it)
                    return false
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}