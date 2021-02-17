package com.ilustris.couple.view.binder

import android.content.Context
import com.bumptech.glide.Glide
import com.ilustris.animations.popIn
import com.ilustris.base.databinding.WeddingUserLayoutBinding
import com.ilustris.couple.bean.CoupleUser
import com.ilustris.couple.presenter.CoupleUserPresenter
import com.silent.ilustriscore.core.view.BaseView

class UserHeaderBinder(override val context: Context, override val viewBind: WeddingUserLayoutBinding, val uid: String) : BaseView<CoupleUser>() {

    override fun presenter() = CoupleUserPresenter(this)

    override fun initView() {
        presenter().currentUser?.let {
            presenter().getUser(it.uid)
        }
    }

    override fun showData(data: CoupleUser) {
        super.showData(data)
        viewBind.run {
            Glide.with(context).load(data.profilePic).into(userpic)
            userpic.popIn()
        }
    }

}