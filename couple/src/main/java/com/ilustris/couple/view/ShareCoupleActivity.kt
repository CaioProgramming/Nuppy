package com.ilustris.couple.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilustris.base.COUPLE_ID_BUNDLE
import com.ilustris.couple.R
import com.silent.ilustriscore.core.utilities.showSnackBar
import kotlinx.android.synthetic.main.activity_share_couple.*

class ShareCoupleActivity : AppCompatActivity(R.layout.activity_share_couple) {

    private fun getCoupleID() = intent.getSerializableExtra(COUPLE_ID_BUNDLE) as? String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCoupleID()?.let {
            coupleIDText.text = it
            coupleIDText.setOnClickListener { v ->
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Id de casamento", it)
                clipboard.setPrimaryClip(clip)
                showSnackBar(this, "código do casamento copiado com sucesso", Color.WHITE, Color.RED, v)
            }
            shareWeddingButton.setOnClickListener { _ ->
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/pain"
                    putExtra(Intent.EXTRA_SUBJECT, "Nuppy")
                    putExtra(Intent.EXTRA_TEXT, it)
                    startActivity(Intent.createChooser(this, "Compartilhar código de casamento"))

                }
            }
        }
    }

    companion object {
        fun launchShareCouple(context: Context, coupleID: String) {
            context.startActivity(Intent(context, ShareCoupleActivity::class.java).apply {
                putExtra(COUPLE_ID_BUNDLE, coupleID)
            })
        }

    }
}