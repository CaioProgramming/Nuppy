package com.ilustris.base

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

    protected fun getCoupleID() = intent.getSerializableExtra(COUPLE_ID_BUNDLE) as? String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_act_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.couple -> {
                this.finish()
                return false
            }
            R.id.wishes -> {
                getCoupleID()?.let {
                    if (javaClass.name != WISHES_CLASS) {
                        Routes.openWishes(this, it)
                    }
                    return false
                }
            }
            R.id.wedDate -> {
                if (javaClass.name != EVENTS_CLASS) {
                    getCoupleID()?.let {
                        Routes.openEvents(this, it)
                        return false
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setToolbar() {
        val toolbar: Toolbar? = findViewById(R.id.coupleToolbar)
        toolbar?.let {
            setSupportActionBar(it)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setToolbar()
    }


}