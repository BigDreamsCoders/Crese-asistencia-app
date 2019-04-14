package com.bigdreamcoders.creseasistencia.models

import android.text.TextUtils
import com.bigdreamcoders.creseasistencia.presenters.LoginPresenter
import com.bigdreamcoders.creseasistencia.views.views.LoginView

class LoginPresenterImp(private val mLoginView: LoginView) : LoginPresenter {

    override fun performLogin(id: String, pass: String) {
        when {
            TextUtils.isEmpty(id) -> mLoginView.loginEmptyId(id)
            TextUtils.isEmpty(pass) -> mLoginView.loginEmptyPassword(pass)
            else->if (mLoginView.loginDefineEmailOrUsername(id)) {
                performLoginEmail(id, pass)
            } else {
                performLoginUsername(id, pass)
            }
        }
    }

    private fun performLoginEmail(email: String, pass: String) {

    }

    private fun performLoginUsername(username: String, pass: String) {

    }

}