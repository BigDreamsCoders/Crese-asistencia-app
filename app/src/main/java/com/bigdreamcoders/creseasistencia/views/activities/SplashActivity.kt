package com.bigdreamcoders.creseasistencia.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.login.Login
import com.bigdreamcoders.creseasistencia.utils.Constants
import spencerstudios.com.bungeelib.Bungee

const val TIME_OUT = 2000


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent=if(checkLog() and checkPref()){
                Intent(this@SplashActivity, MainActivity::class.java)
            }else{
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            Bungee.card(this@SplashActivity)
            finish()
        }, TIME_OUT.toLong())
    }

    private fun checkPref():Boolean=getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE).getBoolean(Constants.SP_STAY_ACTIVE, false)

    private fun checkLog():Boolean{
        return getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
            .contains(Constants.SP_TOKEN)
    }
}
