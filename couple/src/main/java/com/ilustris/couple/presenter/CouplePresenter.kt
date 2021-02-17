package com.ilustris.couple.presenter

import com.google.firebase.firestore.CollectionReference
import com.ilustris.couple.bean.Couple
import com.ilustris.couple.model.CoupleModel
import com.silent.ilustriscore.core.presenter.BasePresenter
import com.silent.ilustriscore.core.view.BaseView

class CouplePresenter(override val view: BaseView<Couple>) : BasePresenter<Couple>() {
    override val model: CoupleModel = CoupleModel(this)

    fun findPartner() {
        currentUser?.let { model.findWeddingFromUID(it.uid) }
    }


    fun getPartnerReference(coupleID: String, uid: String): CollectionReference {
        return model.getUserReference(coupleID, uid)
    }

}