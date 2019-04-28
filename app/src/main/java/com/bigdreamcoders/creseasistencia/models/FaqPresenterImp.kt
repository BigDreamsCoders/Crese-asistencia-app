package com.bigdreamcoders.creseasistencia.models

import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.presenters.FaqPresenter
import com.bigdreamcoders.creseasistencia.services.networkService.RequestService
import com.bigdreamcoders.creseasistencia.views.views.FaqView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class FaqPresenterImp(private val view:FaqView):FaqPresenter {

    private lateinit var disposable: Disposable

    private val service by lazy {
        RequestService.create()
    }

    override fun fetchFAQ(auth:String) {
        view.beginFetch()
        disposable=service
            .fetchFaq("Bearer $auth")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.code()){
                    200->{
                        view.deleteAllChild()
                        view.inflateView(it?.body()?.faqs?: ArrayList())
                        view.finishFetch()
                    }
                    401->{
                        view.logout()
                    }
                    else->{
                        view.error(R.string.some_err_msg)
                    }
                }
            }, {
                run {
                    if (it is SocketTimeoutException || it is UnknownHostException) {
                        view.error(R.string.timeout_msg)
                        view.finishFetch()
                    } else {
                        view.finishFetch()
                        view.error(R.string.some_err_msg)
                        it.printStackTrace()
                    }
                }
            })
    }
}