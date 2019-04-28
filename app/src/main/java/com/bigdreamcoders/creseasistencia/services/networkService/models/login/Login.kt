package com.bigdreamcoders.creseasistencia.services.networkService.models.login

data class Login(var email:String="", var account:String="", val password:String="")

data class LoginResponse(val token:String, val message:String)