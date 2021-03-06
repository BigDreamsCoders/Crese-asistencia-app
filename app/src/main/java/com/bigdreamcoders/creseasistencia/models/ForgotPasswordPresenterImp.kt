package com.bigdreamcoders.creseasistencia.models

import com.bigdreamcoders.creseasistencia.presenters.ForgotPasswordPresenter
import com.bigdreamcoders.creseasistencia.services.networkService.RequestService
import com.bigdreamcoders.creseasistencia.views.views.ForgotPasswordView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ForgotPasswordPresenterImp(val view: ForgotPasswordView) : ForgotPasswordPresenter {

    private lateinit var disposable: Disposable

    private val service by lazy {
        RequestService.createLogin()
    }

    override fun requestRecovery(email: String) {
        if (email == "") {
            view.badCredentials()
        } else {
            view.perform()
            disposable = service
                .forgot(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.done()
                    when (it.code()) {
                        200 -> view.successful()
                        404 -> view.badCredentials()
                    }
                }, {
                    run {
                        view.done()
                        if (it is SocketTimeoutException || it is UnknownHostException) {
                            view.timeout()
                        } else {
                            it.printStackTrace()
                            view.errorH()
                        }
                    }
                })
        }
    }

}