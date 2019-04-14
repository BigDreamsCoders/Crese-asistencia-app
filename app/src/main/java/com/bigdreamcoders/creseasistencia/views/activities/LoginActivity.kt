package com.bigdreamcoders.creseasistencia.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.LoginPresenterImp
import com.bigdreamcoders.creseasistencia.presenters.LoginPresenter
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.LoginView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter=LoginPresenterImp(this)
        bindViews()
    }

    private fun bindViews(){
        btn_login_login_activity.setOnClickListener{
            loginPresenter.performLogin(et_id_login_activity.text.toString().trim(), et_password_login_activity.text.toString().trim())
        }
    }

    override fun loginDefineEmailOrUsername(id: String): Boolean {
        return Constants.isEmailOrUsername(id)
    }

    override fun loginEmptyId(message: String) {
        Snackbar.make(cl_root_login_activity, resources.getString(R.string.id_empty), Snackbar.LENGTH_LONG).show()
    }

    override fun loginEmptyPassword(message: String) {
        Snackbar.make(cl_root_login_activity, resources.getString(R.string.password_empty), Snackbar.LENGTH_LONG).show()
    }

    override fun loginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginPerform() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginSaveToken(token: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
