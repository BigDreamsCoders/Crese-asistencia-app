package com.bigdreamcoders.creseasistencia.models

import android.text.TextUtils
import android.util.Log
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.presenters.FaqPresenter
import com.bigdreamcoders.creseasistencia.services.networkService.RequestService
import com.bigdreamcoders.creseasistencia.services.networkService.models.faq.FaqBody
import com.bigdreamcoders.creseasistencia.views.views.FaqView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class FaqPresenterImp(private val view: FaqView) : FaqPresenter {

    private lateinit var disposable: Disposable

    private val service by lazy {
        RequestService.create()
    }

    override fun sendEmail(auth: String, email: String, name: String, content: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(content)) {
            view.msg(R.string.some_empty)
        } else {
            view.beginFetch()
            disposable = service
                .fetchFaq("Bearer $auth", FaqBody(email, name, content))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.code()) {
                        200 -> {
                            view.finishFetch()
                            view.msg(R.string.requisition)
                        }
                        401 -> {
                            view.logout()
                        }
                        else -> {
                            view.finishFetch()
                            view.msg(R.string.some_err_msg)
                        }
                    }
                }, {
                    run {
                        if (it is SocketTimeoutException || it is UnknownHostException) {
                            view.msg(R.string.timeout_msg)
                            view.finishFetch()
                        } else {
                            view.finishFetch()
                            view.msg(R.string.some_err_msg)
                            it.printStackTrace()
                        }
                    }
                })
        }
    }
}