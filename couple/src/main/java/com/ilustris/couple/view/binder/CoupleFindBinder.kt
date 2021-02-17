package com.ilustris.couple.view.binder

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.ilustris.animations.fadeIn
import com.ilustris.animations.fadeOut
import com.ilustris.animations.slideInBottom
import com.ilustris.couple.bean.Couple
import com.ilustris.couple.databinding.CoupleFindDialogBinding
import com.ilustris.couple.presenter.CouplePresenter
import com.silent.ilustriscore.core.view.BaseView

class CoupleFindBinder(override val context: Context, override val viewBind: CoupleFindDialogBinding) : BaseView<Couple>() {

    override fun presenter() = CouplePresenter(this)

    override fun initView() {
        viewBind.run {
            coupleIDEditText.addTextChangedListener {
                if (coupleIDEditText.text.count() == 20) {
                    presenter().loadSingleData(coupleIDEditText.text.toString())
                }
            }
        }
    }

    override fun showData(data: Couple) {
        super.showData(data)
        viewBind.coupleInfo.run {
            this.coupleCollapsetoolbar.title = data.name
            this.coupleCollapsetoolbar.subtitle = data.htmlRelationShipDate()
            marriageText.text = data.htmlWeddingDate()
            coupleAppbar.slideInBottom()

        }
        presenter().currentUser?.let {
            viewBind.confirmWedding.run {
                setOnClickListener { _ ->
                    data.partners.add(it.uid)
                    presenter().updateData(data)
                }
                fadeIn()
            }

        }

    }

    override fun onLoadFinish() {
        super.onLoadFinish()
        viewBind.loading.run {
            cancelAnimation()
            fadeOut()
        }
    }

    override fun onLoading() {
        super.onLoading()
        viewBind.loading.fadeIn()
    }
}