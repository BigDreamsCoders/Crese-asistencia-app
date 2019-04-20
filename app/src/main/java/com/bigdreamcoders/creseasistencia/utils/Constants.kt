package com.bigdreamcoders.creseasistencia.utils

import java.util.regex.Pattern.compile

object Constants {
    private val usernameAtRegex=compile("^((?!@).)*\$")
    private val usernameDotComRegex=compile("^((?!\\.com).)*\$")
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

    private fun String.hasAtChar():Boolean{
        return usernameAtRegex.matcher(this).matches()
    }
    fun hasAt(username:String):Boolean{
        return !username.hasAtChar()
    }

    private fun String.hasDotComString():Boolean{
        return usernameDotComRegex.matcher(this).matches()
    }
    fun hasDotCom(username:String):Boolean{
        return !username.hasDotComString()
    }

    const val SP_NAME:String="PREFERENCES"
    const val SP_TOKEN:String="TOKEN"
    const val SP_STAY_ACTIVE:String="ACTIVE"

    const val SI_TITLE="FRAGMENT_TITLE"
    const val SI_LIST_MANUALS="MANUALS LIST"
    const val SI_LIST_VIDEOS="VIDEOS LIST"
    const val SI_PDF_LOAD="PDF"

    const val MANUAL_SPINNER_ALPHA="Alphabetic sort"
    const val MANUAL_SPINNER_DATE="Date sort"
    const val MANUAL_SPINNER_TYPE="Type sort"

    const val MATERIAL_TYPE_MANUAL="MANUAL"
    const val MATERIAL_TYPE_VIDEO="VIDEO"
    const val MATERIAL_TYPE_QUESTION="QUESTIONS"

    const val CATEGORY_CCTV="cctv"
    const val CATEGORY_CAMARAS="cámara wifi"
    const val CATEGORY_GPS="gps"
    const val CATEGORY_AC="control de acceso"

    const val REQUEST_QUERY_SEARCH="search"
    const val REQUEST_QUERY_CATEGORY="category"

    const val MATERIAL_TYPE_KEY="MATERIAL TYPE"
    const val MATERIAL_CATEGORY_KEY="MATERIAL CATEGORY"

    const val PDF_ACTION_SHARE="Share"
    const val PDF_ACTION_OPEN="Open"

}
