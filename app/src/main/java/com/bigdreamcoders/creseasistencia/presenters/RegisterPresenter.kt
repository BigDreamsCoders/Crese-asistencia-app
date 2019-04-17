package com.bigdreamcoders.creseasistencia.presenters

import com.bigdreamcoders.creseasistencia.pojos.UserRegister

interface RegisterPresenter {

    fun performRegister(user:UserRegister)

}