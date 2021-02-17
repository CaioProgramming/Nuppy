package com.ilustris.couple.view.binder

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.ilustris.animations.fadeOut
import com.ilustris.animations.slideInBottom
import com.ilustris.couple.R
import com.ilustris.couple.bean.Couple
import com.ilustris.couple.databinding.CoupleSetupDialogBinding
import com.ilustris.couple.presenter.CouplePresenter
import com.ilustris.couple.view.dialog.FindWeddingDialog
import com.silent.ilustriscore.core.model.DTOMessage
import com.silent.ilustriscore.core.utilities.OperationType
import com.silent.ilustriscore.core.view.BaseView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class CoupleSetupBinder(override val context: Context, override val viewBind: CoupleSetupDialogBinding,
                        val dialog: DialogFragment) : BaseView<Couple>() {
    var wedding = Couple()

    override fun presenter() = CouplePresenter(this)

    init {
        initView()
    }

    override fun initView() {
        viewBind.run {
            presenter().currentUser?.let {
                wedding.partners.add(it.uid)
                Glide.with(context).load(it.photoUrl).into(userInfo.userpic)
                userInfo.username.text = it.displayName
            }
            marriageDate.setOnClickListener {
                openDatePicker()
            }
            relationshipDate.setOnClickListener {
                openDatePicker(false)
            }
            saveWeddingInfoButton.setOnClickListener {
                presenter().saveData(wedding)
            }
            weddingAlreadyButton.setOnClickListener {
                FindWeddingDialog.showWeddingFinder(dialog.childFragmentManager)
            }

        }
    }

    override fun onLoading() {
        super.onLoading()
        viewBind.saveWeddingInfoButton.text = ""
        viewBind.loading.slideInBottom()
        viewBind.loading.playAnimation()
    }

    override fun onLoadFinish() {
        super.onLoadFinish()
        viewBind.loading.run {
            cancelAnimation()
            fadeOut()
        }
    }

    override fun getCallBack(dtoMessage: DTOMessage) {
        super.getCallBack(dtoMessage)
        if (dtoMessage.operationType == OperationType.DATA_SAVED) {
            viewBind.saveWeddingInfoButton.run {
                text = "Dados salvos com sucesso"
                viewBind.saveWeddingInfoButton.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                                context,
                                R.color.material_blue500
                        )
                )
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            GlobalScope.launch {
                delay(3000)
                dialog.dismiss()
            }
        }
    }

    private fun openDatePicker(isMarriageDate: Boolean = true) {
        val datenow = Calendar.getInstance().time
        val calendar = GregorianCalendar()
        calendar.time = datenow
        DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    if (isMarriageDate) {
                        wedding.weddingDate = calendar.time

                        viewBind.marriageDateText.text = wedding.htmlWeddingDate()

                    } else {
                        wedding.relationshipDate = calendar.time
                        viewBind.relationshipDateText.text = wedding.htmlRelationShipDate()
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener {
                checkDates()
            }
        }.show()
    }

    private fun checkDates() {
        viewBind.saveWeddingInfoButton.isEnabled = (wedding.weddingDate != null && wedding.relationshipDate != null)
    }

}