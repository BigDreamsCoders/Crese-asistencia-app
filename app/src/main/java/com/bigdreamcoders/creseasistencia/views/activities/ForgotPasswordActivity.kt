package com.bigdreamcoders.creseasistencia.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.models.ForgotPasswordPresenterImp
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.views.ForgotPasswordView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*
import spencerstudios.com.bungeelib.Bungee

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordView {

    private lateinit var presenter:ForgotPasswordPresenterImp

    override fun onCreate(savedInstanceState: Bundle?) {
        if(PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(Constants.PREFERENCES_THEME, false)){
            setTheme(R.style.Base_AppTheme_Dark)
        }else{
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        presenter= ForgotPasswordPresenterImp(this)
        bind()
    }

    private fun bind(){
        btn_forgot_activity.setOnClickListener {
            presenter.requestRecovery(et_email_forgot_activity.text.toString().trim())
        }
        setSupportActionBar(toolbarFP)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(null)
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

    override fun onBackPressed() {
        super.onBackPressed()
        Bungee.slideLeft(this)
    }

    override fun perform() {
        btn_forgot_activity.visibility= View.GONE
        pb_forgot_activity.visibility=View.VISIBLE
    }

    override fun errorH() {
        Snackbar.make(cl_forgot_activity, resources.getText(R.string.some_err_msg), Snackbar.LENGTH_LONG).show()
    }

    override fun badCredentials() {
        Snackbar.make(cl_forgot_activity, resources.getText(R.string.bad_email), Snackbar.LENGTH_LONG).show()
    }

    override fun timeout() {
        Snackbar.make(cl_forgot_activity, resources.getText(R.string.timeout_msg), Snackbar.LENGTH_LONG).show()
    }

    override fun done() {
        btn_forgot_activity.visibility= View.VISIBLE
        pb_forgot_activity.visibility=View.GONE
    }

    override fun successful() {
        Snackbar.make(cl_forgot_activity, resources.getText(R.string.password_sucessful), Snackbar.LENGTH_LONG).show()
    }
}
