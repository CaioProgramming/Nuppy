package com.ilustris.nuppy.bean

import com.silent.ilustriscore.core.bean.BaseBean

data class WeddingItem(var wedId: String = "",
                       val name: String = "",
                       val checked: Boolean = false,
                       val link: String = "") : BaseBean(wedId)