package com.bigdreamcoders.creseasistencia.views.activities

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.TypedValue
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.bigdreamcoders.creseasistencia.R
import com.bigdreamcoders.creseasistencia.services.networkService.models.faq.Faq
import com.bigdreamcoders.creseasistencia.services.networkService.models.manuals.Manuals
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.bigdreamcoders.creseasistencia.views.fragments.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.smarteist.autoimageslider.DefaultSliderView
import hakobastvatsatryan.DropdownTextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import spencerstudios.com.bungeelib.Bungee

class MainActivity : AppCompatActivity(), DashBoardFragment.InnerDashBoardFun,
    ManualsFragment.InnerFunctions, CategoryFragment.InnerCategoryFunctions,
    VideosFragment.InnerFunctions, FaqFragment.InnerFunctions {

    private var title: ArrayList<String> = ArrayList()
    private var shouldOpenMenu = true

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
        for (i in 0..2) {
            val slider = DefaultSliderView(this@MainActivity)
            when (i) {
                0 -> slider.setImageDrawable(R.drawable.ad1)
                1 -> slider.setImageDrawable(R.drawable.ad2)
                2 -> slider.setImageDrawable(R.drawable.ad3)
            }
            slider.setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
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

    override fun downloadPDF(manual: Manuals) {
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

    override fun createView(list: ArrayList<Faq>): MutableList<LinearLayout> {
        return MutableList(list.size) {
            run {
                val value = TypedValue()
                theme.resolveAttribute(R.attr.themeName, value, true)
                val bool = "dark" == value.string
                val linearLayout = LinearLayout(this@MainActivity)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 5, 10, 5)
                val item = DropdownTextView.Builder(this@MainActivity)
                    .setTitleTextRes(R.string.faq_item_title)
                    .setContentTextRes(R.string.faq_item_content)
                    .setRegularBackgroundDrawableRes(
                        if (bool) {
                            R.drawable.dropdown_regular_dark
                        } else {
                            R.drawable.dropdown_regular
                        }
                    )
                    .setExpandedBackgroundDrawableRes(
                        if (bool) {
                            R.drawable.dropdown_expanded_dark
                        } else {
                            R.drawable.dropdown_expanded
                        }
                    )
                    .setTitleTextColorRes(R.color.textColorDark)
                    .setContentTextColorRes(R.color.textColorDark)
                    .setPanelPaddingRes(R.dimen.panel_default_padding)
                    .setContentPaddingRes(R.dimen.content_default_padding)
                    .setExpandDuration(1)
                    .build()
                item.apply {
                    setTitleText(list[it].question)
                    setContentText(list[it].answer)
                }
                linearLayout.addView(item, params)
                return@run linearLayout
            }
        }
    }

}
