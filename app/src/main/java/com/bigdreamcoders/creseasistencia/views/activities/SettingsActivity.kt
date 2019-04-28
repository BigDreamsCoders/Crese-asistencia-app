package com.bigdreamcoders.creseasistencia.views.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.settings_activity.*
import spencerstudios.com.bungeelib.Bungee

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(Constants.PREFERENCES_THEME, false)
        ) {
            setTheme(R.style.Base_AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        setSupportActionBar(toolbar_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
        finish()
        Bungee.card(this)
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences?,
            key: String?
        ) {
            when(key){
                Constants.PREFERENCES_THEME->{
                    activity?.finish()
                    activity?.startActivity(Intent(activity, SettingsActivity::class.java))
                }
                Constants.PREFERENCES_NOTIFICATION->{
                    if(sharedPreferences?.getBoolean(key, true) == false){
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC)
                    }else{
                        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
                    }
                }
            }

        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            PreferenceManager.getDefaultSharedPreferences(activity)
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }
    }
}