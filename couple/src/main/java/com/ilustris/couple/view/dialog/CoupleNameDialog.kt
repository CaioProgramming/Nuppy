package com.ilustris.couple.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ilustris.couple.R
import com.ilustris.couple.databinding.CoupleNameDialogBinding

class CoupleNameDialog : BottomSheetDialogFragment() {

    lateinit var coupleNameDialogBinding: CoupleNameDialogBinding
    var couplename: String? = null
    var updateNameCallback: ((String) -> Unit)? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.couple_name_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coupleNameDialogBinding = CoupleNameDialogBinding.bind(view)
        coupleNameDialogBinding.run {
            coupleName.run {
                hint = couplename ?: ""
                addTextChangedListener {
                    couplename = coupleName.text.toString()
                    saveItemButton.isEnabled = !it.isNullOrEmpty()
                }
            }
            saveItemButton.setOnClickListener {
                couplename?.let { it1 ->
                    updateNameCallback?.invoke(it1)
                    dismiss()
                }
            }
        }

    }

    companion object {
        fun showCoupleNameEdit(coupleName: String, updateNameCallback: (String) -> Unit, fragmentManager: FragmentManager) {
            CoupleNameDialog()
                    .apply {
                        this.couplename = coupleName
                        this.updateNameCallback = updateNameCallback
                    }
                    .show(fragmentManager, "COUPLE_NAME_FRAGMENT")
        }
    }

}