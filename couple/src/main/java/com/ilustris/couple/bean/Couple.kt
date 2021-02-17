package com.ilustris.couple.bean

import android.text.Html
import android.text.Spanned
import com.silent.ilustriscore.core.bean.BaseBean
import com.silent.ilustriscore.core.utilities.DateFormats
import com.silent.ilustriscore.core.utilities.formatDate
import java.util.*
import kotlin.collections.ArrayList

data class Couple(
        override var id: String = "",
        var name: String = "",
        var relationshipDate: Date? = null,
        var weddingDate: Date? = null,
        var partners: ArrayList<String> = ArrayList(),
) : BaseBean(id) {
    fun htmlWeddingDate(): Spanned = Html.fromHtml("Casamento em <b>${weddingDate?.formatDate(DateFormats.DD_OF_MM_FROM_YYYY.format) ?: "-"}</b>")
    fun htmlRelationShipDate(): Spanned = Html.fromHtml("Juntos desde <b>${relationshipDate?.formatDate(DateFormats.DD_OF_MM_FROM_YYYY.format) ?: "-"}</b>")
}