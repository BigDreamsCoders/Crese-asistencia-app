package com.bigdreamcoders.creseasistencia.utils

import java.util.regex.Pattern.compile

object Constants {
    /**
     * return a regex to verify if is and email or not
    * */
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

    const val SP_NAME:String="PREFERENCES"
    const val SP_TOKEN:String="TOKEN"
    const val SP_STAY_ACTIVE:String="ACTIVE"
}
