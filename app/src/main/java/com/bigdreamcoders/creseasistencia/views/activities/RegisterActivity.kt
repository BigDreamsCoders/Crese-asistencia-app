package com.bigdreamcoders.creseasistencia.views.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.RegisterPresenterImp
import com.bigdreamcoders.creseasistencia.pojos.UserRegister
import com.bigdreamcoders.creseasistencia.presenters.RegisterPresenter
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.RegisterView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_register.*
import spencerstudios.com.bungeelib.Bungee

class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var registerPresenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        if(PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(Constants.PREFERENCES_THEME, false)){
            setTheme(R.style.Base_AppTheme_Dark)
        }else{
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerPresenter = RegisterPresenterImp(this)
        bindViews()
    }

    private fun bindViews() {
        setSupportActionBar(toolbarRegister)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(null)
        }
        tv_login_register_activity.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            Bungee.slideRight(this)
        }
        btn_register_register_activity.setOnClickListener {
            registerPresenter.performRegister(
                UserRegister(
                    et_username_register_activity.text.toString().trim(),
                    et_email_register_activity.text.toString().trim(),
                    (et_password_register_activity as EditText).text.toString().trim(),
                    (et_password_repeat_register_activity as EditText).text.toString().trim()
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun registerSuccess() {
        cl_register_action_register_activity.visibility = View.VISIBLE
        pb_register_activity.visibility = View.GONE
        Toast.makeText(this, "Account created, you most login", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        Bungee.slideRight(this)
        finish()
    }

    override fun registerError(message: Int) {
        cl_register_action_register_activity.visibility = View.VISIBLE
        pb_register_activity.visibility = View.GONE
        Snackbar.make(cl_root_register_activity, resources.getString(message), Snackbar.LENGTH_LONG).show()
    }

    override fun registerPerform() {
        cl_register_action_register_activity.visibility = View.GONE
        pb_register_activity.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideRight(this)
    }
}
