package com.ilustris.couple.presenter

import com.ilustris.couple.bean.CoupleUser
import com.ilustris.couple.model.CoupleUserModel
import com.silent.ilustriscore.core.model.DTOMessage
import com.silent.ilustriscore.core.presenter.BasePresenter
import com.silent.ilustriscore.core.utilities.ErrorType
import com.silent.ilustriscore.core.view.BaseView

class CoupleUserPresenter(override val view: BaseView<CoupleUser>) : BasePresenter<CoupleUser>() {
    override val model = CoupleUserModel(this)

    fun getUser(uid: String) {
        model.getSingleData(uid)
    }

    override fun modelCallBack(dtoMessage: DTOMessage) {
        super.modelCallBack(dtoMessage)
        dtoMessage.errorType?.let {
            if (it == ErrorType.DATANOTFOUND) {
                model.addData(
                        CoupleUser(
                                id = currentUser!!.uid,
                                name = currentUser!!.displayName!!,
                                profilePic = currentUser!!.photoUrl.toString()
                        ),
                        forcedID = currentUser!!.uid)
            }
        }

    }

}