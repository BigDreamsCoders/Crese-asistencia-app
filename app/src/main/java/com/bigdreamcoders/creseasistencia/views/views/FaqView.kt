package com.bigdreamcoders.creseasistencia.views.views

import com.bigdreamcoders.creseasistencia.services.networkService.models.faq.Faq

interface FaqView {

    fun inflateView(list: ArrayList<Faq>)
    fun deleteAllChild()
    fun beginFetch()
    fun logout()
    fun error(msg: Int)
    fun finishFetch()

}