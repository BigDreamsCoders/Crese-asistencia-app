package com.bigdreamcoders.creseasistencia.views.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.LoginPresenterImp
import com.bigdreamcoders.creseasistencia.presenters.LoginPresenter
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.LoginView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import spencerstudios.com.bungeelib.Bungee

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter = LoginPresenterImp(this)
        bindViews()
    }

    private fun bindViews() {
        btn_login_login_activity.setOnClickListener {
            loginPresenter.performLogin(
                et_id_login_activity.text.toString().trim(),
                (et_password_login_activity as EditText).text.toString().trim()
            )
        }
        tv_register_login_activity.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            Bungee.slideLeft(this@LoginActivity)
        }
        tv_forgot_password_login_activity.setOnClickListener{
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
            Bungee.slideRight(this@LoginActivity)
        }
    }

    override fun loginDefineEmailOrUsername(id: String): Boolean {
        return Constants.isEmailOrUsername(id)
    }

    override fun loginEmptyId() {
        Snackbar.make(cl_root_login_activity, resources.getString(R.string.id_empty), Snackbar.LENGTH_LONG).show()
    }

    override fun loginEmptyPassword() {
        Snackbar.make(cl_root_login_activity, resources.getString(R.string.password_empty), Snackbar.LENGTH_LONG).show()
    }

    override fun loginSuccess() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        Bungee.card(this@LoginActivity)
        finish()
    }

    override fun loginError(message: String) {
        cl_login_options_login_activity.visibility=View.VISIBLE
        pb_login_activity.visibility=View.GONE
        Snackbar.make(cl_root_login_activity, message, Snackbar.LENGTH_LONG).show()
    }

    override fun loginPerform() {
        cl_login_options_login_activity.visibility=View.GONE
        pb_login_activity.visibility=View.VISIBLE
    }

    override fun loginSaveToken(token: String?) {
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(Constants.SP_TOKEN, token).apply()
    }

    override fun loginSavePreference() {
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(Constants.SP_STAY_ACTIVE, cb_prevail_session_login_activity.isChecked).apply()
    }
}
