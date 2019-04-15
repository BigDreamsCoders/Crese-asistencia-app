package com.bigdreamcoders.creseasistencia.views.views

interface RegisterView {

    fun registerEmptyUsername()
    fun registerEmptyEmail()
    fun registerEmptyPassword()
    fun registerSuccess()
    fun registerError(message:String)
    fun registerPerform()

}