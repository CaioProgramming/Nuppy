package com.ilustris.couple.bean

import com.silent.ilustriscore.core.bean.BaseBean

data class CoupleUser(override var id: String = "",
                      var name: String = "",
                      var profilePic: String = "") : BaseBean(id)