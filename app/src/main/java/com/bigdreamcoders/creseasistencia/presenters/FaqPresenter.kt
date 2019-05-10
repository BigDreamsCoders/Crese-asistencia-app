package com.bigdreamcoders.creseasistencia.presenters

interface FaqPresenter {

    fun sendEmail(auth:String, email:String, name:String, content:String)

}