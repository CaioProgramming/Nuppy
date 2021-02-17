package com.ilustris.wishes.view.binder

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilustris.base.Routes
import com.ilustris.couple.view.binder.CoupleHeaderBinder
import com.ilustris.wishes.bean.WishList
import com.ilustris.wishes.databinding.ActivityWishesBinding
import com.ilustris.wishes.presenter.WeddingPresenter
import com.ilustris.wishes.view.adapter.WeddingHeaderAdapter
import com.ilustris.wishes.view.dialog.NewListDialog
import com.silent.ilustriscore.core.model.DTOMessage
import com.silent.ilustriscore.core.utilities.OperationType
import com.silent.ilustriscore.core.utilities.showSnackBar
import com.silent.ilustriscore.core.view.BaseView

class WishesActBinder(override val context: Context, override val viewBind: ActivityWishesBinding, val coupleID: String) : BaseView<WishList>() {

    override fun presenter() = WeddingPresenter(this)
    private var headerAdapter: WeddingHeaderAdapter? = null

    init {
        initView()
    }

    override fun initView() {
        presenter().findPreciseData(coupleID, "coupleID")
        CoupleHeaderBinder(context, viewBind.coupleHeader).initView()
    }


    override fun getCallBack(dtoMessage: DTOMessage) {
        super.getCallBack(dtoMessage)
        if (dtoMessage.message == "Usuário desconectado") {
            Routes.showCouple(context)
        }
        if (dtoMessage.operationType == OperationType.DATA_SAVED) {
            showSnackBar(context, "Nova lista adicionada", rootView = viewBind.root)
        }

    }

    override fun showListData(list: List<WishList>) {
        super.showListData(list)
        if (headerAdapter == null) {
            headerAdapter = WeddingHeaderAdapter(list.sortedBy { it.title }, coupleID) {
                NewListDialog.newInstance { wedList ->
                    wedList.apply {
                        this.coupleID = this@WishesActBinder.coupleID
                    }
                    presenter().saveData(wedList)
                }.show((context as AppCompatActivity).supportFragmentManager, "newListDialog")
            }
            viewBind.nuppyRecycler.run {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = headerAdapter
            }
        } else {
            headerAdapter?.updateWeddingList(list)
        }

    }

}