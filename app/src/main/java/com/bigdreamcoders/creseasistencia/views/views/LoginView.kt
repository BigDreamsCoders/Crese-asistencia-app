package com.bigdreamcoders.creseasistencia.views.views

interface LoginView {

    fun loginEmptyId()
    fun loginEmptyPassword()
    fun loginSuccess()
    fun loginError(message:String)
    fun loginPerform()
    fun loginSaveToken(token:String?)
    fun loginDefineEmailOrUsername(id:String):Boolean
    fun loginSavePreference()
}