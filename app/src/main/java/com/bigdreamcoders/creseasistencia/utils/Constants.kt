package com.bigdreamcoders.creseasistencia.utils

import com.bigdreamcoders.creseasistencia.R
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
    const val SI_LIST_FAQ="VIDEOS LIST"
    const val SI_FILTER="filter"

    const val MANUAL_SPINNER_ALPHA= R.string.spinner_alpha
    const val MANUAL_SPINNER_DATE=R.string.spinner_date
    const val MANUAL_SPINNER_TYPE=R.string.spinner_type

    const val MATERIAL_TYPE_MANUAL=R.string.manual_categories
    const val MATERIAL_TYPE_VIDEO=R.string.video_categories
    const val MATERIAL_TYPE_QUESTION=R.string.faq_title

    const val CATEGORY_CCTV="cctv"
    const val CATEGORY_CAMARAS="cámara wifi"
    const val CATEGORY_GPS="gps"
    const val CATEGORY_AC="control de acceso"

    const val REQUEST_QUERY_SEARCH="search"
    const val REQUEST_QUERY_CATEGORY="category"
    const val REQUEST_QUERY_EMAIL="email"
    const val REQUEST_QUERY_NAME="name"
    const val REQUEST_QUERY_CONTENT="content"

    const val MATERIAL_TYPE_KEY="MATERIAL TYPE"
    const val MATERIAL_CATEGORY_KEY="MATERIAL CATEGORY"

    const val PDF_ACTION_SHARE=R.string.share
    const val PDF_ACTION_OPEN=R.string.pdf_download

    const val VIDEO_ACTION_SHARE=R.string.share
    const val VIDEO_ACTION_OPEN=R.string.video_watch

    const val PREFERENCES_THEME="dark_theme"
    const val PREFERENCES_NOTIFICATION="notifications"
    const val TOPIC="AllDevices"

}
