package com.bigdreamcoders.creseasistencia.views.views

interface LoginView {

    fun loginEmptyId(message:String)
    fun loginEmptyPassword(message:String)
    fun loginSuccess()
    fun loginError(message:String)
    fun loginPerform()
    fun loginSaveToken(token:String)
    fun loginDefineEmailOrUsername(id:String):Boolean
}