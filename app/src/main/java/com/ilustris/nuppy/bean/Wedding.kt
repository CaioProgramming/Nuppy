package com.ilustris.nuppy.bean

import com.silent.ilustriscore.core.bean.BaseBean
import java.util.*

data class Wedding(override var id: String, val husband: String, val wife: String, val weddingDate: Date) : BaseBean(id)