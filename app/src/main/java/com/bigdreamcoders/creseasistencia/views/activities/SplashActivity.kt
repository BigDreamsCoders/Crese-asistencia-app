package com.bigdreamcoders.creseasistencia.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.SplashPresenterImp
import com.bigdreamcoders.creseasistencia.presenters.SplashPresenter
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.SplashView
import com.google.firebase.messaging.FirebaseMessaging
import spencerstudios.com.bungeelib.Bungee

const val TIME_OUT = 2000


class SplashActivity : AppCompatActivity(), SplashView {

    private lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(Constants.PREFERENCES_THEME, false)
        ) {
            setTheme(R.style.Base_AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        splashPresenter = SplashPresenterImp(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            registerOnFirebase()
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

    private fun registerOnFirebase() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                Constants.PREFERENCES_NOTIFICATION,
                true
            )
        ) {
            FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
            Log.d("FIREBASE", true.toString())
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC)
            Log.d("FIREBASE", false.toString())
        }
    }

    override fun openNextActivity(flag: Boolean) {
        if (flag) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
        Bungee.card(this@SplashActivity)
        finish()
    }
}
