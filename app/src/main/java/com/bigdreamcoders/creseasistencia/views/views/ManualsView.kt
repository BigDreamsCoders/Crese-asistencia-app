package com.bigdreamcoders.creseasistencia.views.views

import com.bigdreamcoders.creseasistencia.services.networkService.models.manuals.Manuals

interface ManualsView {

    fun updateList(list: ArrayList<Manuals>)
    fun updateItemCount(count: Int)
    fun beginFetch()
    fun logout()
    fun hideKeyboard()
    fun error(msg: Int)
    fun finishFetch()
}