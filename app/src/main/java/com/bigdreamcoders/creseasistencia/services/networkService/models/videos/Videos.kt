package com.bigdreamcoders.creseasistencia.services.networkService.models.videos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Videos(
    val _id: String,
    val name: String,
    val URL: String,
    val sourceType: String,
    val dateCreated: String,
    val keywords: ArrayList<String>,
    val category: String,
    val reachFactor: Int,
    val viewFactor: Int,
    val shareFactor: Int,
    val creator: String
) : Parcelable

data class VideosResponse(
    val count: Int,
    val videos: ArrayList<Videos>
)