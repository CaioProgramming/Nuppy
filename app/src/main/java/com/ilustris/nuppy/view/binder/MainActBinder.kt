package com.ilustris.nuppy.view.binder

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.nuppy.view.dialog.NewListDialog
import com.ilustris.nuppy.view.adapter.WeddingHeaderAdapter
import com.ilustris.nuppy.bean.WedingList
import com.ilustris.nuppy.databinding.ActivityMainBinding
import com.ilustris.nuppy.presenter.WeddingPresenter
import com.ilustris.nuppy.view.dialog.DisconnectedDialog
import com.silent.ilustriscore.core.model.DTOMessage
import com.silent.ilustriscore.core.utilities.OperationType
import com.silent.ilustriscore.core.view.BaseView

class MainActBinder(override val context: Context, override val viewBind: ActivityMainBinding) : BaseView<WedingList>() {

    override fun presenter() = WeddingPresenter(this)

    init {
        initView()
    }

    override fun initView() {
        presenter().loadData()
        viewBind.addItemToListCard.setOnClickListener {
            Toast.makeText(context, "New List View", Toast.LENGTH_SHORT).show()
            NewListDialog.newInstance { name, emoji ->
                presenter().saveData(WedingList(title = name, icon = emoji))
                Log.i(javaClass.simpleName, "saving new item $name, $emoji")
            }.show((context as AppCompatActivity).supportFragmentManager,"newListDialog")
        }
    }

    override fun getCallBack(dtoMessage: DTOMessage) {
        super.getCallBack(dtoMessage)
        if (dtoMessage.message == "Usuário desconectado") {
            DisconnectedDialog(context as AppCompatActivity).buildDialog()
        }
        if (dtoMessage.operationType == OperationType.DATA_SAVED) {
            Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun showListData(list: List<WedingList>) {
        super.showListData(list)
        viewBind.nuppyRecycler.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            adapter = WeddingHeaderAdapter(list)
        }

    }

}