package com.ilustris.base

import android.content.Context
import android.content.Intent
import android.widget.Toast

const val COUPLE_ID_BUNDLE = "COUPLE_ID"

const val COUPLE_CLASS = "com.ilustris.couple.view.CoupleActivity"
const val EVENTS_CLASS = "com.ilustris.events.WeddingEventsActivity"
const val WISHES_CLASS = "com.ilustris.wishes.view.WishesActivity"

class Routes {

    companion object {
        fun openWishes(context: Context, coupleID: String) {

            context.startActivity(
                    Intent(context,
                            Class.forName(WISHES_CLASS)).apply {
                        putExtra(COUPLE_ID_BUNDLE, coupleID)
                    }
            )
        }

        fun showCouple(context: Context) {
            context.startActivity(Intent(context,
                    Class.forName(COUPLE_CLASS)))
        }

        fun openEvents(context: Context, coupleID: String) {
            Toast.makeText(context, "Módulo em construção", Toast.LENGTH_SHORT).show()
            /*context.startActivity(Intent(context, Class.forName(EVENTS_CLASS)).apply {
                    putExtra(COUPLE_ID_BUNDLE,coupleID)
            })*/
        }

    }


}