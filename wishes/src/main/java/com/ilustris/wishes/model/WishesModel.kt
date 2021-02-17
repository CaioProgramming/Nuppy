package com.ilustris.wishes.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ilustris.wishes.bean.WishList
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter

class WishesModel(override val presenter: BasePresenter<WishList>) : BaseModel<WishList>() {

    override fun deserializeDataSnapshot(dataSnapshot: DocumentSnapshot): WishList {
        return dataSnapshot.toObject(WishList::class.java)?.apply {
            id = dataSnapshot.id
        }!!
    }

    override fun deserializeDataSnapshot(dataSnapshot: QueryDocumentSnapshot): WishList {
        return dataSnapshot.toObject(WishList::class.java).apply {
            id = dataSnapshot.id
        }
    }


    override val path = "Wishes"


}