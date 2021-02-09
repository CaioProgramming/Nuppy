package com.ilustris.nuppy.presenter

import com.ilustris.nuppy.bean.WeddingList
import com.ilustris.nuppy.model.WeddingModel
import com.silent.ilustriscore.core.presenter.BasePresenter
import com.silent.ilustriscore.core.view.BaseView

class WeddingPresenter(override val view: BaseView<WeddingList>) : BasePresenter<WeddingList>() {
    override val model = WeddingModel(this)

    fun deleteList(id: String) {
        model.deleteData(id)
    }

}