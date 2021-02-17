package com.ilustris.nuppy.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.creat.motiv.utilities.RC_SIGN_IN
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.ilustris.animations.slideInBottom
import com.ilustris.base.Routes
import com.ilustris.nuppy.R
import com.silent.ilustriscore.core.utilities.ColorUtils
import com.silent.ilustriscore.core.utilities.showSnackBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (userLogged()) {
            Routes.showCouple(this)
        } else {
            signIn()
        }

        loginButton.setOnClickListener {
            signIn()
        }

    }

    private fun userLogged(): Boolean = FirebaseAuth.getInstance().currentUser != null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                finish()
            } else {
                showSnackBar(context = this,
                        message = "Ocorreu um erro ao realizar o login tente novamente",
                        backColor = ContextCompat.getColor(this, ColorUtils.ERROR),
                        rootView = mainContainer
                )
                loginButton.slideInBottom()

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