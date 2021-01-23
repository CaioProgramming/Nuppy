package com.ilustris.nuppy.view.binder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.ilustris.nuppy.view.dialog.NewLinkItemDialog
import com.ilustris.nuppy.view.adapter.WeddingItemAdapter
import com.ilustris.nuppy.bean.WeddingItem
import com.ilustris.nuppy.bean.WedingList
import com.ilustris.nuppy.databinding.HeaderRecyclerLayoutBinding
import com.ilustris.nuppy.presenter.WeddingPresenter
import com.silent.ilustriscore.core.view.BaseView

class ListHeadBinder(override val context: Context,
                     override val viewBind: HeaderRecyclerLayoutBinding,
                     val weddingList: WedingList) : BaseView<WedingList>() {
    override fun presenter() = WeddingPresenter(this)

    init {
        initView()
    }

    override fun initView() {
        viewBind.run {
            title.text = weddingList.title
            recyclerChild.adapter = WeddingItemAdapter(weddingList.items)
            addItemToListCard.setOnClickListener {
                NewLinkItemDialog.newInstance(onSaveItem = { newItem, newItemName ->
                    weddingList.items.add(WeddingItem(link = newItem, name = newItemName))
                    presenter().saveData(weddingList, weddingList.id)
                }).show((context as AppCompatActivity).supportFragmentManager,"newItemDialog")
            }
        }
    }


}