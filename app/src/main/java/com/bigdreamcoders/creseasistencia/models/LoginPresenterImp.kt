package com.bigdreamcoders.creseasistencia.models

import android.text.TextUtils
import android.util.Log
import com.bigdreamcoders.creseasistencia.presenters.LoginPresenter
import com.bigdreamcoders.creseasistencia.services.NetworkService.RequestService
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.login.Login
import com.bigdreamcoders.creseasistencia.views.views.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoginPresenterImp(private val mLoginView: LoginView) : LoginPresenter {

    private lateinit var disposable: Disposable

    private val service by lazy {
        RequestService.createLogin()
    }

    override fun performLogin(id: String, pass: String) {
        when {
            TextUtils.isEmpty(id) -> mLoginView.loginEmptyId()
            TextUtils.isEmpty(pass) -> mLoginView.loginEmptyPassword()
            else -> {
                mLoginView.loginPerform()
                val flag = mLoginView.loginDefineEmailOrUsername(id)
                val loginModel = if (flag) {
                    Login(
                        email = id,
                        password = pass
                    )
                } else {
                    Login(
                        account = id,
                        password = pass
                    )
                }
                disposable = service
                    .login(loginModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        run {
                            Log.d("FAIL", it.code().toString())
                            when {
                                it.code() == 200 -> {
                                    mLoginView.loginSuccess()
                                    mLoginView.loginSavePreference()
                                    mLoginView.loginSaveToken(it.body()?.token)
                                    mLoginView.loginSavePreference()
                                }
                                it.code() == 401 -> mLoginView.loginError("Authorization failed")
                                else -> mLoginView.loginError("An Error occurred, try again later")
                            }
                        }
                    }, {
                        run {
                            Log.d("FAIL", "XXXXXXXXXX")
                            if (it is SocketTimeoutException || it is UnknownHostException) {
                                mLoginView.loginError("Check your internet connection")
                            } else {
                                it.printStackTrace()
                                mLoginView.loginError("An error happen, try later")
                            }
                        }
                    })
            }
        }
    }
}