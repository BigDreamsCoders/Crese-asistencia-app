package com.bigdreamcoders.creseasistencia.views.activities

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.networkService.models.manuals.Manuals
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.fragments.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.smarteist.autoimageslider.DefaultSliderView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import spencerstudios.com.bungeelib.Bungee

class MainActivity : AppCompatActivity(), DashBoardFragment.InnerDashBoardFun,
    ManualsFragment.InnerFunctions, CategoryFragment.InnerCategoryFunctions,
    VideosFragment.InnerFunctions, FaqFragment.InnerFunctions {

    private var title: ArrayList<String> = ArrayList()
    private var shouldOpenMenu = true
    private lateinit var manuals: Manuals

    override fun onCreate(savedInstanceState: Bundle?) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(Constants.PREFERENCES_THEME, false)
        ) {
            setTheme(R.style.Base_AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) {
            firstEntry()
        }
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(Constants.SI_TITLE, title)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredTitle =
            savedInstanceState?.getStringArrayList(Constants.SI_TITLE) ?: ArrayList()
        title = restoredTitle
        changeToolbarTitle()
        showBackButton(supportFragmentManager.backStackEntryCount > 0)
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showBackButton(false)
        nv_main_activity.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dw_manuals -> {
                    this.changeFragment(
                        CategoryFragment.newInstance(resources.getString(Constants.MATERIAL_TYPE_MANUAL)),
                        resources.getString(Constants.MATERIAL_TYPE_MANUAL)
                    )
                }
                R.id.dw_videos -> {
                    this.changeFragment(
                        CategoryFragment.newInstance(resources.getString(Constants.MATERIAL_TYPE_VIDEO)),
                        resources.getString(Constants.MATERIAL_TYPE_VIDEO)
                    )
                }
                R.id.dw_quotation -> {
                    this.changeFragment(
                        CategoryFragment.newInstance(resources.getString(Constants.MATERIAL_TYPE_QUESTION)),
                        resources.getString(Constants.MATERIAL_TYPE_QUESTION)
                    )
                }
                R.id.session_drawer -> {
                    clearSession()
                }
                R.id.settings -> {
                    startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                    Bungee.card(this)
                    return@setNavigationItemSelectedListener true
                }

            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun firstEntry() {
        title.add(resources.getString(R.string.dashboard_fragment_name))
        supportActionBar?.apply {
            title = this@MainActivity.title.last()
            setHomeAsUpIndicator(R.drawable.ic_dehaze)
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_content_main_content, DashBoardFragment(), "DashBoard")
            .commit()
    }

    private fun clearSession() {
        getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        Bungee.card(this)
        finish()
    }

    private fun showBackButton(enable: Boolean) {
        shouldOpenMenu = if (enable) {
            dl_main_activity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            supportActionBar?.setHomeAsUpIndicator(null)
            false
        } else {
            dl_main_activity.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_dehaze)
            true
        }
    }

    private fun changeToolbarTitle() {
        supportActionBar?.title = this@MainActivity.title.last()
    }

    override fun setSlideViews(): ArrayList<DefaultSliderView> {
        val array = ArrayList<DefaultSliderView>()
        for (i in 0..6) {
            val slider = DefaultSliderView(this@MainActivity)
            when (i) {
                0 -> slider.setImageDrawable(R.drawable.ad4)
                1 -> slider.setImageDrawable(R.drawable.ad9)
                2 -> slider.setImageDrawable(R.drawable.ad10)
                3 -> slider.setImageDrawable(R.drawable.ad5)
                4 -> slider.setImageDrawable(R.drawable.ad8)
                5 -> slider.setImageDrawable(R.drawable.ad7)
                6 -> slider.setImageDrawable(R.drawable.ad6)
            }
            slider.setImageScaleType(ImageView.ScaleType.FIT_XY)
            array.add(slider)
        }
        return array
    }

    override fun changeFragment(fragment: Fragment, tag: String) {
        title.add(tag)
        changeToolbarTitle()
        showBackButton(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_content_main_content, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                if (shouldOpenMenu) {
                    dl_main_activity.openDrawer(GravityCompat.START)
                    return true
                } else {
                    onBackPressed()
                }
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        title.removeAt(title.size - 1)
        changeToolbarTitle()
        if (supportFragmentManager.backStackEntryCount == 0) {
            showBackButton(false)
        }
    }

    override fun getToken(): String {
        return getSharedPreferences(
            Constants.SP_NAME,
            Context.MODE_PRIVATE
        ).getString(Constants.SP_TOKEN, "") ?: ""
    }

    private fun downloadPDF(manual: Manuals) {
        val fileName = "${manual.name}.pdf"
        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val r = DownloadManager.Request(Uri.parse(manual.URL)).apply {
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            allowScanningByMediaScanner()
            setMimeType("application/pdf")
            setNotificationVisibility(1)
        }
        dm.enqueue(r)
        Snackbar.make(
            dl_main_activity,
            resources.getString(R.string.download_info),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun checkPermission(manual: Manuals) {
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makePermissionRequest()
            this@MainActivity.manuals = manual
        } else {
            downloadPDF(manual)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.downloadPDF(manuals)
                } else {
                    Snackbar.make(
                        dl_main_activity,
                        "Danos permiso gandalla",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun makePermissionRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            101
        )
    }

    override fun logout() {
        Toast.makeText(this, resources.getString(R.string.logout_msg), Toast.LENGTH_LONG).show()
        clearSession()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        Bungee.zoom(this)
        finish()
    }

    override fun error(msg: Int) {
        Snackbar.make(dl_main_activity, resources.getText(msg), Snackbar.LENGTH_LONG).show()
    }

    override fun openVideo(url: String) {
        val appIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${url.substringAfter("v=")}"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }

    override fun hideKeyboard() {
        try {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {

        }
    }

}
