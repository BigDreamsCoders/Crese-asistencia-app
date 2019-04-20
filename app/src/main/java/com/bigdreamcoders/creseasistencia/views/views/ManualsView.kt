package com.bigdreamcoders.creseasistencia.views.views

import com.bigdreamcoders.creseasistencia.services.NetworkService.models.manuals.Manuals

interface ManualsView {

    fun updateList(list: ArrayList<Manuals>)
    fun updateItemCount(count:Int)
    fun beginFetch()
    fun logout()
    fun error(msg:Int)
    fun finishFetch()
}