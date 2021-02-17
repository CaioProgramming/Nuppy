package com.ilustris.wishes.presenter

import com.ilustris.wishes.bean.WishList
import com.ilustris.wishes.model.WishesModel
import com.silent.ilustriscore.core.presenter.BasePresenter
import com.silent.ilustriscore.core.view.BaseView

class WeddingPresenter(override val view: BaseView<WishList>) : BasePresenter<WishList>() {
    override val model = WishesModel(this)

    fun deleteList(id: String) {
        model.deleteData(id)
    }






}