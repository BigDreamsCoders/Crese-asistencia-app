package com.bigdreamcoders.creseasistencia.services.networkService.models.faq

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Faq(val question: String, val answer: String) : Parcelable

data class FaqResponse(
    val count: String,
    val faqs: ArrayList<Faq>
)