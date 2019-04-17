package com.bigdreamcoders.creseasistencia.models

import android.util.Log
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.pojos.UserRegister
import com.bigdreamcoders.creseasistencia.presenters.RegisterPresenter
import com.bigdreamcoders.creseasistencia.services.NetworkService.RequestService
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.register.Register
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.RegisterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterPresenterImp(private val registerView: RegisterView) : RegisterPresenter {

    private lateinit var disposable:Disposable

    private val service by lazy {
        RequestService.create()
    }

    override fun performRegister(user: UserRegister) {
        when{
            user.username.isEmpty()->registerView.registerError(R.string.username_empty)
            Constants.hasAt(user.username)||Constants.hasDotCom(user.username)->registerView.registerError(R.string.has_special_char)
            user.email.isEmpty()->registerView.registerError(R.string.email_empty)
            !Constants.isEmailOrUsername(user.email)->registerView.registerError(R.string.email_valid)
            user.password.isEmpty()||user.repeatPassword.isEmpty()->registerView.registerError(R.string.password_empty)
            user.password!=user.repeatPassword->registerView.registerError(R.string.password_not_match)
            user.password.length<8-> registerView.registerError(R.string.password_length)
            else->{
                registerView.registerPerform()
                disposable=service
                    .register(Register(user.email, user.password, user.username))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("CODEXXX", it.code().toString())
                        registerView.registerSuccess()
                    }, {
                        registerView.registerError(R.string.server_error)
                    })
            }
        }
    }
}