package com.ilustris.couple.view.binder

import android.content.Context
import com.bumptech.glide.Glide
import com.ilustris.animations.slideInBottom
import com.ilustris.couple.bean.CoupleUser
import com.ilustris.couple.databinding.CoupleUserInfoBinding
import com.ilustris.couple.presenter.CoupleUserPresenter
import com.silent.ilustriscore.core.view.BaseView

class CoupleUserInfoBinder(override val context: Context,
                           override val viewBind: CoupleUserInfoBinding) : BaseView<CoupleUser>() {
    override fun presenter() = CoupleUserPresenter(this)

    override fun initView() {
        presenter().currentUser?.let { presenter().getUser(it.uid) }
    }

    override fun showData(data: CoupleUser) {
        super.showData(data)
        viewBind.run {
            Glide.with(context).load(data.profilePic).into(userpic)
            username.text = data.name
            userpic.slideInBottom()
        }

    }

}