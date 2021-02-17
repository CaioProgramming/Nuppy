package com.ilustris.couple.view.binder

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.ilustris.animations.slideInBottom
import com.ilustris.couple.bean.Couple
import com.ilustris.couple.databinding.ActivityCoupleBinding
import com.ilustris.couple.presenter.CouplePresenter
import com.ilustris.couple.view.ShareCoupleActivity
import com.ilustris.couple.view.dialog.CoupleNameDialog
import com.ilustris.couple.view.dialog.CoupleSetupDialog
import com.silent.ilustriscore.core.utilities.gone
import com.silent.ilustriscore.core.utilities.visible
import com.silent.ilustriscore.core.view.BaseView
import java.util.*


class CoupleBinder(override val viewBind: ActivityCoupleBinding, override val context: Context, val fragmentManager: FragmentManager) : BaseView<Couple>() {

    var couple: Couple? = null

    override fun presenter(): CouplePresenter = CouplePresenter(this)

    init {
        initView()
    }

    override fun initView() {
        (context as AppCompatActivity).setSupportActionBar(viewBind.coupleHeader.coupleToolbar)
        presenter().findPartner()
    }


    override fun showListData(list: List<Couple>) {
        super.showListData(list)
        if (list.isEmpty()) {
            CoupleSetupDialog().show(fragmentManager, "WEDDING_SETUP_FRAGMENT")
        } else {
            val wedding = list[0]
            couple = wedding
            viewBind.coupleData.run {
                this.userInfo.root.gone()
                saveWeddingInfoButton.gone()
                marriageDateText.gone()
                relationshipDateText.gone()
                coupleName.gone()
                coupleSetupTitle.gone()

                marriageDate.run {
                    visible()
                    setOnClickListener {
                        openDatePicker(true, couple?.weddingDate ?: Date())
                    }
                }

                relationshipDate.run {
                    visible()
                    setOnClickListener {
                        openDatePicker(false, couple?.relationshipDate ?: Date())
                    }
                }

                coupleNameCard.run {
                    visible()
                    setOnClickListener {
                        CoupleNameDialog.showCoupleNameEdit(wedding.name, updateNameCallback = {
                            wedding.name = it
                            presenter().updateData(wedding)
                        }, fragmentManager)
                    }
                }

                weddingAlreadyButton.gone()

                shareCouple.run {
                    visible()
                    setOnClickListener {
                        ShareCoupleActivity.launchShareCouple(context, wedding.id)
                    }
                }

                viewBind.coupleHeader.coupleCollapsetoolbar.title = wedding.name
                viewBind.coupleHeader.coupleCollapsetoolbar.subtitle = wedding.htmlRelationShipDate()
                viewBind.coupleHeader.marriageText.text = wedding.htmlWeddingDate()
                if (coupleDataContainer.visibility == View.GONE) {
                    coupleDataContainer.slideInBottom()
                }

            }
            viewBind.logOut.setOnClickListener {
                (context as Activity).finish()
                FirebaseAuth.getInstance().signOut()
            }
        }
    }

    private fun openDatePicker(isMarriageDate: Boolean = true, currentDate: Date) {
        val calendar = GregorianCalendar()
        calendar.time = currentDate
        DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    if (isMarriageDate) {
                        couple?.weddingDate = calendar.time
                        presenter().updateData(couple!!)
                    } else {
                        couple?.relationshipDate = calendar.time
                        presenter().updateData(couple!!)
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}