package com.ilustris.nuppy.bean

import android.os.Parcelable
import com.silent.ilustriscore.core.bean.BaseBean
import kotlinx.android.parcel.Parcelize


const val WEDDING_ITEM_COMPANION_BUNDLE = "WEDDING ITEM BUNDLE"

data class WeddingItem(var wedId: String = "",
                       val whoAdded: String = "",
                       val checked: Boolean = false,
                       val link: String = "") :BaseBean(wedId)