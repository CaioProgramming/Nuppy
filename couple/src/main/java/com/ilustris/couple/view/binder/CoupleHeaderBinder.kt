package com.ilustris.couple.view.binder

import android.content.Context
import com.ilustris.base.databinding.CoupleHeaderLayoutBinding
import com.ilustris.couple.bean.Couple
import com.ilustris.couple.presenter.CouplePresenter
import com.silent.ilustriscore.core.view.BaseView

class CoupleHeaderBinder(override val context: Context,
                         override val viewBind: CoupleHeaderLayoutBinding,
                         val couple: Couple? = null) : BaseView<Couple>() {

    override fun presenter() = CouplePresenter(this)

    init {
        initView()
    }

    override fun initView() {
        if (couple == null) {
            presenter().findPartner()
        } else {
            setupCouple(couple)
        }
    }

    override fun showListData(list: List<Couple>) {
        super.showListData(list)
        if (list.isNotEmpty()) {
            val couple = list[0]
            setupCouple(couple)
        }
    }

    private fun setupCouple(couple: Couple) {
        viewBind.coupleCollapsetoolbar.title = couple.name
        viewBind.coupleCollapsetoolbar.subtitle = couple.htmlRelationShipDate()
        viewBind.marriageText.text = couple.htmlWeddingDate()
    }
}