package com.bigdreamcoders.creseasistencia.services.networkService.models.manuals

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Manuals(
    val _id: String,
    val name: String,
    val URL: String,
    val sourceType: String,
    val dateCreated: String,
    val keywords: ArrayList<String>,
    val category: String,
    val downloadFactor: Int,
    val viewFactor: Int,
    val shareFactor: Int,
    val creator: String
) : Parcelable

data class ManualsResponse(
    val count: Int,
    val manuals: ArrayList<Manuals>,
    val message: String
)