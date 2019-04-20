package com.bigdreamcoders.creseasistencia.views.views

import com.bigdreamcoders.creseasistencia.services.NetworkService.models.videos.Videos

interface VideosView {

    fun updateList(list: ArrayList<Videos>)
    fun logout()
    fun beginFetch()
    fun error(msg:Int)
    fun updateItemCount(count: Int)
    fun finishFetch()
}