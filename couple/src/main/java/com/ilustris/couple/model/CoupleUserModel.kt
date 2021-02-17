package com.ilustris.couple.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ilustris.couple.bean.CoupleUser
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter

class CoupleUserModel(override val presenter: BasePresenter<CoupleUser>) : BaseModel<CoupleUser>() {
    override val path = "User"

    override fun deserializeDataSnapshot(dataSnapshot: DocumentSnapshot): CoupleUser {
        return dataSnapshot.toObject(CoupleUser::class.java)?.apply {
            id = dataSnapshot.id
        }!!
    }

    override fun deserializeDataSnapshot(dataSnapshot: QueryDocumentSnapshot): CoupleUser {
        return dataSnapshot.toObject(CoupleUser::class.java).apply {
            id = dataSnapshot.id
        }
    }


}