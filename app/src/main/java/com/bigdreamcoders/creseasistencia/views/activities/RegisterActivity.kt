package com.bigdreamcoders.creseasistencia.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.views.views.RegisterView
import kotlinx.android.synthetic.main.activity_register.*
import spencerstudios.com.bungeelib.Bungee

class RegisterActivity : AppCompatActivity(), RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bindViews()
    }

    private fun bindViews(){
        tv_login_register_activity.setOnClickListener{
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            Bungee.slideRight(this)
        }
    }

    override fun registerEmptyUsername() {

    }

    override fun registerEmptyEmail() {

    }

    override fun registerEmptyPassword() {

    }

    override fun registerSuccess() {

    }

    override fun registerError(message: String) {

    }

    override fun registerPerform() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideRight(this)
    }
}
