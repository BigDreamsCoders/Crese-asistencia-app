package com.bigdreamcoders.creseasistencia.models

import com.bigdreamcoders.creseasistencia.presenters.SplashPresenter
import com.bigdreamcoders.creseasistencia.views.views.SplashView

class SplashPresenterImp(private val splashView: SplashView) : SplashPresenter {

    override fun decideNextActivity(hasToken: Boolean, hasSessionSaved: Boolean){
        splashView.openNextActivity(hasToken and hasSessionSaved)
    }

}