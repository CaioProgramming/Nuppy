package com.ilustris.nuppy.bean

import com.silent.ilustriscore.core.bean.BaseBean


const val WEDDING_ITEM_COMPANION_BUNDLE = "WEDDING ITEM BUNDLE"

data class WishItem(var wedId: String = "",
                    val whoAdded: String = "",
                    val checked: Boolean = false,
                    val link: String = "") : BaseBean(wedId)