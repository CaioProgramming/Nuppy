package com.ilustris.couple.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ilustris.couple.R
import com.ilustris.couple.databinding.CoupleSetupDialogBinding
import com.ilustris.couple.view.binder.CoupleSetupBinder

class CoupleSetupDialog : BottomSheetDialogFragment() {

    lateinit var coupleSetupFargmentBinding: CoupleSetupDialogBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setStyle(STYLE_NO_TITLE, R.style.Theme_Nuppy)
        return inflater.inflate(R.layout.couple_setup_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coupleSetupFargmentBinding = CoupleSetupDialogBinding.bind(view)
        CoupleSetupBinder(requireContext(), coupleSetupFargmentBinding, this@CoupleSetupDialog)
    }


}