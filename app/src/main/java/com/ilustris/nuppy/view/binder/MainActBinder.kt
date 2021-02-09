package com.ilustris.nuppy.view.binder

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.nuppy.bean.WeddingList
import com.ilustris.nuppy.view.dialog.NewListDialog
import com.ilustris.nuppy.view.adapter.WeddingHeaderAdapter
import com.ilustris.nuppy.databinding.ActivityMainBinding
import com.ilustris.nuppy.presenter.WeddingPresenter
import com.ilustris.nuppy.view.LoginActivity
import com.silent.ilustriscore.core.model.DTOMessage
import com.silent.ilustriscore.core.utilities.OperationType
import com.silent.ilustriscore.core.view.BaseView

class MainActBinder(override val context: Context, override val viewBind: ActivityMainBinding) : BaseView<WeddingList>() {

    override fun presenter() = WeddingPresenter(this)
    private var headerAdapter: WeddingHeaderAdapter? = null
    init {
        initView()
    }

    override fun initView() {
        presenter().loadData()
        viewBind.addItemToListCard.setOnClickListener {
            NewListDialog.newInstance { wedList ->
                presenter().saveData(wedList)
            }.show((context as AppCompatActivity).supportFragmentManager,"newListDialog")
        }
    }

    private fun startLogin() {
        val i = Intent(context, LoginActivity::class.java)
        context.startActivity(i)
    }

    override fun getCallBack(dtoMessage: DTOMessage) {
        super.getCallBack(dtoMessage)
        if (dtoMessage.message == "Usuário desconectado") {
            startLogin()
        }
        if (dtoMessage.operationType == OperationType.DATA_SAVED) {
            Toast.makeText(context, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }

    }



    override fun showListData(list: List<WeddingList>) {
        super.showListData(list)
        if (headerAdapter == null) {
            headerAdapter = WeddingHeaderAdapter(list.sortedBy {
                it.title
            })
            viewBind.nuppyRecycler.run {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
                adapter = headerAdapter
            }
        } else {
            headerAdapter?.updateWeddingList(list)
        }

    }

}