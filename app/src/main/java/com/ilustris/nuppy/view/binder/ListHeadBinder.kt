package com.ilustris.nuppy.view.binder

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.animations.slideOutBottom
import com.ilustris.nuppy.R
import com.ilustris.nuppy.bean.ListType
import com.ilustris.nuppy.view.dialog.NewLinkItemDialog
import com.ilustris.nuppy.view.adapter.WeddingItemAdapter
import com.ilustris.nuppy.bean.WeddingItem
import com.ilustris.nuppy.bean.WeddingList
import com.ilustris.nuppy.databinding.HeaderRecyclerLayoutBinding
import com.ilustris.nuppy.presenter.WeddingPresenter
import com.silent.ilustriscore.core.view.BaseView

class ListHeadBinder(override val context: Context,
                     override val viewBind: HeaderRecyclerLayoutBinding,
                     private val weddingList: WeddingList
) : BaseView<WeddingList>() {
    override fun presenter() = WeddingPresenter(this)

    init {
        initView()
    }

    override fun initView() {
        viewBind.run {
            val itemCount =  if(weddingList.items.size > 1 && weddingList.listType != ListType.TEXT_LIST) 2 else 1
            title.text = weddingList.title
            recyclerChild.apply {
                if (weddingList.listType == ListType.TEXT_LIST) viewBind.root.setBackgroundColor(ContextCompat.getColor(context, R.color.lblack))
                adapter = WeddingItemAdapter(weddingList.listType,weddingList.items)
                layoutManager = GridLayoutManager(context,itemCount,RecyclerView.VERTICAL,false)
            }

            addItemToListCard.setOnClickListener {
                NewLinkItemDialog.newInstance(weddingList.listType,onSaveItem = { newItem ->
                    if (!weddingList.itemExists(newItem)) {
                        weddingList.items.add(WeddingItem(link = newItem,whoAdded = presenter().currentUser!!.uid))
                        presenter().saveData(weddingList, weddingList.id)
                    }
                }).show((context as AppCompatActivity).supportFragmentManager,"newItemDialog")
            }
            titleView.setOnLongClickListener {
                presenter().deleteList(weddingList.id)
                false
            }
            if (weddingList.items.count() > 4) {
                addItemToListCard.setOnClickListener {
                    Toast.makeText(context, "Ver mais items", Toast.LENGTH_SHORT).show()
                }
            }
            viewBind.root.slideOutBottom()
        }
    }
}