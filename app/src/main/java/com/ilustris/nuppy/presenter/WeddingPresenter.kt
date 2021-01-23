package com.ilustris.nuppy.presenter

import com.ilustris.nuppy.bean.WedingList
import com.ilustris.nuppy.model.WeddingModel
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter
import com.silent.ilustriscore.core.view.BaseView

class WeddingPresenter(override val view: BaseView<WedingList>) : BasePresenter<WedingList>() {
    override val model = WeddingModel(this)

}