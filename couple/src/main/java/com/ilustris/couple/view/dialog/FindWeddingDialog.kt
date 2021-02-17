package com.ilustris.couple.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ilustris.couple.R
import com.ilustris.couple.databinding.CoupleFindDialogBinding
import com.ilustris.couple.view.binder.CoupleFindBinder

class FindWeddingDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.couple_find_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoupleFindBinder(view.context, CoupleFindDialogBinding.bind(view)).initView()
    }

    companion object {
        fun showWeddingFinder(fragmentManager: FragmentManager) {
            FindWeddingDialog().show(fragmentManager, "FINDWEDDINGFRAGMENT")
        }
    }

}