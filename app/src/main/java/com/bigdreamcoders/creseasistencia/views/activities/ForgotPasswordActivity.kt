package com.bigdreamcoders.creseasistencia.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bigdreamcoders.creseasistencia.R
import spencerstudios.com.bungeelib.Bungee

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideLeft(this)
    }
}
