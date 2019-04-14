package com.bigdreamcoders.creseasistencia.utils

import java.util.regex.Pattern.compile

object Constants {
    private val emailRegex = compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )
    private fun String.isEmail():Boolean{
        return emailRegex.matcher(this).matches()
    }
    /**
    * @param id the id to be verify if is email or username
     * @return return true for email, false for username
    * */
    fun isEmailOrUsername(id:String):Boolean{
        return id.isEmail()
    }
}
