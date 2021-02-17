package com.ilustris.wishes.bean

import com.ilustris.nuppy.bean.WishItem
import com.silent.ilustriscore.core.bean.BaseBean


const val WEDDING_LIST_COMPANION_BUNDLE = "WEDDING LIST BUNDLE"

enum class ListType {
    TEXT_LIST, GRID_LIST
}

data class WishList(override var id: String = "",
                    val title: String = "",
                    val listType: ListType = ListType.GRID_LIST,
                    var coupleID: String = "",
                    val items: ArrayList<WishItem> = ArrayList()) : BaseBean(id) {

    fun itemExists(item: String): Boolean {
        items.forEach {
            return it.link == item
        }
        return false
    }

}