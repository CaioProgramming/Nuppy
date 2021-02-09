package com.ilustris.nuppy.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ilustris.nuppy.bean.WeddingList
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter

class WeddingModel(override val presenter: BasePresenter<WeddingList>) : BaseModel<WeddingList>() {
    override val path = "Wedding"

    override fun deserializeDataSnapshot(dataSnapshot: DocumentSnapshot): WeddingList {
      return  dataSnapshot.toObject(WeddingList::class.java)?.apply {
          id = dataSnapshot.id
      }!!
    }

    override fun deserializeDataSnapshot(dataSnapshot: QueryDocumentSnapshot): WeddingList {
        return dataSnapshot.toObject(WeddingList::class.java).apply {
            id = dataSnapshot.id
        }
    }


}