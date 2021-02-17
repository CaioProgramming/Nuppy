package com.ilustris.couple.model

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ilustris.couple.bean.Couple
import com.silent.ilustriscore.core.model.BaseModel
import com.silent.ilustriscore.core.presenter.BasePresenter

class CoupleModel(override val presenter: BasePresenter<Couple>) : BaseModel<Couple>() {
    override val path: String = "Couple"

    fun findWeddingFromUID(id: String) {
        db().whereArrayContains("partners", id).addSnapshotListener(this)
    }

    fun getUserReference(coupleID: String, uid: String): CollectionReference {
        return FirebaseFirestore.getInstance().collection("$path/$coupleID/partners/$uid")
    }


    override fun deserializeDataSnapshot(dataSnapshot: DocumentSnapshot): Couple {
        return dataSnapshot.toObject(Couple::class.java).apply {
            this!!.id = dataSnapshot.id
        }!!
    }

    override fun deserializeDataSnapshot(dataSnapshot: QueryDocumentSnapshot): Couple {
        return dataSnapshot.toObject(Couple::class.java).apply {
            this.id = dataSnapshot.id
        }
    }
}