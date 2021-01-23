package com.ilustris.nuppy.bean

import com.silent.ilustriscore.core.bean.BaseBean

data class WedingList(override var id: String = "",
                      val title: String = "",
                      val icon: String = "",
                      val items: ArrayList<WeddingItem> = ArrayList()) : BaseBean(id) {
}