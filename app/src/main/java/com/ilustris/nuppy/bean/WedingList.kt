package com.ilustris.nuppy.bean

import com.silent.ilustriscore.core.bean.BaseBean


const val WEDDING_LIST_COMPANION_BUNDLE = "WEDDING LIST BUNDLE"

enum class ListType {
    TEXT_LIST, GRID_LIST
}

data class WeddingList(override var id: String = "",
                       val title: String = "",
                       val listType: ListType = ListType.GRID_LIST,
                       val items: ArrayList<WeddingItem> = ArrayList()) : BaseBean(id) {

                          fun itemExists(item: String) : Boolean {
                              items.forEach {
                                  return it.link == item
                              }
                             return  false
                          }

}