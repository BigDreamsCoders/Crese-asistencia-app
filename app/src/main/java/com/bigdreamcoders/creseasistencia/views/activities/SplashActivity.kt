package com.bigdreamcoders.creseasistencia.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.SplashPresenterImp
import com.bigdreamcoders.creseasistencia.presenters.SplashPresenter
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.SplashView
import spencerstudios.com.bungeelib.Bungee

const val TIME_OUT = 2000


class SplashActivity : AppCompatActivity(), SplashView {

    private lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashPresenter = SplashPresenterImp(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            splashPresenter.decideNextActivity(checkLog(), checkPref())
        }, TIME_OUT.toLong())
    }

    private fun checkPref(): Boolean =
        getSharedPreferences(
            Constants.SP_NAME,
            Context.MODE_PRIVATE
        ).getBoolean(Constants.SP_STAY_ACTIVE, false)

    private fun checkLog(): Boolean =
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
            .contains(Constants.SP_TOKEN)

    override fun openNextActivity(flag:Boolean) {
        if(flag){
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }else{
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        Bungee.card(this@SplashActivity)
        finish()
    }
}
