package com.ilustris.nuppy.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.creat.motiv.utilities.RC_SIGN_IN
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ilustris.nuppy.R
import com.ilustris.nuppy.databinding.AnimatedDialogBinding
import java.util.*

class DisconnectedDialog(val activity: Activity){



    var dialog: Dialog = Dialog(activity)

    fun buildDialog() {
        val animatedDialogBinding: AnimatedDialogBinding =  DataBindingUtil.inflate(LayoutInflater.from(activity),R.layout.animated_dialog,null,false)
        animatedDialogBinding.run {
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            okButton.setOnClickListener {
                signIn()
            }
        }
        dialog.setContentView(animatedDialogBinding.root)
        dialog.show()
    }




    private fun signIn() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            val providers = listOf(
                //AuthUI.IdpConfig.FacebookBuilder().build(),
                //new AuthUI.IdpConfig.TwitterBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build())
            activity.startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                    .setLogo(R.mipmap.ic_launcher)
                    .setAvailableProviders(providers)
                    .setTheme(R.style.Theme_Nuppy)
                    .build(), RC_SIGN_IN
            )
        } else {
            dialog.dismiss()
        }
    }

}