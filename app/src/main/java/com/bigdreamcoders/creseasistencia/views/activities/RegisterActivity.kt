package com.bigdreamcoders.creseasistencia.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bigdreamcoders.creseasistencia.R
import spencerstudios.com.bungeelib.Bungee

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideRight(this)
    }
}
