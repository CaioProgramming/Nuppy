package com.ilustris.nuppy.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.creat.motiv.utilities.RC_SIGN_IN
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.ilustris.animations.fadeIn
import com.ilustris.animations.fadeOut
import com.ilustris.animations.popIn
import com.ilustris.nuppy.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        okButton.setOnClickListener {
            signIn()
        }
        if (user == null) {
            animatedIcon.fadeIn()
            message.fadeIn()
            okButton.popIn()
            loading.fadeOut()
        } else {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                finish()
            } else {
                if (response != null) {
                    Toast.makeText(
                        this,
                        "Ocorreu um erro ao fazer login tente novamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

    private fun signIn() {
        val providers = listOf(
            //AuthUI.IdpConfig.FacebookBuilder().build(),
            //new AuthUI.IdpConfig.TwitterBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build())
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setLogo(R.mipmap.ic_launcher)
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_Nuppy)
                .build(), RC_SIGN_IN
        )
    }

}