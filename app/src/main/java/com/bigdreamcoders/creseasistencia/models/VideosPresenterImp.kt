package com.bigdreamcoders.creseasistencia.models

import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.presenters.VideosPresenter
import com.bigdreamcoders.creseasistencia.services.NetworkService.RequestService
import com.bigdreamcoders.creseasistencia.views.views.VideosView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class VideosPresenterImp(private val view: VideosView) : VideosPresenter {

    private lateinit var disposable: Disposable

    private val service by lazy {
        RequestService.create()
    }

    override fun fetchVideos(token: String, word: String, category: String) {
        view.beginFetch()
        disposable = service
            .fetchVideos("Bearer $token", word, category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.code()){
                    200->{
                        view.updateItemCount(it.body()?.count ?: 0)
                        view.updateList(it.body()?.videos ?: ArrayList())
                        view.finishFetch()
                    }
                    401->{
                        view.finishFetch()
                        view.logout()
                    }
                    else->{
                        view.error(R.string.some_err_msg)
                        view.finishFetch()
                    }
                }
            }, {
                run {
                    if (it is SocketTimeoutException || it is UnknownHostException) {
                        view.error(R.string.timeout_msg)
                        view.finishFetch()
                    } else {
                        view.error(R.string.some_err_msg)
                        view.finishFetch()
                    }
                }
            })
    }
}