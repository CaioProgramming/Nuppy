package com.ilustris.nuppy.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ilustris.nuppy.bean.WedingList
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter

class WeddingModel(override val presenter: BasePresenter<WedingList>) : BaseModel<WedingList>() {
    override val path = "Wedding"

    override fun deserializeDataSnapshot(dataSnapshot: DocumentSnapshot): WedingList {
      return  dataSnapshot.toObject(WedingList::class.java)?.apply {
          id = dataSnapshot.id
      }!!
    }

    override fun deserializeDataSnapshot(dataSnapshot: QueryDocumentSnapshot): WedingList {
        return dataSnapshot.toObject(WedingList::class.java).apply {
            id = dataSnapshot.id
        }
    }


}