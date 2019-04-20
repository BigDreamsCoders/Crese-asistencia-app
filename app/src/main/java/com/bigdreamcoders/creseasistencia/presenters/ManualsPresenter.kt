package com.bigdreamcoders.creseasistencia.presenters

import com.bigdreamcoders.creseasistencia.services.NetworkService.models.manuals.Manuals

interface ManualsPresenter {

    fun fetchManuals(token: String, word:String, category:String)

}